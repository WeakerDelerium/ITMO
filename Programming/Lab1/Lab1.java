import java.util.Random;
import static java.lang.Math.*;


public class Lab1 {

    public static void main(String[] args) {

        short[] c = new short[18];

        for (int i = 0; i < 18; i++) {
            c[i] = (short) (i + 2);
        }

        double[] x = new double[11];
        Random rand = new Random();

        for (int i = 0; i < 11; i++) {
            x[i] = rand.nextDouble(-15, 13);
        }

        double[][] array = new double[18][11];

        for (int i = 0; i < 18; i++) {
            for (int j = 0; j < 11; j++) {
                switch (c[i]) {
                    case 8:
                        array[i][j] = pow(sin(x[j]), (log(abs(x[j])) - 0.75) / (pow(x[j], x[j] / 2)) * (log(pow(2 * (2 * PI + abs(x[j])), 2))));
                        break;
                    case 3, 5, 7, 10, 12, 14, 16, 17, 18:
                        array[i][j] = pow(0.25 * pow(0.25 * (1 - pow(0.75 / (x[j] + 0.25), 3)), 2), 3);
                        break;
                    default:
                        array[i][j] = cos(atan(pow(E, cbrt(-sqrt(abs(x[j]))))));
                }
            }
        }

        for (double[] elArray : array) {
            for (double el : elArray) {
                System.out.printf("%.2f\t", el);
            }
            System.out.println();
        }
    }
}


