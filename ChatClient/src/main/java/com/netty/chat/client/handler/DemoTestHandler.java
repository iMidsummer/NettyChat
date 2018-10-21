package com.netty.chat.client.handler;

import com.netty.chat.codec.PacketCodec;
import com.netty.chat.protocol.Request.LoginRequestPacket;
import com.netty.chat.protocol.Response.LoginResponsePacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class DemoTestHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        LoginRequestPacket requestPacket = new LoginRequestPacket();
        requestPacket.setUserName("Charles");
        requestPacket.setPassword("123456");

        ByteBuf requestBuf = ctx.alloc().buffer();
        PacketCodec.INSTANCE.encode(requestPacket, requestBuf);
        ctx.channel().writeAndFlush(requestBuf);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf responseBuf = (ByteBuf) msg;
        LoginResponsePacket responsePacket = (LoginResponsePacket) PacketCodec.INSTANCE.decode(responseBuf);
        System.out.println("User ID: " + responsePacket.getUserID());
        System.out.println("User Name: " + responsePacket.getUserName());
    }
}

