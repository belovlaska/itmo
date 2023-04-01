import java.lang.Math.*;
import java.util.Random;

public class Lab1_prog {
    static final short amount1 = 7;
    static final short amount2 = 16;
    static final float Max_secondArray = 14.0F;
    static final float Min_secondArray = -2.0F;

    public static void main(String[] args) {
        Random rand = new Random();
        short[] a = new short[amount1];
        for (int i = 0; i < amount1; i++)
            a[i] = (short) (amount1 + i * 2);
        float[] x = new float[amount2];
        for (int i = 0; i < amount2; ++i) {
            x[i] = rand.nextFloat() * (Max_secondArray - Min_secondArray) + Min_secondArray;
        }
        /*
        for (int i = 0; i < amount2; ++i){
            System.out.println(x[i]);
        }
         */
        double[][] result_array = new double[amount1][amount2];
        for (int i = 0; i < amount1; i++) {
            short num = a[i];
            for (int j = 0; j < amount2; ++j) {
                if (num == 13) {
                    result_array[i][j] = Math.asin(Math.sin(x[j]));
                } else if (num == 7 || num == 9 || num == 17) {
                    result_array[i][j] = Math.asin(Math.pow((1 / Math.pow(Math.E, Math.abs(x[j]))), 2));
                } else {
                    result_array[i][j] = Math.tan(Math.cbrt(Math.pow(2 * x[j], ((1 - 3 * x[j]) / 2))));
                }
            }
        }
        for (int i = 0; i < amount1; i++) {
            for (int j = 0; j < amount2 - 1; ++j) {
                System.out.printf("%10.3f ", result_array[i][j]);
            }
            System.out.printf("%10.3f\n", result_array[i][amount2 - 1]);
        }
    }
}