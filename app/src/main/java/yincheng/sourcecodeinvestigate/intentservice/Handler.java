package yincheng.sourcecodeinvestigate.intentservice;

/**
 * Created by yincheng on 2018/5/9/15:16.
 * github:luoyincheng
 */

//
///**
// * A Handler allows you to send and process {@link Message} and Runnable
// * objects associated with a thread's {@link MessageQueue}.  Each Handler
// * instance is associated with a single thread and that thread's message
// * queue.  When you create a new Handler, it is bound to the thread /
// * message queue of the thread that is creating it -- from that point on,
// * it will deliver messages and runnables to that message queue and execute
// * them as they come out of the message queue.
// * <p>
// * <p>There are two main uses for a Handler: (1) to schedule messages and
// * runnables to be executed as some point in the future; and (2) to enqueue
// * an action to be performed on a different thread than your own.
// * <p>
// * <p>Scheduling messages is accomplished with the
// * {@link #post}, {@link #postAtTime(Runnable, long)},
// * {@link #postDelayed}, {@link #sendEmptyMessage},
// * {@link #sendMessage}, {@link #sendMessageAtTime}, and
// * {@link #sendMessageDelayed} methods.  The <em>post</em> versions allow
// * you to enqueue Runnable objects to be called by the message queue when
// * they are received; the <em>sendMessage</em> versions allow you to enqueue
// * a {@link Message} object containing a bundle of data that will be
// * processed by the Handler's {@link #handleMessage} method (requiring that
// * you implement a subclass of Handler).
// * <p>
// * <p>When posting or sending to a Handler, you can either
// * allow the item to be processed as soon as the message queue is ready
// * to do so, or specify a delay before it gets processed or absolute time for
// * it to be processed.  The latter two allow you to implement timeouts,
// * ticks, and other timing-based behavior.
// * <p>
// * <p>When a
// * process is created for your application, its main thread is dedicated to
// * running a message queue that takes care of managing the top-level
// * application objects (activities, broadcast receivers, etc) and any windows
// * they create.  You can create your own threads, and communicate back with
// * the main application thread through a Handler.  This is done by calling
// * the same <em>post</em> or <em>sendMessage</em> methods as before, but from
// * your new thread.  The given Runnable or Message will then be scheduled
// * in the Handler's message queue and processed when appropriate.
// */
public class Handler {
    /**
     ******************************************************************************************
     * 1. Handler：处理与发送消息（Message）
     *    Message：消息的包装类
     *    Looper：整个 Handler 通信的核心，接受 Handler 发送的 Message，循环遍历 MessageQueue 中的 Message，并发送至 Handler 处理
     *    MessageQueue：保存 Message
     *    LocalThread：存储 Looper 与 Looper所在线程的信息
     ******************************************************************************************
     * 2. 每个Thread对应一个Looper，Looper是属于某一个Thread的
     *    每个Looper对应一个MessageQueue
     *    每个MessageQueue对应多个Message
     *    每个Message最多只能指定一个Handler来处理
     *
     *    一个Thread可以有多个Handler，但是一个Handler只能绑定一个线程
     ******************************************************************************************
     ******************************************************************************************
     ******************************************************************************************
     ******************************************************************************************
     */
    /*
     * Set this flag to true to detect anonymous, local or member classes
     * that extend this Handler class and that are not static. These kind
     * of classes can potentially create leaks.
     */
}
