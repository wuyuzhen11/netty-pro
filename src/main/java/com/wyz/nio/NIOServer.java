package com.wyz.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @program: nettyPro
 * @description:
 * @author: wyz
 * @create: 2021-09-13 13:58
 **/
public class NIOServer {
    public static void main(String[] args) throws IOException {
        //创建ServerSocketChannal
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        //创建selector
        Selector selector = Selector.open();
        //绑定一个端口6666，在服务器端监听
        serverSocketChannel.socket().bind(new InetSocketAddress(6666));
        //设置成非阻塞
        serverSocketChannel.configureBlocking(false);
        //把serverSocketChannal 注册到 selector 中，关心事件为 OP_ACCEPT
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        //循环等待客户端连接
        while (true){
            //等待一秒，如果没有连接，返回
            if (selector.select(1000)==0){
                System.out.println("等待一秒，无连接");
                continue;
            }
            //如果返回是大于0，则获取相关的selectorKey集合
            //1.如果返回是大于0,表示已经获取到关注的事件
            //2.selector.selectKey返回关注事件的集合
            //3.通过selectorKey反向获取通道
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            //使用迭代器遍历
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()){
                //获取serverKey
                SelectionKey selectionKey = iterator.next();
                //根据key，对应的通道发生的事件做相应的处理
                if (selectionKey.isAcceptable()){
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    System.out.println("客户端连接成功，生成一个socketChannel:"+socketChannel.hashCode());
                    //设置为非阻塞
                    socketChannel.configureBlocking(false);
                    //注册socketChannal
                    //并且关联buffer
                    socketChannel.register(selector,SelectionKey.OP_READ, ByteBuffer.allocate(1024));


                }
                if(selectionKey.isReadable()){
                    SocketChannel channel = (SocketChannel)selectionKey.channel();
                    //获取改channal对应的buffer
                    ByteBuffer buffer=(ByteBuffer)selectionKey.attachment();
                    channel.read(buffer);
                    System.out.println("from 客户端："+new String(buffer.array()));
                }
                //手动从集合中移除当前的selectorKey，防止重复执行
                iterator.remove();
            }


        }
    }
}
