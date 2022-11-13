package com.passer.demo.netty.handler;

import com.passer.demo.netty.domain.Message;
import com.passer.demo.netty.utils.GsonUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static com.passer.demo.netty.constants.Constants.MAGIC_CODE;
import static com.passer.demo.netty.constants.Constants.MAGIC_CODE_LEN;
import static com.passer.demo.netty.constants.Constants.MIN_LEN;

/**
 * @author passer
 * @time 2022/11/13 15:07
 *
 * [MAGIC_CODE 4bits][MESSAGE_LENGTH 32bits][MESSAGE]
 */
public class CustomByte2MessageDecoder extends ByteToMessageDecoder {

    private static final Logger log = LoggerFactory.getLogger(CustomByte2MessageDecoder.class);

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        if (byteBuf == null || byteBuf.readableBytes() <= MIN_LEN) {
            log.info("byteBuf not enough");
            return;
        }

        byteBuf.markReaderIndex();
        byte[] bytes = new byte[MAGIC_CODE_LEN];
        byteBuf.readBytes(bytes);
        String data = new String(bytes, StandardCharsets.UTF_8);
        if (!MAGIC_CODE.equals(data)) {
            log.warn("magic code is invalid, expect: {}, actual: {}", MAGIC_CODE, data);
            throw new IllegalStateException("MAGIC CODE is invalid");
        }

        int msgLen = byteBuf.readInt();

        if (byteBuf.readableBytes() < msgLen) {
            byteBuf.resetReaderIndex();
            log.info("byteBuf not enough");
            return;
        }
        bytes = new byte[msgLen];
        byteBuf.readBytes(bytes);
        String msgJson = new String(bytes, StandardCharsets.UTF_8);
        Message message = GsonUtils.fromJson(msgJson, Message.class);
        list.add(message);

        byteBuf.markReaderIndex();
    }
}
