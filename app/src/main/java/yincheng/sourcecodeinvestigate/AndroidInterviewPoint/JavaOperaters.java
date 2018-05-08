package yincheng.sourcecodeinvestigate.AndroidInterviewPoint;

/**
 * Created by yincheng on 2018/5/8/9:55.
 * github:luoyincheng
 */
public class JavaOperaters {
    /**
     * ## “&” 和 “&&” 的区别
     * 1. & 支持位运算， && 不支持位运算
     * 2. && 具有短路功能(即如果第一个表达式为false，则不再计算第二个表达式以及后面的表达式，如果遇到true就一直向下判断条件直到遇到false的表达式返回)， & 没有短路功能
     * 3. & 和 && 都可以作为逻辑与的运算符，当两边的结果都为true时，结果才为true
     */
    public static void main(String[] args) {
        /**
         * “0x0f”经常用来与一个整数进行“&”运算，来获取该整数最低位的4个bit位
         */
        System.out.println(0x0f & 0x31);
        /**
         * 十进制转为十六进制
         */
        System.out.println(Integer.toHexString(49));
        /**
         * 十六进制转为十进制
         */
        System.out.println(Integer.parseInt("31", 16));

        {
            int a = 1;
            if (a == 1 && getBooleanResult()) {
                System.out.println("执行通过1");
            }
            if (a == 2 && getBooleanResult()) {//短路了
                System.out.println("执行通过2");
            }
            if (a == 1 & getBooleanResult()) {
                System.out.println("执行通过3");
            }
            if (a == 2 & getBooleanResult()) {//未短路
                System.out.println("执行通过4");
            }
        }

        System.out.println(Integer.toBinaryString(0x31));
        {
            int a = 0x2f;//0x表示16进制
            int b = 010;//0开头代表8进制
            char c = 0xff;//char为两个字节，16位
            byte d = 0xf;//byte为8位
            short e = 0xff;//short为16位
            System.out.println(Integer.toBinaryString(a));
            System.out.println(Integer.toBinaryString(b));
            System.out.println(Integer.toBinaryString(c));
            System.out.println(Integer.toBinaryString(d));
            System.out.println(Integer.toBinaryString(e));
        }
    }

    private static boolean getBooleanResult() {
        System.out.println("getBooleanResult()执行了");
        return true;
    }
}
