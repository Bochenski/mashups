name := "stack"

version := "0.0.1"

scalaVersion := "2.9.1"

resolvers += "sonatype-snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"

libraryDependencies ++= Seq(
	"com.novocode" % "junit-interface" % "0.8" % "test->default",
	"org.seleniumhq.selenium" % "selenium-java" % "2.20.0" % "test",
	"info.cukes" % "cucumber-junit" % "1.0.2" % "test",
	"info.cukes" % "cucumber-scala" % "1.0.2" % "test"
	)