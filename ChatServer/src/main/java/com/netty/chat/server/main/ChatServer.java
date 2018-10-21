package com.netty.chat.server.main;

import com.netty.chat.handler.PacketSpliter;
import com.netty.chat.server.handler.DemoTestHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class ChatServer {
    public static void main(String[] args) throws Exception {
        EventLoopGroup bossLoopGroup = new NioEventLoopGroup();
        EventLoopGroup workerLoopGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossLoopGroup, workerLoopGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new ChannelInitializer<NioServerSocketChannel>() {
                        @Override
                        protected void initChannel(NioServerSocketChannel nioServerSocketChannel) throws Exception {
                            System.out.println("server starts to listen");
                        }
                    })
                    .childHandler(new ChannelInitializer<NioSocketChannel>() {
                        @Override
                        protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                            System.out.println("init child handler");
                            nioSocketChannel.pipeline().addLast(new PacketSpliter());
                            nioSocketChannel.pipeline().addLast(new DemoTestHandler());
                        }
                    });

            ChannelFuture future = serverBootstrap.bind(3600).sync();
            future.channel().closeFuture().sync();
        } finally {
            bossLoopGroup.shutdownGracefully().sync();
            workerLoopGroup.shutdownGracefully().sync();
        }
    }
}
