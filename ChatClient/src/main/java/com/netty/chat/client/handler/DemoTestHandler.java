package com.netty.chat.client.handler;

import com.netty.chat.codec.PacketCodec;
import com.netty.chat.protocol.Request.LoginRequestPacket;
import com.netty.chat.protocol.Response.LoginResponsePacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class DemoTestHandler extends ChannelInboundHandlerAdapter {
    private static final ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(2);

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            LoginRequestPacket requestPacket = new LoginRequestPacket();
            requestPacket.setUserName("Charles123456Charles123456Charles123456Charles123456Charles123456Charles123456Charles123456Charles123456Charles123456Charles123456Charles123456");
            requestPacket.setPassword("123456");

            ByteBuf requestBuf = ctx.alloc().buffer();
            PacketCodec.INSTANCE.encode(requestPacket, requestBuf);
            ctx.channel().writeAndFlush(requestBuf);
        }, 10, 10, TimeUnit.MILLISECONDS);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf responseBuf = (ByteBuf) msg;
        LoginResponsePacket responsePacket = (LoginResponsePacket) PacketCodec.INSTANCE.decode(responseBuf);
        System.out.println("User ID: " + responsePacket.getUserID());
        System.out.println("User Name: " + responsePacket.getUserName());
    }
}

