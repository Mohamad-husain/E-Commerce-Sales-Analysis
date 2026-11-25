# E-Commerce Sales Analysis

A Big Data Engineering project by **Team T18** at An-Najah National University. The project helps online store managers identify peak sales hours and see which countries have the most purchasing customers.

## Data Source

The project uses the [UCI Online Retail dataset](https://archive.ics.uci.edu/dataset/352/online-retail), which contains approximately 542,000 historical invoice lines. The pipeline will replay these records at a controlled rate while preserving their original invoice timestamps.

Download the full dataset separately and store it locally in `data/`.

## Planned Architecture

| Stage | Role |
| --- | --- |
| **Apache Kafka** | Receives historical invoice lines replayed as transaction events. |
| **Spark Structured Streaming** | Validates records, excludes cancellations from sales totals, and groups transactions into one-hour windows by country. |
| **MongoDB** | Stores hourly sales totals and estimated distinct customer counts by country. |
| **Spark SQL Batch** | Reads stored summaries and relates hourly sales to daily country totals. |
| **Dashboard** | Shows hourly sales trends and compares customer activity across countries. |

The streaming job will use a **10-minute watermark** to handle late events. It will estimate distinct customer counts with **HyperLogLog++** and compare the estimates with exact counts on the same input. The evaluation will report relative error, memory usage, and elapsed time.

## Current Implementation

The project contains the Scala and sbt setup and a local Spark startup check in `src/main/scala/Main.scala`, with `Main` as the entry point. The Kafka producer, sales analysis jobs, MongoDB integration, and dashboard are planned for the next phase.

## Getting Started

### Requirements

- **JDK 21**, with `JAVA_HOME` set and Java on `PATH`
- An **sbt** launcher on `PATH`; version **1.10.3** is pinned in `project/build.properties`
- **Scala 2.13.18**, resolved by sbt
- **Apache Spark 4.2.0**, built for Scala 2.13, with `SPARK_HOME` set to its installation directory
- Network access for the first dependency download

### Build and Run on Windows

Open PowerShell in the project root and run:

```powershell
sbt package
& "$env:SPARK_HOME\bin\spark-submit.cmd" --class Main --master "local[2]" ".\target\scala-2.13\ecommerce-sales-analysis_2.13-0.1.0-SNAPSHOT.jar"
```

The JAR name matches the project name, Scala binary version, and `0.1.0-SNAPSHOT` version in `build.sbt`; Spark supplies its runtime dependencies through `spark-submit`.

If Windows reports `ServerAlreadyBootingException` with named-pipe lock error 5, use this command for the build, then run the same `spark-submit` command:

```powershell
sbt "-Dsbt.server.forcestart=true" "-Dsbt.server.autostart=false" package
```

These options apply only to that invocation and do not change the project configuration.

In IntelliJ IDEA, import the project as an sbt project, select JDK 21 for both the project and sbt JVM, and run `package` in **sbt Shell**, then run the `spark-submit` command in PowerShell.

The Spark startup check was verified with `local[2]`: it printed an `id` column containing `1`, `2`, and `3` and exited successfully without a dataset, Kafka, or MongoDB.

## Contributing

Create a branch for your task and open a pull request into `main` for team review. Keep the full dataset, credentials, IDE settings, and generated files out of Git.
