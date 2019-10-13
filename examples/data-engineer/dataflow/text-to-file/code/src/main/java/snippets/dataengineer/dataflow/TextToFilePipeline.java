package snippets.dataengineer.dataflow;

import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.options.PipelineOptions;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.values.PCollection;

public class TextToFilePipeline {

    public static void main(String... args){

        final String INPUT_FILE_PATH = "input.txt";
        final String OUTPUT_FILE_NAME = "output";

        PipelineOptions options = PipelineOptionsFactory.create();
        Pipeline pipeline = Pipeline.create(options);

        PCollection<String> lines = pipeline.apply("Read from file:", TextIO.read().from(INPUT_FILE_PATH));

        lines.apply("Write to file:", TextIO.write().to(OUTPUT_FILE_NAME));

        pipeline.run().waitUntilFinish();

    }

}