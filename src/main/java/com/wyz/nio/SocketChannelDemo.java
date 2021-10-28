package com.wyz.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @program: nettyPro
 * @description:
 * @author: wyz
 * @create: 2021-09-09 18:00
 **/
public class SocketChannelDemo {
    public static void main(String[] args) throws IOException {
        SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("www.baidu.com", 80));
        //设置非阻塞
        socketChannel.configureBlocking(false);
        //读操作
        ByteBuffer buffer = ByteBuffer.allocate(16);
        socketChannel.read(buffer);
        socketChannel.close();
        System.out.println("read over");

    }
}
