package com.passer.demo.netty.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author passer
 * @time 2022/11/13 15:16
 */
public class CustomMessageReceiveHandler extends ChannelInboundHandlerAdapter {
    private static final Logger log = LoggerFactory.getLogger(CustomMessageReceiveHandler.class);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 得到在上一个Handler中处理完成的数据，用于实现自己的业务逻辑
        log.info("channelRead, message: {}", msg);
    }
}
