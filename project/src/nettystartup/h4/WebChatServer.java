package nettystartup.h4;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import nettystartup.h2.NettyStartupUtil;
import nettystartup.h3.HttpNotFoundHandler;
import nettystartup.h3.HttpStaticFileHandler;

public class WebChatServer {
    static String index = System.getProperty("user.dir") + "/res/h4/index.html";

    public static void main(String[] args) throws Exception {
        NettyStartupUtil.runServer(8040, new ChannelInitializer<SocketChannel>() {
            @Override
            public void initChannel(SocketChannel ch) {
                ChannelPipeline p = ch.pipeline();
                p.addLast(new HttpServerCodec());
                p.addLast(new HttpObjectAggregator(65536));
                p.addLast(new WebSocketHandshakeHandler("/chat", new WebChatHandler()));
                p.addLast(new HttpStaticFileHandler("/", index));
                p.addLast(new HttpNotFoundHandler());
            }
        });
    }
}
