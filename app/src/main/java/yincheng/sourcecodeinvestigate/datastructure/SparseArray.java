package yincheng.sourcecodeinvestigate.datastructure;


/**
 * Created by yincheng on 2018/5/8/15:08.
 * github:luoyincheng
 */
public class SparseArray {
//    使用SparseArray和ArrayMap来代替HashMap。
    /**
     * 1. SparseArray比HashMap更省内存，在某些条件下性能更好，主要是因为它避免了对key的自动装箱(int转为
     *    integer类型)，它的内部通过两个数组进行数据存储，一个存储key，一个存储value，为了优化性能，它的内部对数
     *    据采用了压缩的方式来表示稀疏数组的数据，从而节省内存空间。
     * 2. SparseArray只能存储key为int类型的数据，同时，SparseArray在存储和读取数据的时候，使用的是二分查找法，
     * 3. put添加数据的时候，会使用二分查找法和之前的key比较当前我们添加的元素的key的大小，然后按照从小到大的顺
     *    序排列好，所以，SparseArray存储的元素都是按元素的key值从小到大排列好的。
     *    而在获取数据的时候，也是使用二分查找法判断元素的位置，所以，在获取数据的时候非常快，比HashMap快的多，
     *    因为HashMap获取数据是通过遍历Entry[]数组来得到对应的元素。
     */
    android.util.SparseArray fadf = new android.util.SparseArray();
}
