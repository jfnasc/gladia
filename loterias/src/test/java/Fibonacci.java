import java.text.DecimalFormat;

import org.ganimede.TiposConcurso;
import org.ganimede.dao.ConcursoDAO;
import org.ganimede.dao.impl.ConcursoDAOImpl;

public class Fibonacci {

    public static void main(String[] args) {
        DecimalFormat df = new DecimalFormat("##0.00");

        ConcursoDAO dao = new ConcursoDAOImpl();
        for (int i = 1; i <= 1716; i++) {
            Integer[] a = dao.recuperarConcurso(i, TiposConcurso.QUINA).getSorteios().get(0).getDezenasAsArray();
            for (int k = a.length - 1; k > 0; k--) {
                System.out.print(df.format(Float.valueOf(a[k]) / Float.valueOf(a[k - 1])) + "\t");
            }
            System.out.println();
        }
    }

    public static void main2(String[] args) {
        long[] a = seq();
        for (int i = a.length - 1; i > 0; i--) {
            System.out.println(Float.valueOf(a[i]) / Float.valueOf(a[i - 1]));
        }

    }

    public static long calc(long total) {
        System.out.println(total);
        while ((total = tese(total)) > 9) {
        }
        return total;
    }

    public static long tese(long arg) {
        char[] a = String.valueOf(arg).toCharArray();
        long total = 0;
        for (int i = 0; i < a.length; i++) {
            total += Integer.valueOf(Character.toString(a[i]));
        }

        return total;
    }

    public static long[] seq() {
        int a = 0;
        int b = 1;
        int z = 50;

        long[] c = new long[z];
        for (int i = 0; i < z; i++) {
            c[i] = i;
        }

        c[0] = a;
        c[1] = b;

        for (int i = 2; i < c.length; i++) {
            c[i] = c[i - 2] + c[i - 1];
        }

        return c;
    }
}
