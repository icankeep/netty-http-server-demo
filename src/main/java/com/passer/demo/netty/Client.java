package com.passer.demo.netty;

import com.passer.demo.netty.handler.CustomMessage2ByteEncoder;
import com.passer.demo.netty.handler.CustomMessageSendHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author passer
 * @time 2022/11/13 16:05
 */
public class Client {

    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();
        new Bootstrap()
                .group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        ChannelPipeline pipeline = socketChannel.pipeline();
                        pipeline.addLast(new CustomMessage2ByteEncoder());
                        pipeline.addLast(new CustomMessageSendHandler());
                    }
                })
                .connect("localhost", 9999)
                .sync()
                .channel()
                .closeFuture()
                .sync();
    }
}
