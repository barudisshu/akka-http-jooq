import com.typesafe.sbt.packager.docker.Cmd

organization in ThisBuild := "info.galudisu"

name := """akka-http-jooq"""

maintainer := "Galudisu <galudisu@gmail.com>"

/* scala versions and options */
scalaVersion := "2.12.8"

// dependencies versions
lazy val log4jVersion           = "2.7"
lazy val scalaLoggingVersion    = "3.7.2"
lazy val chillVersion           = "0.9.5"
lazy val slf4jVersion           = "1.7.25"
lazy val akkaHttpVersion        = "10.1.12"
lazy val akkaVersion            = "2.6.6"
lazy val persistenceVersion     = "1.0.1"
lazy val mysqlVersion           = "8.0.13"
lazy val hikariCPVersion        = "3.4.5"
lazy val jooqVersion            = "3.12.4"
lazy val akkaManagementVersion  = "1.0.8"
lazy val scalaJavaCompatVersion = "0.9.1"

// make version compatible with docker for publishing
ThisBuild / dynverSeparator := "-"

// This work for jdk >= 8u131
javacOptions in Universal := Seq(
  "-J-XX:+UnlockExperimentalVMOptions",
  "-J-XX:+UseCGroupMemoryLimitForHeap",
  "-J-XX:MaxRAMFraction=1",
  "-J-XshowSettings:vm"
)

// These options will be used for *all* versions.
scalacOptions := Seq(
  "-unchecked",
  "-feature",
  "-language:existentials",
  "-language:higherKinds",
  "-language:implicitConversions",
  "-language:postfixOps",
  "-deprecation",
  "-encoding",
  "utf8"
)
resolvers ++= Seq(
  "Local Maven Repository" at "file://" + Path.userHome.absolutePath + "/.m2/repository",
  Resolver.sonatypeRepo("releases"),
  Resolver.sonatypeRepo("snapshots")
)

fork in run := true
Compile / run / fork := true
scalafmtOnCompile := true
scalafmtTestOnCompile := true
scalafmtVersion := "1.2.0"

mainClass in (Compile, run) := Some("info.galudisu.Main")

enablePlugins(JavaAppPackaging, DockerPlugin, AshScriptPlugin)

dockerExposedPorts := Seq(1600, 1601, 1602, 8080)
dockerUpdateLatest := true
version in Docker := "latest"
dockerBaseImage := "openjdk:8u171-jre-alpine"
dockerRepository := Some("cplier")
daemonUser in Docker := "root"
dockerCommands := dockerCommands.value.flatMap {
  case cmd @ Cmd("FROM", _) => List(cmd, Cmd("RUN", "apk update && apk add bash"))
  case other                => List(other)
}

wartremoverWarnings in (Compile, compile) ++= Warts.allBut(Wart.Throw)

libraryDependencies ++= {
  Seq(
    "org.scala-lang.modules"     %% "scala-java8-compat"          % scalaJavaCompatVersion,
    "com.typesafe.akka"          %% "akka-http"                   % akkaHttpVersion,
    "com.typesafe.akka"          %% "akka-http-spray-json"        % akkaHttpVersion,
    "com.typesafe.akka"          %% "akka-cluster-typed"          % akkaVersion,
    "com.typesafe.akka"          %% "akka-cluster-sharding-typed" % akkaVersion,
    "com.typesafe.akka"          %% "akka-stream-typed"           % akkaVersion,
    "com.typesafe.akka"          %% "akka-cluster-tools"          % akkaVersion,
    "com.typesafe.akka"          %% "akka-persistence-typed"      % akkaVersion,
    "com.typesafe.akka"          %% "akka-persistence-query"      % akkaVersion,
    "com.typesafe.akka"          %% "akka-persistence-cassandra"  % persistenceVersion,
    "com.typesafe.akka"          %% "akka-discovery"              % akkaVersion,
    "org.apache.logging.log4j"   % "log4j-core"                   % log4jVersion,
    "org.apache.logging.log4j"   % "log4j-api"                    % log4jVersion,
    "org.apache.logging.log4j"   % "log4j-slf4j-impl"             % log4jVersion,
    "com.typesafe.scala-logging" %% "scala-logging"               % scalaLoggingVersion,
    "com.twitter"                %% "chill-akka"                  % chillVersion,
    "org.slf4j"                  % "slf4j-api"                    % slf4jVersion,
    "mysql"                      % "mysql-connector-java"         % mysqlVersion,
    "com.zaxxer"                 % "HikariCP"                     % hikariCPVersion,
    "org.jooq"                   % "jooq"                         % jooqVersion,
    "org.jooq"                   % "jooq-meta"                    % jooqVersion,
    "org.jooq"                   % "jooq-codegen-maven"           % jooqVersion exclude ("org.slf4j", "slf4j-simple"),
    "org.jooq"                   %% "jooq-scala"                  % jooqVersion,
    "com.typesafe.akka"          %% "akka-testkit"                % akkaVersion % Test,
    "com.typesafe.akka"          %% "akka-actor-testkit-typed"    % akkaVersion % Test,
    "com.typesafe.akka"          %% "akka-http-testkit"           % akkaHttpVersion % Test,
    "com.typesafe.akka"          %% "akka-testkit"                % akkaVersion % Test,
    "com.typesafe.akka"          %% "akka-stream-testkit"         % akkaVersion % Test
  )
}

val generateJOOQ = taskKey[Seq[File]]("Generate JooQ classes")

generateJOOQ := {
  val src = sourceManaged.value
  val cp  = (fullClasspath in Compile).value
  val r   = (runner in Compile).value
  val s   = streams.value
  r.run("org.jooq.codegen.GenerationTool", cp.files, Array("jooq-generate.xml"), s.log)
    .failed foreach (sys error _.getMessage)
  (src ** "*.scala").get
}

unmanagedSourceDirectories in Compile += baseDirectory.value / "src" / "main" / "generated"
