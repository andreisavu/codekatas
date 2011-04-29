
import sbt._

class _99probsProject(info: ProjectInfo) extends DefaultProject(info) with IdeaProject {
  val scalaTest = "org.scalatest" % "scalatest" % "1.3" % "test"
}

