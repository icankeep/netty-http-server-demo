package com.passer.demo.netty.handler;

import com.passer.demo.netty.domain.Message;
import io.netty.channel.Channel;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

import java.util.UUID;

/**
 * @author passer
 * @time 2022/11/13 16:09
 */
public class CustomMessageSendHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();

        Message message = new Message();
        message.setUuid(UUID.randomUUID().toString());
        message.setSender("passer1");
        message.setReceiver("passer2");
        message.setContent(System.currentTimeMillis() + " hello, hello");

        if (channel != null && channel.isWritable()) {
            channel.writeAndFlush(message);
            channel.close();
        }

        super.channelActive(ctx);
    }
}
