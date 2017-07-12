import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.ganimede.utils.MathUtils;

public class TestComb {
    public static void main(String[] args) {
        List<Integer[]> p = comb(15, 9);
        for (Integer[] i : p) {
            System.out.println(Arrays.toString(i));
        }
        //comb(5, 2);
    }

    public static List<Integer[]> comb(int m, int n) {
        List<Integer[]> result = new ArrayList<>();

        Integer[] a = new Integer[n];
        for (int i = 0; i < n; i++) {
            a[i] = i;
        }

        int count = 0;
        int index = -1;

        while ((index = nextIndex(m, n, a)) != -1) {
            result.add(Arrays.copyOf(a, a.length));
            count++;
            move(index, a);
        }

        result.add(Arrays.copyOf(a, a.length));
        count++;

        System.out.println(MathUtils.csimples(m, n));
        System.out.println(count);
        System.out.println(MathUtils.csimples(m, n) == count);

        return result;
    }

    private static void move(int index, Integer[] a) {
        a[index] = a[index] + 1;
        if (index < a.length - 1) {
            for (int i = index; i < a.length - 1; i++) {
                a[i + 1] = a[i] + 1;
            }
        }
    }

    private static int nextIndex(int m, int n, Integer[] a) {
        for (int i = n - 1; i > -1; i--) {
            if (a[i] < (m - (a.length - i))) {
                return i;
            }
        }
        return -1;
    }
}
