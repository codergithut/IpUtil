import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.net.NetServer;

public class NetServerDemo extends AbstractVerticle {

    public static void main(String[] args) {
        Vertx.vertx().deployVerticle(new NetServerDemo());
    }




    @Override
    public void start() throws Exception {

        //绑定处理器，当有请求进入时触发
        NetServer netServer = vertx.createNetServer().connectHandler(netSocket -> {
            System.out.println(netSocket.remoteAddress().host());
            //得到NetSocket实例
            netSocket.handler(buffer -> {
                //读取数据
                System.out.println("读取到数据:" + buffer.toString() + " 长度为: " + buffer.length());
            });

            netSocket.write(Buffer.buffer("数据已接收......"), ar -> {
                if (ar.succeeded()) {
                    System.out.println("写入数据成功!");
                } else {
                    System.err.println("写入数据失败!");
                }
            });
            netSocket.closeHandler(ar -> {
                System.out.println("客户端退出连接");
            });
        }).listen(9984, "weixin.frontjs.top", res -> {
            if (res.succeeded()) {
                System.out.println("Tcp服务端启动成功");
            } else {
                System.out.println(res.cause().getMessage());
                System.err.println("Tcp服务端启动失败");
            }
        });
    }
}