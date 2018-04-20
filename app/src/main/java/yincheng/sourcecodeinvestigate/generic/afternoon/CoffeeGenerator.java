package yincheng.sourcecodeinvestigate.generic.afternoon;

import android.support.annotation.NonNull;

import java.util.Iterator;
import java.util.Random;

/**
 * Created by yincheng on 2018/4/19.
 */

public class CoffeeGenerator implements Generator<Coffee>, Iterable<Coffee> {

    private Class[] Types = new Class[]{Latte.class, Mocha.class, Cappuccino.class, Breve.class, Americano.class};
    private static Random random = new Random(47);

    public CoffeeGenerator() {
    }

    private int size = 0;

    public CoffeeGenerator(int sz) {
        size = sz;
    }

    @NonNull
    @Override
    public Iterator<Coffee> iterator() {
        return new CoffeeIterator();
    }

    @Override
    public Coffee next() {
        try {
            return (Coffee) Types[random.nextInt(Types.length)].newInstance();
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    class CoffeeIterator implements Iterator<Coffee> {

        int count = size;

        public boolean hasNext() {
            return count > 0;
        }

        public Coffee next() {
            count--;
            return CoffeeGenerator.this.next();
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
