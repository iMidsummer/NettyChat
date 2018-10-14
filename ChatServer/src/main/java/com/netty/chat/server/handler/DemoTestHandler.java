package com.netty.chat.server.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;

public class DemoTestHandler extends ChannelInboundHandlerAdapter{
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        String message = byteBuf.toString(Charset.forName("UTF-8"));
        System.out.println("message from client:\n" + message);

        ByteBuf responseBuf = ctx.alloc().buffer();
        responseBuf.writeBytes("hello, this is netty server".getBytes("UTF-8"));

        ctx.channel().writeAndFlush(responseBuf);
    }
}
