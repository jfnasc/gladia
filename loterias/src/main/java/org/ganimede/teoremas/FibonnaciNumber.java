package org.ganimede.teoremas;

public class FibonnaciNumber {
    public static void main(String[] args) {
        int limit = 11;

        int[] f = new int[limit];
        f[0] = 0;
        f[1] = 1;
        f[2] = 1;

        // Fn = (Fn - 2) + (Fn - 1)
        for (int i = 3; i < limit; i++) {
            f[i] = f[i - 2] + f[i - 1];
        }

        for (int i = 0; i < f.length; i++) {
            System.out.print(f[i] + ",");
        }
    }
}
