ThisBuild / version := "Interprete Lambda"

ThisBuild / scalaVersion := "3.1.3"

lazy val root = (project in file("."))
  .settings(
    name := "InterpreteLambda"
  )

scalacOptions += "-encoding"
scalacOptions += "UTF-8"