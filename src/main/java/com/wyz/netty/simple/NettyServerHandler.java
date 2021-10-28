package com.wyz.netty.simple;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * @program: nettyPro
 * @description:
 * @author: wyz
 * @create: 2021-09-26 21:42
 **/
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    //读取实际数据
    //1. ChannelHandlerContext ctx:上下文对象，含有管道pipeline , 通道channel，地址
    // 2. object msq:就是客户端发送的数据默认object

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("server ctx"+ ctx);
        //将msg转成buffer
        ByteBuf buf = (ByteBuf) msg;
        System.out.println("客户端发送的消息："+buf.toString(CharsetUtil.UTF_8));
        System.out.println("客户端地址："+ctx.channel().remoteAddress());
    }

    //数据读取完毕

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        //将数据写入缓存并刷新
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello，客户端",CharsetUtil.UTF_8));

    }

    //异常处理

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
