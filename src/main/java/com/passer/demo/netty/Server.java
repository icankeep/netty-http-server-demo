package com.passer.demo.netty;

import com.passer.demo.netty.handler.CustomByte2MessageDecoder;
import com.passer.demo.netty.handler.CustomMessageReceiveHandler;
import com.passer.demo.netty.handler.TestInBoundHandler;
import com.passer.demo.netty.handler.TestOutBoundHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author passer
 * @time 2022/11/13 13:05
 */
public class Server {

    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap server = new ServerBootstrap();
            server.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel channel) throws Exception {
                            ChannelPipeline pipeline = channel.pipeline();
                            pipeline.addLast(new CustomByte2MessageDecoder());
                            pipeline.addLast(new CustomMessageReceiveHandler());
                            pipeline.addLast(new TestInBoundHandler());
                            pipeline.addLast(new TestOutBoundHandler());
                        }
                    });

            ChannelFuture future = server.bind("localhost", 9999).sync();
            // 阻塞，避免直接退出
            future.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
