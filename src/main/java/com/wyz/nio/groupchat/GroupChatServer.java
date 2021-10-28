package com.wyz.nio.groupchat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

/**
 * @program: nettyPro
 * @description: 群聊服务端
 * @author: wyz
 * @create: 2021-09-13 16:38
 **/
public class GroupChatServer {
    private Selector selector;

    private ServerSocketChannel serverSocketChannel;

    private static final int PORT = 6667;

    public GroupChatServer() {
        try {
            selector = Selector.open();

            serverSocketChannel = ServerSocketChannel.open();

            InetSocketAddress address = new InetSocketAddress(PORT);

            serverSocketChannel.socket().bind(address);

            //设置非阻塞
            serverSocketChannel.configureBlocking(false);

            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //监听
    public void listen() {
        try {
            while (true) {
                int count = selector.select();
                if (count > 0) {
                    Iterator<SelectionKey> keyIterator = selector.selectedKeys().iterator();
                    while (keyIterator.hasNext()) {
                        SelectionKey key = keyIterator.next();
                        if (key.isAcceptable()) {
                            SocketChannel accept = serverSocketChannel.accept();
                            accept.configureBlocking(false);
                            //注册
                            accept.register(selector, SelectionKey.OP_READ);
                            //提示
                            System.out.println(accept.getRemoteAddress() + "上线");
                        }
                        if (key.isReadable()) {
                            readData(key);
                        }
                        //手动从集合中移除当前的selectorKey，防止重复执行
                        keyIterator.remove();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void readData(SelectionKey key) {
        SocketChannel socketChannel = null;
        try {
            socketChannel = (SocketChannel) key.channel();
            //创建buffer
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            int read = socketChannel.read(buffer);
            if (read > 0) {
                String string = new String(buffer.array());
                System.out.println("from 客户端：" + string);
                //向其他客户端(排除自己)转发信息
                sendInfoToOtherClient(string,socketChannel);
            }
        } catch (Exception e) {
            try {
                System.out.println(socketChannel.getRemoteAddress()+"离线了");
                //取消注册
                key.cancel();
                //关闭通道
                socketChannel.close();
            }catch (Exception e1){
                e1.printStackTrace();
            }
        }
    }

    //转发
    public void sendInfoToOtherClient(String msg, SocketChannel socketChannel) throws IOException {
        System.out.println("服务器转发消息中");
        //遍历注册到selector的socketChannel,排除自己
        for (SelectionKey key : selector.keys()) {
            Channel channel = key.channel();
            //排除自己
            if (channel instanceof SocketChannel && channel != socketChannel) {
                //转类型
                SocketChannel dest = (SocketChannel)channel;
                ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());
                dest.write(buffer);
            }
        }
    }

    public static void main(String[] args) {
        GroupChatServer chatServer = new GroupChatServer();
        chatServer.listen();
    }
}
