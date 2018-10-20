package com.netty.chat.server.handler;

import com.netty.chat.codec.PacketCodec;
import com.netty.chat.protocol.Request.LoginRequestPacket;
import com.netty.chat.protocol.Response.LoginResponsePacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;
import java.util.UUID;

public class DemoTestHandler extends ChannelInboundHandlerAdapter{
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf requestBuf = (ByteBuf) msg;
        LoginRequestPacket requestPacket = (LoginRequestPacket) PacketCodec.INSTANCE.decode(requestBuf);

        System.out.println("user name: " + requestPacket.getUserName());
        System.out.println("password: " + requestPacket.getPassword());

        LoginResponsePacket responsePacket = new LoginResponsePacket();
        responsePacket.setUserID(UUID.randomUUID().toString());
        responsePacket.setUserName(requestPacket.getUserName());
        ByteBuf responseBuf = ctx.alloc().buffer();
        PacketCodec.INSTANCE.encode(responsePacket, responseBuf);

        ctx.channel().writeAndFlush(responseBuf);
    }
}
