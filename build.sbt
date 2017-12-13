import Dependencies._

lazy val `monix-example` = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "com.github.DmytroOrlov",
      scalaVersion := "2.12.4",
      version      := "0.1.0-SNAPSHOT"
    )),
    libraryDependencies ++= Seq(
      "io.monix" %% "monix" % "2.3.0",
      scalaTest % Test
    )
  )
