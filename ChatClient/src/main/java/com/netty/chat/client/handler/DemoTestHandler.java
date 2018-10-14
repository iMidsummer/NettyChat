package com.netty.chat.client.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;

public class DemoTestHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        String message = "hello, this is charles";
        byte [] bytes = message.getBytes("UTF-8");
        ByteBuf byteBuf = ctx.alloc().buffer();
        byteBuf.writeBytes(bytes);

        ctx.channel().writeAndFlush(byteBuf);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        String message = byteBuf.toString(Charset.forName("UTF-8"));
        System.out.println("message from server:\n" + message);
    }
}

