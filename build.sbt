import Dependencies.*

val scalaV      = "2.13.15"

lazy val core = (project in file("core"))
  .settings(
    name         := "core",
    version      := "0.1",
    scalaVersion := scalaV,
    libraryDependencies ++= all,
  )

lazy val api = (project in file("api"))
  .settings(
    name         := "api",
    version      := "0.1",
    scalaVersion := scalaV,
    libraryDependencies ++= all,
  )

lazy val root = (project in file("."))
  .aggregate(api, core)
  .settings(
    name         := "zio-web-app",
    scalaVersion := scalaV,
    libraryDependencies ++= Seq(),
    scalacOptions ++= Seq(
      "-encoding",
      "UTF-8",
      "-Xfatal-warnings",
      "-deprecation",
      "-feature",
      "-unchecked",
      "-language:implicitConversions",
      "-language:higherKinds",
      "-language:existentials",
      "-language:postfixOps",
    ),
  )
