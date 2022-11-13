package com.passer.demo.netty.handler;

import com.passer.demo.netty.utils.GsonUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.nio.charset.StandardCharsets;

import static com.passer.demo.netty.constants.Constants.MAGIC_CODE;

/**
 * @author passer
 * @time 2022/11/13 16:00
 */
public class CustomMessage2ByteEncoder extends MessageToByteEncoder {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object o, ByteBuf byteBuf) throws Exception {

        byteBuf.markWriterIndex();

        byte[] bytes = MAGIC_CODE.getBytes(StandardCharsets.UTF_8);
        String data = GsonUtils.toJson(o);
        byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);
        int len = dataBytes.length;
        byteBuf.writeBytes(bytes);
        byteBuf.writeInt(len);
        byteBuf.writeBytes(dataBytes);

        byteBuf.markWriterIndex();
    }
}
