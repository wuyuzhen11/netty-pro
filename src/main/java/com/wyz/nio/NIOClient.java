package com.wyz.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @program: nettyPro
 * @description:
 * @author: wyz
 * @create: 2021-09-13 14:59
 **/
public class NIOClient {
    public static void main(String[] args) throws IOException {
        //得到一个网络通道
        SocketChannel socketChannel = SocketChannel.open();
        //设置非阻塞
        socketChannel.configureBlocking(false);
        //提供服务端的ip，端口
        InetSocketAddress address = new InetSocketAddress("127.0.0.1", 6666);
        //连接服务器
        if (!socketChannel.connect(address)){
            while (!socketChannel.finishConnect()){
                System.out.println("客户端连接需要时间，客户端不会阻塞，可以做其他事情");
            }
        }
        //如果连接成功就发送数据
        String str="wyz xixi";
        ByteBuffer buffer = ByteBuffer.wrap(str.getBytes());
        socketChannel.write(buffer);
        System.in.read();
    }
}
