
import sbt._

class Life(info: ProjectInfo) extends DefaultProject(info) {

    val junit = "junit" % "junit" % "4.4" % "test"
    val scalatest = "org.scalatest" % "scalatest" % "1.2" % "test"
    
}

