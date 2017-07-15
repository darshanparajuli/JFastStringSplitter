package com.dp.stringutil;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

@RunWith(Parameterized.class)
public class PerformanceTest {

    private String mTestCase;

    @Parameterized.Parameters(name = "{index}: {0}")
    public static Collection<Object[]> data() throws URISyntaxException, IOException {
        final URI uri = PerformanceTest.class.getResource("../../../long_text.txt").toURI();
        final List<String> lines = Files.readAllLines(Paths.get(uri), Charset.defaultCharset());
        final List<Object[]> result = new ArrayList<>();
        final Random random = new Random();
        for (String s : lines) {
            result.add(new Object[]{s});

            if (s.length() > 0) {
                result.add(new Object[]{s.substring(0, random.nextInt(s.length()))});
            }
        }
        return result;
    }

    public PerformanceTest(String testCase) {
        mTestCase = testCase;
    }

    @SuppressWarnings("Duplicates")
    @Test
    public void testPerformance() {
        final Timer timer = new Timer();

        final int N = 10000;
        final List<Float> stringSplitTimes = new ArrayList<>();
        final List<Float> splitOnEmptySequenceTimes = new ArrayList<>();

        // warm up
        for (int i = 0; i < 1000; i++) {
            timer.start();
            mTestCase.split("\\s+");
            timer.stop();
        }

        for (int i = 0; i < 1000; i++) {
            timer.start();
            StringSplitter.splitOnEmptySequence(mTestCase);
            timer.stop();
        }

        for (int i = 0; i < N; i++) {
            timer.start();
            mTestCase.split("\\s+");
            timer.stop();

            stringSplitTimes.add(timer.getElapsedTime());
        }

        for (int i = 0; i < N; i++) {
            timer.start();
            StringSplitter.splitOnEmptySequence(mTestCase);
            timer.stop();

            splitOnEmptySequenceTimes.add(timer.getElapsedTime());
        }

        float avgStringSplitTime = 0.0f;
        float avgSplitOnEmptySequenceTime = 0.0f;
        for (int i = 0; i < N; i++) {
            avgStringSplitTime += stringSplitTimes.get(i);
            avgSplitOnEmptySequenceTime += splitOnEmptySequenceTimes.get(i);
        }
        avgStringSplitTime /= N;
        avgSplitOnEmptySequenceTime /= N;

        final float percentFaster = ((avgStringSplitTime - avgSplitOnEmptySequenceTime) / avgStringSplitTime) * 100.0f;
        System.out.printf("String.split time: %.6f ms, StringSplitter.splitOnEmptySequence time: %.6f ms," +
                " %.2f%% faster\n", avgStringSplitTime, avgSplitOnEmptySequenceTime, percentFaster);
        Assert.assertTrue(avgSplitOnEmptySequenceTime < avgStringSplitTime);
    }

    private static class Timer {

        private long mStartTime;
        private long mStopTime;

        Timer() {
            reset();
        }

        void reset() {
            mStartTime = 0L;
            mStopTime = 0L;
        }

        void start() {
            mStartTime = System.nanoTime();
        }

        void stop() {
            mStopTime = System.nanoTime();
        }

        float getElapsedTime() {
            return (mStopTime - mStartTime) / 1000000.0f;
        }
    }
}
