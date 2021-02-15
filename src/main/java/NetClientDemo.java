import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.net.NetClient;
import io.vertx.core.net.NetClientOptions;
import io.vertx.core.net.NetSocket;


public class NetClientDemo extends AbstractVerticle {
    public static void main(String[] args) {
        Vertx.vertx().deployVerticle(new NetClientDemo());
    }




    @Override
    public void start() throws Exception {


        //创建连接到指定主机和端口的客户端，并绑定创建结果的处理器
        NetClient netClient3 = vertx.createNetClient(new NetClientOptions().setConnectTimeout(10000))
                .connect(9984, "119.3.156.49", res -> {
                    if (res.succeeded()) {
                        System.out.println("连接成功!");
                        NetSocket socket = res.result();
                        //向服务器写入数据
                        socket.write(Buffer.buffer("发送数据......"), ar -> {
                            if (ar.succeeded()) {
                                System.out.println("数据发送成功!");
                            } else {
                                System.err.println("数据发送失败!");
                            }
                        });

                        //读取服务端返回的数据
                        socket.handler(buffer -> {
                            System.out.println("读取到数据:" + buffer.toString() + " 长度为: " + buffer.length());
                        });
                        socket.closeHandler(ar -> {
                            System.out.println("客户端断开连接");
                        });
                    } else {
                        System.out.println("连接失败!: " + res.cause().getMessage());
                    }
                });
    }
}