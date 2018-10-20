package com.netty.chat.serialize;

import com.alibaba.fastjson.JSON;
import com.netty.chat.protocol.Packet;

public class JSONPacketSerializer implements PacketSerializer {
    public byte getSerializerType() {
        return SerializerType.JSON;
    }

    public byte [] encode(Packet packet) {
        return JSON.toJSONBytes(packet);
    }

    public Packet decode(Class<? extends Packet> clazz, byte [] data) {
        return (Packet) JSON.parseObject(data, clazz);
    }
}
