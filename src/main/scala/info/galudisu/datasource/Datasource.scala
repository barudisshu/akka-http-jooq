package info.galudisu.datasource

import akka.actor.typed._
import akka.actor.typed.scaladsl.Behaviors
import com.zaxxer.hikari.HikariDataSource
import info.galudisu.datasource.DataSourceProtocol.{DSLContextResult, GetDSLContext}
import info.galudisu.{Command, jooqSettings}
import org.jooq.impl.DSL
import org.jooq.{DSLContext, SQLDialect}

object DataSourceProtocol {
  case class GetDSLContext(replyTo: ActorRef[Command]) extends Command
  case class DSLContextResult(dsl: DSLContext)         extends Command
}

/**
  * 用于获取[[DSLContext]]
  * @author Galudisu
  */
object MySQLDataSource {
  val Name = "mysql-datasource"
  def apply(dataSource: HikariDataSource): Behavior[Command] =
    Behaviors.receive {
      case (_, GetDSLContext(replyTo)) =>
        val conn = dataSource.getConnection
        val dsl  = DSL.using(conn, SQLDialect.MYSQL, jooqSettings)
        replyTo ! DSLContextResult(dsl)
        Behaviors.same
      case (context, _) =>
        context.log.warn(s"Unsupported Protocol")
        Behaviors.same
    }
}
