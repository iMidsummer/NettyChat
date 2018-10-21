package com.netty.chat.client.main;

import com.netty.chat.client.handler.DemoTestHandler;
import com.netty.chat.handler.PacketSpliter;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;


public class ChatClient {
    public static void main(String[] args) throws Exception {
        NioEventLoopGroup eventLoopGroup = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(eventLoopGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<NioSocketChannel>() {
                        @Override
                        protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                            System.out.println("client connected");
                            nioSocketChannel.pipeline().addLast(new PacketSpliter());
                            nioSocketChannel.pipeline().addLast(new DemoTestHandler());
                        }
                    });
            ChannelFuture future = bootstrap.connect("127.0.0.1", 3600).sync();
            future.channel().closeFuture().sync();
        } finally {
            eventLoopGroup.shutdownGracefully().sync();
        }
    }
}
