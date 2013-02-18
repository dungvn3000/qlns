import sbt._
import Keys._
import play.Project._

object ProjectBuild extends Build {

  val appName = "qlns"
  val appVersion = "1.0-SNAPSHOT"

  val appDependencies = Seq(
    jdbc,
    anorm,
    "mysql" % "mysql-connector-java" % "5.1.18",
    "joda-time" % "joda-time" % "2.1",
    "jp.t2v" %% "play21.auth" % "0.7"
  )


  val main = play.Project(appName, appVersion, appDependencies).settings(
    resolvers ++= Seq(
      "t2v.jp repo" at "http://www.t2v.jp/maven-repo/"
    )
  )

}
