import java.util.Arrays;

import org.ganimede.utils.RndUtils;

public class TestPrognosticos {
    public static void main(String[] args) {
        int m = 5;
        int n = 80;

        Integer[] result = new Integer[m];
        for (int i = 0; i < m; i++) {
            while (result[i] == null) {
                int p = RndUtils.nextInt(n);
                if (!Arrays.asList(result).contains(p)) {
                    result[i] = p;
                }
            }
        }
        
        System.out.println(Arrays.toString(result));
    }
}
