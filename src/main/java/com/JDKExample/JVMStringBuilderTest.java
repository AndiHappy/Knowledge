package com.JDKExample;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

/**
 * 跑起来非常的耗费CPU
 * */

@BenchmarkMode(Mode.Throughput)
@Warmup(iterations = 1)
@Measurement(iterations = 2, time = 2, timeUnit = TimeUnit.SECONDS)
@Threads(5)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class JVMStringBuilderTest {
 
    @Benchmark
    public void testStringAdd() {
        String a = "";
        for (int i = 0; i < 10; i++) {
            a += i;
        }
        print(a);
    }
 
    @Benchmark
    public void testStringBuilderAdd() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            sb.append(i);
        }
        print(sb.toString());
    }
 
    private void print(String a) {
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder ()
            .include(JVMStringBuilderTest.class.getSimpleName())
            .forks(1)
            .warmupIterations(5)
            .measurementIterations(5)
            .build();

        new Runner (opt).run();
    }
}