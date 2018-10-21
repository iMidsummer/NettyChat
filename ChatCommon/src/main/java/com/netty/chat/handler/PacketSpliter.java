package com.netty.chat.handler;

import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

public class PacketSpliter extends LengthFieldBasedFrameDecoder {
    public PacketSpliter() {
        super(2048, 4 + 1 + 1 + 1, 4);
    }
}
