package com.wyz.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

/**
 * @program: nettyPro
 * @description:
 * @author: wyz
 * @create: 2021-09-12 14:48
 **/
public class ScatteringAndGatheringTest {

    /*
     * @author wuyz
     * @date 2021/9/12 15:47
     * @return void
     * scattering:将数据写入buffer时可以使用buffer数组，一次写入【分散】
     * gathering:从buffer读取数据时可以采用buffer数组，依次读取【聚集】
     *
     */
    public static void main(String[] args) throws IOException {
        //使用ServerSocketChannel和SocketChannel 网络
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        InetSocketAddress inetSocketAddress = new InetSocketAddress(7000);

        serverSocketChannel.socket().bind(inetSocketAddress);

        //创建buffer
        ByteBuffer[] buffers = new ByteBuffer[2];
        buffers[0] = ByteBuffer.allocate(5);
        buffers[1] = ByteBuffer.allocate(3);

        //等待客户端连接
        SocketChannel socketChannel = serverSocketChannel.accept();
        int messageLength = 8;//假设从客户端接收八个字节
        while (true) {
            int byteRead = 0;
            while (byteRead < messageLength) {
                long l = socketChannel.read(buffers);
                byteRead +=l;
                System.out.println("byteRead:"+byteRead);
                Arrays.asList(buffers).stream().map(buffer->
                    "position="+buffer.position()+",limit="+buffer.limit()
                ).forEach(System.out::println);
            }
            //将所有的buffer反转
            Arrays.asList(buffers).forEach(buffer->{
                buffer.flip();
            });
            //将数据读出显示到客户端
            long byteWrite=0;
            while (byteWrite<messageLength){
                long l = socketChannel.write(buffers);
                byteWrite+=l;
            }
            //将所有buffer进行clear
            Arrays.asList(buffers).forEach(buffer->{
                buffer.clear();
            });
            System.out.println("byteRead+"+byteRead+",byteWrite:"+byteWrite+",messageLength:"+messageLength);
        }
    }
}
