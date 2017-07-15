# JFastStringSplitter
Fast string splitter for Java applications.

## Installation
1. `git clone https://github.com/darshanparajuli/JFastStringSplitter.git`
2. `cd JFastStringSplitter`
3. `mvn clean install -DskipTests`
    * `mvn clean install` (optional; running tests might take some time)
4. Import it like following:
~~~~
<dependency>
    <groupId>com.dp.stringutils</groupId>
    <artifactId>fast-string-splitter</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
~~~~
## Usage
~~~~
StringSplitter.splitOnEmptySequence("   a b c    d  "); // result: ["a", "b", "c", "d"]
StringSplitter.split("axbxxxcxd", "xxx"); // result: ["axb", "cxd"]
~~~~
## But why?
I realized I was using String.split() method quite a bit in one of my Java applications and almost all of the use cases did not require regex, but a greedy approach.
~~~~
// For example, this will result in [a, b] instead of [a, , , b].
StringSplitter.split("axxxb", "x"); 
~~~~
So, I wrote this thinking it would be faster than Java's String.split() for non-regex delimiters and add a little performance boost to my application. According to my tests/benchmarks, it seems like it indeed is faster, which is why I thought I'd share it. Cheers!

# License
MIT License

Copyright (c) 2017 Darshan Parajuli

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
