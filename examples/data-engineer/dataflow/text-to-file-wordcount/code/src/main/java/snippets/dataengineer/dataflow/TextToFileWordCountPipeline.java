package snippets.dataengineer.dataflow;

import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.options.PipelineOptions;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.transforms.Count;
import org.apache.beam.sdk.transforms.Flatten;
import org.apache.beam.sdk.transforms.MapElements;
import org.apache.beam.sdk.transforms.SimpleFunction;
import org.apache.beam.sdk.values.KV;
import org.apache.beam.sdk.values.PCollection;

import java.util.Arrays;
import java.util.List;

public class TextToFileWordCountPipeline {

    public static void main(String... args){

        final String INPUT_FILE_PATH = "input.txt";
        final String OUTPUT_FILE_NAME = "output";

        PipelineOptions options = PipelineOptionsFactory.create();
        Pipeline pipeline = Pipeline.create(options);

        PCollection<String> lines = pipeline.apply("Read from file:", TextIO.read().from(INPUT_FILE_PATH));

        PCollection<List<String>> wordsPerLine = lines.apply(MapElements.via(new SimpleFunction<String, List<String>>() {

            @Override
            public List<String> apply(String input){
                return Arrays.asList(input.split(" "));
            }

        }));

        PCollection<String> words =  wordsPerLine.apply(Flatten.<String>iterables());

        PCollection<KV<String,Long>> wordCount =  words.apply(Count.<String>perElement());

        PCollection<String> formatWordCount = wordCount.apply(MapElements.via(new SimpleFunction<KV<String, Long>,String>() {

            @Override
            public String apply(KV<String,Long> input){

                return String.format("%s => %s",input.getKey(),input.getValue());

            }

        }));

        formatWordCount.apply("Write to file:", TextIO.write().to(OUTPUT_FILE_NAME));

        pipeline.run().waitUntilFinish();

    }

}