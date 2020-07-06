package info.galudisu.sharding

import akka.actor.typed._
import akka.actor.typed.scaladsl._
import akka.cluster.sharding.typed.scaladsl._
import akka.persistence.typed.PersistenceId
import akka.persistence.typed.scaladsl._
import info.galudisu._
import info.galudisu.common.TeachersCreateResponse
import info.galudisu.model.tables.records.TeachersRecord
import info.galudisu.worker.TeachersWorker

object TeachersSharding {

  // Command
  final case class AddTeachers(teachersRecord: TeachersRecord, mysqlDataSource: ActorRef[Command])(
      val replyTo: ActorRef[Option[TeachersCreateResponse]])
      extends Command

  // Response
  final case class TeachersAdding(resp: Option[TeachersCreateResponse]) extends Command

  // Event
  final case class TeachersAdded(resp: Option[TeachersCreateResponse]) extends Event

  // State
  final case class Init(entityId: String)                                                               extends State
  final case class DbTeachersAdded(entityId: String, override val resp: Option[TeachersCreateResponse]) extends State

  // 特别说明：关于CQRS的操作，
  // 有两种方式：
  // 1. persist event -> response -> insert record
  // 2. insert record -> persist event -> response
  // 就是要么先保存状态， 立即返回数据给客户单，再慢慢更新MySQL数据库。
  // 要么是先保存数据，再持久化状态，再返回给前端。下次再访问同一个persistenceId是，直接获取状态记录。【本实例实现，可以按照需求实现】
  private def commandHandler(context: ActorContext[Command]): (State, Command) => Effect[TeachersAdded, State] = {
    (_, cmd) =>
      cmd match {
        case cmd: AddTeachers =>
          val worker = context.spawnAnonymous(TeachersWorker(cmd.mysqlDataSource)(context.system.executionContext))
          worker ! TeachersWorker.InsertTeacher(cmd.teachersRecord, context.self, cmd.replyTo)
          Effect.none
        case cmd: DbExecAsyncSuccess[TeachersRecord, TeachersCreateResponse] =>
          val insertData = cmd.result
          val resp       = TeachersCreateResponse(insertData.getId, insertData.getFirstName, insertData.getLastName)
          Effect
            .persist(TeachersAdded(Some(resp)))
            .thenRun(state => cmd.replyTo ! state.resp.map(_.asInstanceOf[TeachersCreateResponse]))
        case _: DbExecAsyncFailure[TeachersCreateResponse] =>
          Effect.none
      }
  }

  private val eventHandler: (State, TeachersAdded) => State = { (state, evt) =>
    evt match {
      case TeachersAdded(resp) =>
        DbTeachersAdded(state.entityId, resp)
    }
  }

  val TypeKey: EntityTypeKey[Command] = EntityTypeKey[Command]("TeachersWorker")

  def apply(entityId: String, persistenceId: PersistenceId): Behavior[Command] = {
    Behaviors.setup { context =>
      context.log.info("Starting create teacher with id {}", entityId)
      EventSourcedBehavior(persistenceId, emptyState = Init(entityId), commandHandler(context), eventHandler)
    }
  }
}
