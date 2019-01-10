package com.hacker.java8.streams;

import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author hacker
 * @date 2019/1/10
 * @describe
 */
public class StreamDebugger {

    @Test
    public void testStream1() {
        /**
         * <pre>
         * 上面的代码创建了一个由字符串 “A”、“B”、“C”组成的 Stream。紧接着对这个 Stream 进行 sorted() 操作，
         * 从而创建了一个新的 Stream（至少在 Java 8-10 中是这样），其中的元素是第一个 Stream 的元素按字母排序的结果。
         * 也就是说，第二个 Stream 包含“A”、“B”、“C”三个元素。最后，这些将元素放到一个 List 中。
         * </pre>
         */
        List<String> collect = Stream.of("C", "A", "B")
                .sorted()
                .collect(Collectors.toList());

        /**
         * 上面的代码 等价于：
         */
        Stream<String> s0 = Stream.of("C", "A", "B"); // "C", "A", "B"
        Stream<String> s1 = s0.sorted();              // "A", "B", "C"
        List<String> strings = s1.collect(Collectors.toList());  // ["A", "B", "C"]
    }
}
