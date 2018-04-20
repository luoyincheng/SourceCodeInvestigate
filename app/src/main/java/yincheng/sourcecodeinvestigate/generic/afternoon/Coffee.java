package yincheng.sourcecodeinvestigate.generic.afternoon;

/**
 * Created by yincheng on 2018/4/19.
 */

public class Coffee {
    private static long counter = 0;
    private final long id = counter++;

    @Override
    public String toString() {
        return getClass().getSimpleName() + " " + id;
    }
}
