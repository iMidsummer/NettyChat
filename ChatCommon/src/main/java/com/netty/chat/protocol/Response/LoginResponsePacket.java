package com.netty.chat.protocol.Response;

import com.netty.chat.protocol.Packet;
import com.netty.chat.protocol.PacketType;
import lombok.Data;

@Data
public class LoginResponsePacket extends Packet {
    private String userID;
    private String userName;

    @Override
    public byte getPacketType() {
        return PacketType.LOGIN_RESPONSE;
    }
}
