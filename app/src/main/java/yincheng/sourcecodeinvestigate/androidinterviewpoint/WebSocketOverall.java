package yincheng.sourcecodeinvestigate.androidinterviewpoint;

/**
 * Created by yincheng on 2018/5/15/14:16.
 * github:luoyincheng
 */
public class WebSocketOverall {
    /**
     * WebSocket在建立握手时，数据是通过HTTP传输的，但是建立之后，在真正传输的时候，是不需要HTTP协议的。
     * WebSocket和HTTP一样都是基于TCP的，都是可靠性传输协议，都是应用层协议。
     * WebSocket是全双工通信协议，模拟Socket协议，乐意双向发送或者接收消息，而Http是单向的
     * WebSocket是需要浏览器和服务器握手来建立连接的，而Http是浏览器发起向服务器的连接，服务器预先并不知道这个连接。
     * Socket是传输控制层接口，WebSocket是应用层协议。
     * 当两台主机通信时，必须通过Socket连接，Socket则利用TCP/IP协议建立TCP连接。TCP连接则更依靠于底层的IP协议，
     * IP协议的连接则依赖于链路层等更低层次。
     * WebSocket就像HTTP一样，则是一个典型的应用层协议。
     * “Socket是应用层与TCP/IP协议族通信的中间软件抽象层，它是一组接口，提供一套调用TCP/IP协议的API。在设计模式中，
     * Socket其实就是一个门面模式，它把复杂的TCP/IP协议族隐藏在Socket接口后面，对用户来说，一组简单的接口就是全部，
     * 让Socket去组织数据，以符合指定的协议。”
     * 在WebSocket中，只需要服务器和浏览器通过HTTP协议进行一个握手的动作，然后单独建立一条TCP的通信通道进行数据的传送。
     *   1.首先，客户端发起http请求，经过3次握手后，建立起TCP连接；
     *   http请求里存放WebSocket支持的版本号等信息，如：Upgrade、Connection、WebSocket-Version等；
     *   2.然后，服务器收到客户端的握手请求后，同样采用HTTP协议回馈数据；
     *   3.最后，客户端收到连接成功的消息后，开始借助于TCP传输信道进行全双工通信。
     *
     */
}
