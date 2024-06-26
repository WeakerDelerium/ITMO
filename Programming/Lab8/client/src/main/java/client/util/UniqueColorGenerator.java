package client.util;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UniqueColorGenerator {
    private static final ArrayList<Color> busyColors = new ArrayList<>();

    public static ArrayList<Color> generateColors(Integer amount) {
        busyColors.clear();

        for (int i = 0; i < amount; i++) generateAnotherOneColor();

        return busyColors;
    }

    public static Color generateAnotherOneColor() {
        Color color = getColor(generateRGB());

        if (busyColors.stream().anyMatch(busyColor -> equalsColor(busyColor, color))) return generateAnotherOneColor();

        busyColors.add(color);

        return color;
    }

    public static void releaseColor(Color color) {
        busyColors.remove(color);
    }

    private static List<Number> generateRGB() {
        return Stream.concat(ThreadLocalRandom.current().ints(3, 0, 256).boxed(),
                Stream.of(ThreadLocalRandom.current().nextDouble(0.5, 0.75))).collect(Collectors.toList());
    }

    private static Color getColor(List<Number> rgb) {
        int r = (int) rgb.get(0);
        int g = (int) rgb.get(1);
        int b = (int) rgb.get(2);

        double o = (double) rgb.get(3);

        return Color.rgb(r, g, b, o);
    }

    private static boolean equalsColor(Color fir, Color sec) {
        return fir.getRed() == sec.getRed() && fir.getBlue() == sec.getBlue() && fir.getGreen() == sec.getGreen();
    }
}
