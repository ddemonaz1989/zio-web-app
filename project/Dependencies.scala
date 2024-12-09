import sbt.*

object Dependencies {
  lazy val zio: Seq[ModuleID] = {
    val version = "2.1.13"
    Seq(
      "dev.zio" %% "zio"         % version,
      "dev.zio" %% "zio-streams" % version,
    )
  }

  lazy val `zio-logging-slf4j2`: Seq[ModuleID] = {
    val version = "2.4.0"
    Seq(
      "dev.zio" %% "zio-logging-slf4j2" % version,
    )
  }

  lazy val `zio-http`: Seq[ModuleID] = {
    val version = "3.0.1"
    Seq(
      "dev.zio" %% "zio-http" % version,
    )
  }

  lazy val `zio-kafka`: Seq[ModuleID] = {
    val version = "2.9.0"
    Seq(
      "dev.zio" %% "zio-kafka"         % version,
      "dev.zio" %% "zio-kafka-testkit" % version % Test,
    )
  }

  lazy val `zio-config`: Seq[ModuleID] = {
    val version = "4.0.2"
    Seq(
      "dev.zio" %% "zio-config"          % version,
      "dev.zio" %% "zio-config-typesafe" % version,
      "dev.zio" %% "zio-config-magnolia" % version,
    )
  }

  lazy val all: Seq[ModuleID] =
    zio ++
      `zio-http` ++
      `zio-kafka` ++
      `zio-config` ++
      `zio-logging-slf4j2`

}
