package com.netty.chat.protocol.Request;

import com.netty.chat.protocol.Packet;
import com.netty.chat.protocol.PacketType;
import lombok.Data;

@Data
public class LoginRequestPacket extends Packet {
    private String userName;
    private String password;

    @Override
    public byte getPacketType() {
        return PacketType.LOGIN_REQUEST;
    }
}
