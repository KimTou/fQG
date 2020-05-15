package cjt.controller.socket;

import net.sf.json.JSONObject;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

/**
 * @author cjt
 * @ServerEndpoint 注解是一个类层次的注解，它的功能主要是将目前的类定义成一个websocket服务器端,
 * 注解的值将被用于监听用户连接的终端访问URL地址,客户端可以通过这个URL来连接到WebSocket服务器端
 */

@ServerEndpoint("/websocket/{userId}")
public class WebSocket {

    /**
     * 静态变量，用来记录当前在线连接数。
     */
    private static int onlineCount = 0;

    /**
     * concurrent包的线程安全Map，用来存放每个客户端对应的WebSocket对象。
     */
    private static Map<Integer,WebSocket> clients = new ConcurrentHashMap<Integer,WebSocket>();

    /**
     * 与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    private Session session;
    /**
     * 某个用户（使用者）的id
     */
    private int userId;

    /**
     * 连接建立成功调用的方法
     * @param userId  使用者的id
     * @param session 可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
     * @throws IOException
     */
    @OnOpen
    public void onOpen(@PathParam("userId")int userId, Session session) throws IOException {
        this.session = session;
        this.userId=userId;
        //加入set中
        clients.put(userId,this);
        //在线数加1
        addOnlineCount();
        this.session.getAsyncRemote().sendText("欢迎来到QG闲鱼聊天室，请注意文明用语，共建纯净和谐的聊天室，祝您使用愉快");
        System.out.println("有新连接加入！当前在线人数为" + getOnlineCount());
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(){
        //从map中删除
        clients.remove(userId,this);
        //在线数减1
        subOnlineCount();
        System.out.println("有一连接关闭！当前在线人数为" + getOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     * @param jsonMsg 客户端发送过来的消息
     * @param session 可选的参数
     */
    @OnMessage
    public void onMessage(String jsonMsg, Session session) throws IOException {
        //解析页面传送过来的json字符串
        JSONObject jsonObject = JSONObject.fromObject(jsonMsg);
        //获取消息内容
        String message=jsonObject.getString("message");
        //获取发送者id
        int sender = jsonObject.getInt("from");
        //type为1，代表群发消息
        if(jsonObject.getInt("type")==1) {
            //发给每一个在线用户
            for (WebSocket item : clients.values()) {
                item.sendMessage("你（id："+sender+"）对大家说："+message);
            }
        }
        //type为2，代表私信
        else {
            //用result来标记是否发送成功
            int result = 0;
            //获取私信人id
            int receiver = jsonObject.getInt("to");
            for (int userId : clients.keySet()) {
                if(userId == receiver){
                    WebSocket item = clients.get(userId);
                    item.sendMessage("用户（id："+sender+"）私信对你说："+message);
                    //代表发送成功
                    result = 1;
                    break;
                }
            }
            for (int userId : clients.keySet()) {
                //回应发送者私信的响应结果
                if(userId == sender){
                    WebSocket item = clients.get(userId);
                    if(result==1) {
                        item.sendMessage("你对"+"用户（id："+receiver+"）说："+message+"***私信已发送***");
                    }
                    else{
                        item.sendMessage("你对"+"用户（id："+receiver+"）说："+message+"***私信发送失败，对方不在线***");
                    }
                    break;
                }
            }
        }
        System.out.println("来自客户端的消息:" + message);
    }

    /**
     * 发生错误时调用
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error){
        System.out.println("发生错误");
        error.printStackTrace();
    }

    /**
     * 这个方法与上面几个方法不一样。没有用注解，是根据自己需要添加的方法。
     * @param message
     * @throws IOException
     */
    public void sendMessage(String message) throws IOException{
        //显示当前时间
        String str = new SimpleDateFormat("HH:mm:ss").format(new Date());
        //异步（非阻塞式）发送消息
        this.session.getAsyncRemote().sendText(str+"</br>"+message);
    }



    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSocket.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSocket.onlineCount--;
    }

}