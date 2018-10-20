package com.netty.chat.serialize;

import com.netty.chat.protocol.Packet;

public interface PacketSerializer {
    byte getSerializerType();
    byte [] encode(Packet packet);
    Packet decode(Class<? extends Packet> clazz, byte [] data);

    PacketSerializer DEFAULT = new JSONPacketSerializer();
}
