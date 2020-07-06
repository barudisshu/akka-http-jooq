package info.galudisu

import java.util.concurrent.TimeUnit

import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.{ActorSystem, SupervisorStrategy}
import akka.cluster.ClusterEvent
import akka.cluster.typed._
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import akka.{actor => classic}
import com.typesafe.config.ConfigFactory
import com.zaxxer.hikari.{HikariConfig, HikariDataSource}
import info.galudisu.datasource.MySQLDataSource

import scala.concurrent.ExecutionContextExecutor

object Main extends App {

  val sysConfig = ConfigFactory.load()
  val clusterName: String = {
    sysConfig.getString("clustering.cluster.name")
  }
  val hikariConfig: HikariConfig = {
    val config = new HikariConfig()
    config.setDriverClassName(sysConfig.getString("db.driver"))
    config.setJdbcUrl(sysConfig.getString("db.url"))
    config.setUsername(sysConfig.getString("db.username"))
    config.setPassword(sysConfig.getString("db.password"))
    config.setMinimumIdle(sysConfig.getInt("db.hikaricp.minimumIdle"))
    config.setMaximumPoolSize(sysConfig.getInt("db.hikaricp.maximumPoolSize"))
    config.setConnectionTimeout(
      TimeUnit.SECONDS
        .toMillis(sysConfig.getInt("db.hikaricp.connectionTimeout"))
    )
    config.setIdleTimeout(
      TimeUnit.SECONDS.toMillis(sysConfig.getInt("db.hikaricp.idleTimeout"))
    )
    config.setAutoCommit(true)
    config
  }

  val dataSource = new HikariDataSource(hikariConfig)

  ActorSystem[Nothing](
    Behaviors.setup[Nothing] { context =>
      import akka.actor.typed.scaladsl.adapter._
      implicit val classicSystem: classic.ActorSystem = context.system.toClassic
      implicit val ec: ExecutionContextExecutor       = context.system.executionContext

      val cluster = Cluster(context.system)
      context.log.info(
        "Started [" + context.system + "], cluster.selfAddress = " + cluster.selfMember.address + ")"
      )

      Http().bindAndHandle(complete("Hello world"), "0.0.0.0", 8080)

      // Create an actor that handles cluster domain events
      val listener = context.spawn(
        Behaviors.receive[ClusterEvent.MemberEvent]((ctx, event) => {
          ctx.log.info("MemberEvent: {}", event)
          Behaviors.same
        }),
        "listener"
      )
      cluster.subscriptions ! Subscribe(
        listener,
        classOf[ClusterEvent.MemberEvent]
      )

      // 监督机制，确保数据准确。忽略失败。继续处理下一条
      val mySQLDataSource = context
        .spawn(Behaviors
                 .supervise(MySQLDataSource(dataSource))
                 .onFailure[Exception](SupervisorStrategy.resume),
               MySQLDataSource.Name)

      Behaviors.empty
    },
    clusterName
  )
}
