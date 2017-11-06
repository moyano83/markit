name := "markit"

version := "0.1"

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  "org.skinny-framework" %% "skinny-http-client" % "2.5.1",
  "joda-time" % "joda-time" % "2.9",
  "org.scalatest" %% "scalatest" % "3.0.4" % "test",
  "junit" % "junit" % "4.12" % "test",
  "org.scalamock" %% "scalamock-scalatest-support" % "3.6.0" % "test")

mainClass := Some("MarkitApp")

artifactName := { (sv: ScalaVersion, module: ModuleID, artifact: Artifact) => s"Markit.${artifact.extension}"}

assemblyJarName in assembly := "Markit.jar"
