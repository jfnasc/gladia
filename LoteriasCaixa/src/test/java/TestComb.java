import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.ganimede.utils.MathUtils;

public class TestComb<E> implements Collection<E>{
    public static void main(String[] args) {
        comb(3, 2);
        comb(5, 2);
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

        return null;
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

    @Override
    public int size() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public boolean isEmpty() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean contains(Object o) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Object[] toArray() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean add(E e) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean remove(Object o) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void clear() {
        // TODO Auto-generated method stub
        
    }
}
