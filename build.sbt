name := "emm-playground"

scalaVersion := "2.11.8"

version := "0.1-SNAPSHOT"

val EmmVersion = "0.2.1"

libraryDependencies += "com.codecommit" %% "emm-cats" % EmmVersion

libraryDependencies += "com.codecommit" %% "emm-core" % EmmVersion

libraryDependencies += "org.typelevel" %% "cats" % "0.4.1"
