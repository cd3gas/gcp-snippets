<img src="https://avatars1.githubusercontent.com/u/48249676?s=200&v=4" alt="Google Cloud Platform logo" title="Google Cloud Platform" align="right" height="96" width="96"/>

# GDG Cloud Santiago [![Slack][slack_badge]][slack_link]

[slack_badge]: https://img.shields.io/badge/slack-gcp-E01563.svg?style=flat
[slack_link]: https://join.slack.com/t/gdgcloudscl/shared_invite/enQtNDg4NjQ2NTE3NDkwLThhMTI0NmQ5NDhjMGRhMzJhNmQwZDEzNWRlNzIzMTA3YWNjMWUyY2Q2OTg1ZTk4OTZiYmNiMDU1MWNjMWZjOTM

# GCP Snippets / Data Engineer / Dataflow / Text To File WordCount Pipeline

1) Para ejecutar el proceso en local:

mvn compile exec:java -Dexec.mainClass=snippets.dataengineer.dataflow.TextToFileWordCountPipeline

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

Transformar una lista PCollection de líneas del archivo a una lista PCollection de listas de las palabras que existen en cada línea.

```
        PCollection<List<String>> wordsPerLine = lines.apply(MapElements.via(new SimpleFunction<String, List<String>>() {

            @Override
            public List<String> apply(String input){
                return Arrays.asList(input.split(" "));
            }

        }));

```

Transformar una lista PCollection de listas de las palabras que existen en cada línea a una sola lista PCollection con todas las palabras que existen en el archivo de entrada.


```
        PCollection<String> words =  wordsPerLine.apply(Flatten.<String>iterables());

```

Transformar una lista PCollection con todas las palabras que existen en el archivo de entrada a una lista PCollection "llave,valor" donde la llave es la palabra  y el valor es la cantidad de veces que aparece en el archivo.

```

        PCollection<KV<String,Long>> wordCount =  words.apply(Count.<String>perElement());

```

Transformar una lista PCollection "llave,valor" a una lista PCollection con un String formateado que indica cuantas veces aparece una palabra en el archivo.

```

        PCollection<String> formatWordCount = wordCount.apply(MapElements.via(new SimpleFunction<KV<String, Long>,String>() {

            @Override
            public String apply(KV<String,Long> input){

                return String.format("%s => %s",input.getKey(),input.getValue());

            }

        }));

```

Escribir x cantidad de archivos con una línea por cada conteo de palabras en el archivo.

```

        formatWordCount.apply("Write to file:", TextIO.write().to(OUTPUT_FILE_NAME));
	

```
o especificar cuantos archivos generar
XX  indica numero de archivos

```

        formatWordCount.apply(TextIO.write().to(OUTPUT_FILE_NAME).withNumShards(XX));
	

```

Ejecutar Pipeline.

```

		pipeline.run().waitUntilFinish();

```
