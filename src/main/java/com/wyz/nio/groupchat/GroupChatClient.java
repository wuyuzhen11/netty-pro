package com.wyz.nio.groupchat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;

/**
 * @program: nettyPro
 * @description: 客户端
 * @author: wyz
 * @create: 2021-09-13 19:49
 **/
public class GroupChatClient {
    private final String HOST = "127.0.0.1";
    private final int PORT = 6667;
    private Selector selector;
    private SocketChannel socketChannel;
    private String username;

    public GroupChatClient() throws IOException {
        selector = Selector.open();
        socketChannel = SocketChannel.open(new InetSocketAddress(HOST, PORT));
        //设置非阻塞
        socketChannel.configureBlocking(false);
        socketChannel.register(selector, SelectionKey.OP_READ);
        //得到username
        username = socketChannel.getLocalAddress().toString().substring(1);
        System.out.println(username+"is ok...");
    }

    //向服务器发送消息
    public void sendInfo(String info){
        info=username+"说："+info;
        try {
            socketChannel.write(ByteBuffer.wrap(info.getBytes()));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //读取服务端返回的消息
    public void readInfo(){
        try {
            int select = selector.select();//阻塞
            if (select>0){//有可用的通道
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()){
                    SelectionKey key = iterator.next();
                    if (key.isReadable()){
                        SocketChannel socketChannel=(SocketChannel)key.channel();
                        socketChannel.configureBlocking(false);
                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        socketChannel.read(buffer);
                        //把读取到缓冲区的数据转成字符串
                        String msg=new String(buffer.array());
                        System.out.println(msg.trim());
                    }
                    iterator.remove();
                }
            }else {
                System.out.println("没有可用通道");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        //启动客户端
        GroupChatClient groupChatClient = new GroupChatClient();
        //启动一个线程，每三秒，读取从服务端发送的数据
        new Thread(){
            @Override
            public void run(){
                while (true){
                    groupChatClient.readInfo();
                    try {
                        Thread.currentThread().sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();

        //发送数据给服务端
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()){
            String s = scanner.nextLine();
            groupChatClient.sendInfo(s);
        }

    }
}
