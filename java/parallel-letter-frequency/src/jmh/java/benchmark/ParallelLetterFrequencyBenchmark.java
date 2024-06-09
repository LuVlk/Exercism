package benchmark;

import org.openjdk.jmh.annotations.*;
import org.volke.exercism.ParallelLetterFrequency;

import java.util.concurrent.TimeUnit;


@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class ParallelLetterFrequencyBenchmark {

    private ParallelLetterFrequency parallelLetterFrequency;

    @Param({"100", "1000", "10000"})
    private int size;

    @Setup
    public void setup() {
        String[] texts = new String[size];
        for (int i = 0; i < size; i++) {
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < 100; j++) {
                sb.append("abcdefghijklmnopqrstuvwxyz");
            }
            texts[i] = sb.toString();
        }
        parallelLetterFrequency = new ParallelLetterFrequency(texts);
    }

    @Benchmark
    public void countLetters() {
        parallelLetterFrequency.countLetters();
    }
}
