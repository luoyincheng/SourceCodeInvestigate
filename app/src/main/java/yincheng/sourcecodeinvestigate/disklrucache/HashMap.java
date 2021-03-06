package yincheng.sourcecodeinvestigate.disklrucache;

import java.util.AbstractMap;
import java.util.Set;

/**
 * Created by yincheng on 2018/5/7/17:20.
 * github:luoyincheng
 */
public class HashMap<K, V> extends AbstractMap<K, V> {

    {
        java.util.HashMap hashMap = new java.util.HashMap();
        hashMap.put("a", "a");
        hashMap.get("a");

    }

    /**
     * ## HashMap中的链表中的Node是Map(Node<K,V> implements Map.Entry<K,V>)
     * ## HashMap中key和value都允许为null。
     * ## put: 如果key不为null，则同样先求出key的hash值，根据hash值得出在table中的索引，而后遍历对应的单链表，
     * 如果单链表中存在与目标key相等的键值对，则将新的value覆盖旧的value，比将旧的value返回，如果找不到与目标
     * key相等的键值对，或者该单链表为空，则将该键值对插入到改单链表的头结点位置（每次新插入的节点都是放在头结点
     * 的位置）
     * <p>
     * 1. HashMap由数组和单链表组成，数组的每个元素都是一个单链表的头节点，链表是用来解决冲突的，如果不同的key映
     * 射到了数组的同一位置处，就将其放入到单链表中
     * 2. HashMap共有四个构造方法。构造方法中提到了两个很重要的参数：初始容量和加载因子。这两个参数是影响HashMap
     * 性能的重要参数，其中容量表示哈希表中槽的数量（即哈希数组的长度），初始容量是创建哈希表时的容量
     * （从构造函数中可以看出，如果不指明，则默认为16），加载因子是哈希表在其容量自动增加之前可以达到多满的
     * 一种尺度，当哈希表中的条目数超出了加载因子与当前容量的乘积时，则要对该哈希表进行 resize 操作（即扩容）。
     * 3. 如果加载因子越大，对空间的利用更充分，但是查找效率会降低（链表长度会越来越长）；如果加载因子太小，
     * 那么表中的数据将过于稀疏（很多空间还没用，就开始扩容了），对空间造成严重浪费。如果我们在构造方法中不指
     * 定，则系统默认加载因子为0.75，这是一个比较理想的值，一般情况下是无需修改的。另外，无论指定的容量为多少
     * ，构造方法都会将实际容量设为不小于指定容量的2的次方的一个数，且最大值不能超过2的30次方
     * 4. key为null的键值对永远都放在以table[0]为头结点的链表中，当然不一定是存放在头结点table[0]中。如果
     * key不为null，则先求的key的hash值，根据hash值找到在table中的索引，在该索引对应的单链表中查找是否有键值
     * 对的key与目标key相等，有就返回对应的value，没有则返回null。
     * 5. 扩容是一个相当耗时的操作，因为它需要重新计算这些元素在新的数组中的位置并进行复制处理。因此，我们在用
     * HashMap的时，最好能提前预估下HashMap中元素的个数，这样有助于提高HashMap的性能。
     * 6. containsKey方法和containsValue方法。前者直接可以通过key的哈希值将搜索范围定位到指定索引对应的链，
     * 而后者要对哈希数组的每个链表进行搜索。
     * 7. HashMap中的hash()方法：它只是一个数学公式，IDK这样设计对hash值的计算，自然有它的好处，至于为什么这
     * 样设计，我们这里不去追究，只要明白一点，用的位的操作使hash值的计算效率很高。
     * 8. 我们一般对哈希表的散列很自然地会想到用hash值对length取模（即除法散列法），Hashtable中也是这样实现，
     * 这种方法基本能保证元素在哈希表中散列的比较均匀，但取模会用到除法运算，效率很低，HashMap中则通过
     * h&(length-1)的方法来代替取模，同样实现了均匀的散列，但效率要高很多，这也是HashMap对Hashtable的一个
     * 改进。
     * 9. 为什么哈希表的容量一定要是2的整数次幂。首先，length为2的整数次幂的话，h&(length-1)就相当于length取
     * 模，这样便保证了散列的均匀，同时也提升了效率；其次，length为2的整数次幂的话，为偶数，这样length-1为
     * 奇数，奇数的最后一位是1，这样便保证了h&(length-1)的最后一位可能为0，也可能为1（这取决于h的值），即与
     * 后的结果可能为偶数，也可能为奇数，这样便可以保证散列的均匀性，而如果length为奇数的话，很明显
     * length-1为偶数，它的最后一位是0，这样h&(length-1)的最后一位肯定为0，即只能为偶数，这样任何hash值都
     * 只会被散列到数组的偶数下标位置上，这便浪费了近一半的空间，因此，length取2的整数次幂，是为了使不hash值
     * 发生碰撞的概率较小，这样就能使元素在哈希表中均匀地散列。
     */

    static int hash(int h) {
        h ^= (h >>> 20) ^ (h >>> 12);
        return h ^ (h >>> 7) ^ (h >>> 4);
    }

    //https://blog.csdn.net/u010687392/article/details/47809295
    static int indexFor(int h, int length) {
        return h & (length - 1);
    }


    @Override
    public Set<Entry<K, V>> entrySet() {
        return null;
    }
}
