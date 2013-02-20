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
    "jp.t2v" %% "play21.auth" % "0.7",
    "com.github.seratch" %% "inputvalidator" % "[0.2,)",
    "com.github.seratch" %% "inputvalidator-play" % "[0.2,)"
  )

  val main = play.Project(appName, appVersion, appDependencies).settings(
    routesImport ++= Seq(
      "plugin.Binders._"
    ),
    templatesImport ++= Seq(
      "plugin.CURDPage",
      "plugin.CURDRoutes"
    ),
    resolvers ++= Seq(
      "t2v.jp repo" at "http://www.t2v.jp/maven-repo/"
    )
  )

}
