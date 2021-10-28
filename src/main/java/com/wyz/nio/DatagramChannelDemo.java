package com.wyz.nio;

import org.junit.Test;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.charset.Charset;

/**
 * @program: nettyPro
 * @description:
 * @author: wyz
 * @create: 2021-09-09 20:52
 **/
public class DatagramChannelDemo {

    @Test
    public void sendDatagram() throws Exception {
        DatagramChannel sendChannel = DatagramChannel.open();
        InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1",9999);
        //发送
        while (true){
            ByteBuffer buffer = ByteBuffer.wrap("发送wyz".getBytes("UTF-8"));
            sendChannel.send(buffer,inetSocketAddress);
            System.out.println("send finish");
            Thread.sleep(1000);
        }
    }

    @Test
    public void receiveDatagram() throws Exception {
        DatagramChannel receiveChannel = DatagramChannel.open();
        InetSocketAddress receiveAddress = new InetSocketAddress(9999);
        //绑定
        receiveChannel.bind(receiveAddress);
        //buffer
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        //接收
        while (true){
            buffer.clear();
            SocketAddress receive = receiveChannel.receive(buffer);
            buffer.flip();
            System.out.println(receiveAddress.toString());
            System.out.println(Charset.forName("UTF-8").decode(buffer));
        }
    }
}
