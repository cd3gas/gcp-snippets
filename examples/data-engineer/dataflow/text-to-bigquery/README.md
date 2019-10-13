<img src="https://avatars1.githubusercontent.com/u/48249676?s=200&v=4" alt="Google Cloud Platform logo" title="Google Cloud Platform" align="right" height="96" width="96"/>

# GDG Cloud Santiago [![Slack][slack_badge]][slack_link]

[slack_badge]: https://img.shields.io/badge/slack-gcp-E01563.svg?style=flat
[slack_link]: https://join.slack.com/t/gdgcloudscl/shared_invite/enQtNDg4NjQ2NTE3NDkwLThhMTI0NmQ5NDhjMGRhMzJhNmQwZDEzNWRlNzIzMTA3YWNjMWUyY2Q2OTg1ZTk4OTZiYmNiMDU1MWNjMWZjOTM

# GCP Snippets / Data Engineer / Dataflow / Text To BigQuery Pipeline

1) Para ejecutar el proceso en local:

mvn compile exec:java -Dexec.mainClass=snippets.

2) Explicación del proceso:

Inicializar Pipeline.

```
        PipelineOptions options = PipelineOptionsFactory.create();
        Pipeline pipeline = Pipeline.create(options);

```