package com.wyz.netty.simple;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @program: nettyPro
 * @description:
 * @author: wyz
 * @create: 2021-09-17 15:30
 **/
public class NettyServer {
    public static void main(String[] args) throws Exception{



        //创建BossGroup和workerGroup
        //1.创建两个线程组
        //2.BossGroup处理连接请求，workerGroup进行业务处理
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        //创建服务端的启动对象，配置 参数
        ServerBootstrap bootStrap = new ServerBootstrap();
        try {
        //使用链式编程进行设置
        bootStrap.group(bossGroup,workerGroup)//设置两个线程组
                .channel(NioServerSocketChannel.class)//使用NioSocketChanne为服务器的通道实现
                .option(ChannelOption.SO_BACKLOG,128)//设置线程队列得到连接个数
                .childOption(ChannelOption.SO_KEEPALIVE,true)//设置保持活动连续状态
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline().addLast(new NettyServerHandler());
                    }
                });//给workerGroup的EventLoop对应的管道设置处理器

        System.out.println("...服务器 is ready");

        //绑定一个端口并且同步，生成一个channelFuture对象
        //启动服务
        ChannelFuture channelFuture = bootStrap.bind(6668).sync();

        //对关闭通道进行监听
        channelFuture.channel().closeFuture().sync();

        }catch (Exception e){

        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
