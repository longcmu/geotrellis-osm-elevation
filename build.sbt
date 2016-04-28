lazy val commonSettings = Seq(
  version := "0.1.0",
  scalaVersion := "2.10.5",
  crossScalaVersions := Seq("2.11.5", "2.10.5"),
  organization := "com.azavea",
  licenses := Seq("Apache-2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0.html")),
  scalacOptions ++= Seq(
    "-deprecation",
    "-unchecked",
    "-Yinline-warnings",
    "-language:implicitConversions",
    "-language:reflectiveCalls",
    "-language:higherKinds",
    "-language:postfixOps",
    "-language:existentials",
    "-feature"),
  publishMavenStyle := true,
  publishArtifact in Test := false,
  pomIncludeRepository := { _ => false },

  libraryDependencies += "org.scalatest" %%  "scalatest" % "2.2.0" % "test",

  resolvers += Resolver.bintrayRepo("azavea", "geotrellis"),

  shellPrompt := { s => Project.extract(s).currentProject.id + " > " }
) ++ net.virtualvoid.sbt.graph.Plugin.graphSettings

lazy val root = Project("demo", file("."))
  .aggregate(ingest, server, core)

lazy val vectorTile = Project("scala-vector-tile", file("scala-vector-tile"))
  .settings(commonSettings: _*)

lazy val core = Project("core", file("core"))
  .dependsOn(vectorTile)  
  .settings(commonSettings: _*)

lazy val ingest = Project("ingest", file("ingest"))
  .dependsOn(core)
  .settings(commonSettings: _*)

lazy val server = Project("server", file("server"))
  .dependsOn(core)
  .settings(commonSettings: _*)
