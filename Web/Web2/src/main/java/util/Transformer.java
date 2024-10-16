package util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.function.Function;

public class Transformer {
    public static Function<Double, Double> doubleTransform = value ->
            BigDecimal.valueOf(value).setScale(value > 10 ? 0 : 2, RoundingMode.HALF_UP).doubleValue();

    public static Function<Boolean, String> booleanTransform = value -> value ? "Y" : "N";

    private Transformer() {}
}
