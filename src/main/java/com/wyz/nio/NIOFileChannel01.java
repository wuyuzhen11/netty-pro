package com.wyz.nio;

import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @program: nettyPro
 * @description:
 * @author: wyz
 * @create: 2021-09-08 16:43
 **/
public class NIOFileChannel01 {
    public static void main(String[] args) throws Exception{
        String text="wyz好帅";
        FileOutputStream stream = new FileOutputStream("d:\\file01.txt");
        //获取管道，相当于把stream做了封装
        FileChannel channel = stream.getChannel();
        //创建缓冲区
        ByteBuffer allocate = ByteBuffer.allocate(1024);
        //写入数据
        allocate.put(text.getBytes());
        //切换到读操作
        allocate.flip();
        //将缓存开始写入
        channel.write(allocate);
        stream.close();

    }
}
