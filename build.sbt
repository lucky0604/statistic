name := """statistic"""
organization := "com.statistic.lucky"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala).settings(
  watchSources ++= (baseDirectory.value / "public/ui" ** "*").get
)

scalaVersion := "2.12.6"

libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.2" % Test

libraryDependencies ++= Seq(
  jdbc ,
  ehcache ,
  ws ,
  specs2 % Test ,
  guice,
  filters,
  "com.typesafe.play" %% "play-slick" % "3.0.0",
  "mysql" % "mysql-connector-java" % "5.1.41",
  "com.typesafe.play" %% "play-mailer" % "6.0.0",
  "com.typesafe.play" %% "play-mailer-guice" % "6.0.0",
  "com.chuusai"        %% "shapeless" % "2.3.3",
  "io.underscore"      %% "slickless" % "0.3.3",
  "ai.x" %% "play-json-extensions" % "0.10.0",
  "com.github.tototoshi" %% "slick-joda-mapper" % "2.3.0",
  "joda-time" % "joda-time" % "2.7",
  "org.joda" % "joda-convert" % "1.7",
  "com.typesafe.play" % "play-json-joda_2.12" % "2.6.0",
  "com.github.nscala-time" %% "nscala-time" % "2.20.0"
)

unmanagedResourceDirectories in Test +=  (baseDirectory.value / "target/web/public/test" )

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.statistic.lucky.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.statistic.lucky.binders._"
EclipseKeys.preTasks := Seq(compile in Compile)
