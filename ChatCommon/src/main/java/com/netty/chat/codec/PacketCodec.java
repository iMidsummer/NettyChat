package com.netty.chat.codec;

import com.netty.chat.protocol.Packet;
import com.netty.chat.protocol.PacketType;
import com.netty.chat.protocol.Request.LoginRequestPacket;
import com.netty.chat.protocol.Response.LoginResponsePacket;
import com.netty.chat.serialize.PacketSerializer;
import com.netty.chat.serialize.SerializerType;
import io.netty.buffer.ByteBuf;

import java.util.HashMap;
import java.util.Map;

public class PacketCodec {
    public static final PacketCodec INSTANCE = new PacketCodec();

    public static final int MAGIC_NUMBER = 0x1e34a5b2;

    private final Map<Byte, Class<? extends Packet>> PACKET_CLASS_MAP = new HashMap<Byte, Class<? extends Packet>>();
    private final Map<Byte, PacketSerializer> PACKET_SERIALIZER_MAP = new HashMap<Byte, PacketSerializer>();

    private PacketCodec() {
        PACKET_CLASS_MAP.put(PacketType.LOGIN_REQUEST, LoginRequestPacket.class);
        PACKET_CLASS_MAP.put(PacketType.LOGIN_RESPONSE, LoginResponsePacket.class);

        PACKET_SERIALIZER_MAP.put(SerializerType.JSON, PacketSerializer.DEFAULT);
    }

    public void encode(Packet packet, ByteBuf byteBuf) {
        byte [] objectBytes = PacketSerializer.DEFAULT.encode(packet);

        byteBuf.writeInt(MAGIC_NUMBER);
        byteBuf.writeByte(packet.getVERSION());
        byteBuf.writeByte(packet.getPacketType());
        byteBuf.writeByte(PacketSerializer.DEFAULT.getSerializerType());
        byteBuf.writeInt(objectBytes.length);
        byteBuf.writeBytes(objectBytes);
    }

    public Packet decode(ByteBuf byteBuf) {
        byteBuf.skipBytes(4 + 1);

        Class<? extends Packet> clazz = PACKET_CLASS_MAP.get(byteBuf.readByte());
        if (clazz == null) {
            throw new RuntimeException("Failed to analyze the packet type");
        }

        PacketSerializer serializer = PACKET_SERIALIZER_MAP.get(byteBuf.readByte());
        if (serializer == null) {
            throw new RuntimeException("Failed to analyze the packet serializer type");
        }

        int length = byteBuf.readInt();
        byte [] data = new byte[length];
        byteBuf.readBytes(data);

        return serializer.decode(clazz, data);
    }
}
