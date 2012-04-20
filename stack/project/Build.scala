import sbt._
import Keys._
import PlayProject._

object ApplicationBuild extends Build {

    val appName         = "stack"
    val appVersion      = "0.0.1-SNAPSHOT"

    val appDependencies = Seq(
	   "com.mongodb.casbah" % "casbah_2.9.1" % "2.1.5-1",
	   "net.liftweb" % "lift-json_2.9.1" % "2.4-M5",
	   "joda-time" % "joda-time" % "2.0",
	   "org.joda" % "joda-convert" % "1.1",
       "com.novocode" % "junit-interface" % "0.8" % "test->default",
       "org.seleniumhq.selenium" % "selenium-java" % "2.20.0" % "test",
       "info.cukes" % "cucumber-junit" % "1.0.2" % "test",
       "info.cukes" % "cucumber-scala" % "1.0.2" % "test"
    )

    val main = PlayProject(appName, appVersion, appDependencies, mainLang = SCALA).settings(
      // Add your own project settings here      
    )

}
