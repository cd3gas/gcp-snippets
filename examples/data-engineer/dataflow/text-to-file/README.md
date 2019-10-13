<img src="https://avatars1.githubusercontent.com/u/48249676?s=200&v=4" alt="Google Cloud Platform logo" title="Google Cloud Platform" align="right" height="96" width="96"/>

# GDG Cloud Santiago [![Slack][slack_badge]][slack_link]

[slack_badge]: https://img.shields.io/badge/slack-gcp-E01563.svg?style=flat
[slack_link]: https://join.slack.com/t/gdgcloudscl/shared_invite/enQtNDg4NjQ2NTE3NDkwLThhMTI0NmQ5NDhjMGRhMzJhNmQwZDEzNWRlNzIzMTA3YWNjMWUyY2Q2OTg1ZTk4OTZiYmNiMDU1MWNjMWZjOTM

# GCP Snippets / Data Engineer / Dataflow / Text To File Pipeline

1) Para ejecutar el proceso en local:

mvn compile exec:java -Dexec.mainClass=snippets.dataengineer.dataflow.TextToFilePipeline

2) Explicación del proceso:

Inicializar Pipeline.

```
        PipelineOptions options = PipelineOptionsFactory.create();
        Pipeline pipeline = Pipeline.create(options);

```
Leer archivo de entrada y obtener una lista PCollection de líneas del archivo.

```

PCollection<String> lines = pipeline.apply("Read from file:", TextIO.read().from(INPUT_FILE_PATH));

```
Escribir x cantidad de archivos con una línea por cada línea existente en el archivo de entrada.

```

lines.apply("Write to file:", TextIO.write().to(OUTPUT_FILE_NAME));

```
Ejecutar Pipeline.

```
pipeline.run().waitUntilFinish();

```