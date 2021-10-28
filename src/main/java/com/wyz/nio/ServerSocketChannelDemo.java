package com.wyz.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * @program: nettyPro
 * @description:
 * @author: wyz
 * @create: 2021-09-09 17:00
 **/
public class ServerSocketChannelDemo {
    public static void main(String[] args) throws IOException {
        //端口号
        int port = 8888;
        //buffer
        ByteBuffer buffer = ByteBuffer.wrap("hello wyz".getBytes());

        ServerSocketChannel socketChannel = ServerSocketChannel.open();
        //绑定
        socketChannel.socket().bind(new InetSocketAddress(port));
        //设置非阻塞
        //socketChannel.configureBlocking(false);
        //监听是否有链接传入
        while (true){
            System.out.println("waiting...");
            SocketChannel accept = socketChannel.accept();
            if (accept==null){
                System.out.println("null");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }else {
                System.out.println("Incoming connection from "+
                        accept.socket().getRemoteSocketAddress());
                buffer.rewind();//指针0
                accept.write(buffer);
                accept.close();
            }
        }
    }
}
