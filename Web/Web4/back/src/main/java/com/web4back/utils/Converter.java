package ru.web4back.utils;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.function.Function;

public class Converter {
    public static final Function<Long, Timestamp> secTimeShift = secs -> Timestamp.from(Instant.now().plusSeconds(secs));
}
