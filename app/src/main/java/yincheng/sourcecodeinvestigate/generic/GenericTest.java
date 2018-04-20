package yincheng.sourcecodeinvestigate.generic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


/**
 * Created by yincheng on 2018/4/19.
 */

public class GenericTest {
    public static <T> List<T> makeList(T... args) {
        List<T> resut = new ArrayList<>();
        Collections.addAll(resut, args);
        return resut;
    }

    public static void test(){
    }
}
