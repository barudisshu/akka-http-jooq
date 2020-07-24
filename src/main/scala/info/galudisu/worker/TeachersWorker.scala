package info.galudisu.worker

import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.{ActorRef, Behavior}
import info.galudisu.common.TeachersCreateResponse
import info.galudisu.datasource.DataSourceProtocol._
import info.galudisu.model.tables.records.TeachersRecord
import info.galudisu.repository.TeacherRepository
import info.galudisu._

import scala.concurrent.ExecutionContext
import scala.util.{Failure, Success}

object TeachersWorker {

  final case class InsertTeacher(teachersRecord: TeachersRecord,
                                 replyTo: ActorRef[Command],
                                 targetReply: ActorRef[Option[TeachersCreateResponse]])
      extends Command

  final case class AddTeachersWorkFeedback(resp: Option[TeachersCreateResponse])(
      val replyTo: ActorRef[Option[TeachersCreateResponse]])
      extends Command

  def apply(mysqlDataSource: ActorRef[Command])(implicit ec: ExecutionContext): Behavior[Command] = Behaviors.setup {
    context =>
      var tmpRecord: Option[TeachersRecord]                                = None
      var tmpReplyTo: Option[ActorRef[Command]]                            = None
      var tmpTargetReply: Option[ActorRef[Option[TeachersCreateResponse]]] = None
      Behaviors.receiveMessage {
        case InsertTeacher(record, replyTo, targetReply) =>
          tmpRecord = Some(record)
          tmpReplyTo = Some(replyTo)
          tmpTargetReply = Some(targetReply)
          mysqlDataSource ! GetDSLContext(context.self)
          Behaviors.same
        case DSLContextResult(dsl) =>
          val repository   = new TeacherRepository(dsl)
          val futureRecord = repository.insertOne(tmpRecord.get)
          context.pipeToSelf(futureRecord) {
            case Failure(_) =>
              DbExecAsyncWrappedResult(DbExecAsyncFailure[TeachersCreateResponse](tmpTargetReply.get), tmpReplyTo.get)
            case Success(v) =>
              DbExecAsyncWrappedResult(DbExecAsyncSuccess[TeachersRecord, TeachersCreateResponse](v,
                                                                                                  tmpTargetReply.get),
                                       tmpReplyTo.get)

          }
          Behaviors.same
        case DbExecAsyncWrappedResult(result, replyTo) =>
          replyTo ! result
          Behaviors.stopped
      }
  }
}
