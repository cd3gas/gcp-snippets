<img src="https://avatars1.githubusercontent.com/u/48249676?s=200&v=4" alt="Google Cloud Platform logo" title="Google Cloud Platform" align="right" height="96" width="96"/>

# GDG Cloud Santiago [![Slack][slack_badge]][slack_link]

[slack_badge]: https://img.shields.io/badge/slack-gcp-E01563.svg?style=flat
[slack_link]: https://join.slack.com/t/gdgcloudscl/shared_invite/enQtNDg4NjQ2NTE3NDkwLThhMTI0NmQ5NDhjMGRhMzJhNmQwZDEzNWRlNzIzMTA3YWNjMWUyY2Q2OTg1ZTk4OTZiYmNiMDU1MWNjMWZjOTM

# GCP Snippets / Data Engineer / Dataflow / WordCountToFile

```
        PipelineOptions options = PipelineOptionsFactory.create();
        Pipeline pipeline = Pipeline.create(options);

```

```
		PCollection<String> lines = pipeline.apply("Read from file", TextIO.read().from(INPUT_FILE_PATH));
        
```

```
        PCollection<List<String>> wordsPerLine = lines.apply(MapElements.via(new SimpleFunction<String, List<String>>() {

            @Override
            public List<String> apply(String input){
                return Arrays.asList(input.split(" "));
            }

        }));

```

```
        PCollection<String> words =  wordsPerLine.apply(Flatten.<String>iterables());

```

```

        PCollection<KV<String,Long>> wordCount =  words.apply(Count.<String>perElement());

```

```

        PCollection<String> formatWordCount = wordCount.apply(MapElements.via(new SimpleFunction<KV<String, Long>,String>() {

            @Override
            public String apply(KV<String,Long> input){

                return String.format("%s => %s",input.getKey(),input.getValue());

            }

        }));

```

```

        formatWordCount.apply(TextIO.write().to(OUTPUT_FILE_NAME));

```

```

		pipeline.run().waitUntilFinish();

```
