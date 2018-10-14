package com.netty.chat.protocol;

import lombok.Data;

@Data
public abstract class Packet {
    private byte VERSION = 1;

    public abstract byte getPacketType();
}
