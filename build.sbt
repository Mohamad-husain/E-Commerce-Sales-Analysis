ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / scalaVersion := "2.13.18"

lazy val root = (project in file("."))
  .settings(
    name := "ecommerce-sales-analysis",
    libraryDependencies += "org.apache.spark" %% "spark-sql" % "4.2.0"
  )