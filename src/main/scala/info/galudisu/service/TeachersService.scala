package info.galudisu.service

import java.util.UUID

import akka.actor.typed.{ActorRef, ActorSystem}
import akka.cluster.sharding.typed.scaladsl.{ClusterSharding, Entity}
import akka.persistence.typed.PersistenceId
import akka.util.Timeout
import info.galudisu.Command
import info.galudisu.common.{TeachersCreateRequest, TeachersCreateResponse}
import info.galudisu.model.tables.records.TeachersRecord
import info.galudisu.sharding.TeachersSharding

import scala.concurrent.Future

class TeachersService(mysqlDataSource: ActorRef[Command], system: ActorSystem[_]) {
  private val sharding = ClusterSharding(system)
  import scala.concurrent.duration._

  private implicit val timeout: Timeout = Timeout(5.seconds)

  // registration at startup
  sharding.init(Entity(typeKey = TeachersSharding.TypeKey) { entityContext =>
    TeachersSharding(entityContext.entityId, PersistenceId(entityContext.entityTypeKey.name, entityContext.entityId))
  })

  def create(request: TeachersCreateRequest): Future[Option[TeachersCreateResponse]] = {
    val uuid           = UUID.randomUUID().toString
    val teachersRecord = new TeachersRecord()
    teachersRecord.setId(uuid)
    teachersRecord.setFirstName(request.firstName)
    teachersRecord.setLastName(request.lastName)
    val entityRef = sharding.entityRefFor(TeachersSharding.TypeKey, uuid)
    entityRef ? TeachersSharding.AddTeachers(teachersRecord, mysqlDataSource)
  }
}
