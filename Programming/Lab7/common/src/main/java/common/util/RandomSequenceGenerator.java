package common.util;

import java.util.concurrent.ThreadLocalRandom;

public class RandomSequenceGenerator {
    public static String generate() {
        return generate(ThreadLocalRandom.current().nextInt(25, 75 + 1));
    }

    public static String generate(Integer seqLen) {
        return ThreadLocalRandom.current()
                .ints(seqLen, 0x21, 0x7F)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}
