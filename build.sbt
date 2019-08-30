import Dependencies._

name := "retirement-calculator"

version := "0.1"

scalaVersion := "2.12.8"

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % Versions.scalaTest % Test
)