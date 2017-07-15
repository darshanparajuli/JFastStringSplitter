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

@RunWith(Parameterized.class)
public class StringSplitterTest {

    private String mTestCase;
    private String mDelim;
    private String[] mExpected;

    public StringSplitterTest(String testCase, String delim, String[] expected) {
        mTestCase = testCase;
        mDelim = delim;
        mExpected = expected;
    }

    @Parameterized.Parameters(name = "{index}: \"{0}\" split on \"{1}\"")
    public static Collection<Object[]> data() throws URISyntaxException, IOException {
        final URI uri = StringSplitterTest.class.getResource("../../../string_splitter_test_cases.txt").toURI();
        final List<String> lines = Files.readAllLines(Paths.get(uri), Charset.defaultCharset());

        final List<Object[]> result = new ArrayList<>();
        for (String line : lines) {
            final int firstIndex = line.indexOf("/");
            final int secondIndex = line.indexOf("/", firstIndex + 1);

            final String testCase = firstIndex == 0 ? "" : line.substring(0, firstIndex);
            final String delim = secondIndex - firstIndex == 1 ? "" : line.substring(firstIndex + 1, secondIndex);
            final String expected = secondIndex == line.length() - 1 ? "" : line.substring(secondIndex + 1);
            final String[] expectedTokens = expected.isEmpty() ? new String[]{} : expected.split(",");

//            System.out.println(testCase + "|" + delim + "|" + Arrays.toString(expectedTokens));

            result.add(new Object[]{testCase, delim, expectedTokens});
        }

        return result;
    }

    @Test
    public void testStringSplit() {
        final String[] result = StringSplitter.split(mTestCase, mDelim);
        Assert.assertArrayEquals(mExpected, result);
    }

}
