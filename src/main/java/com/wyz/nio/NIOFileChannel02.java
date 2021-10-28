package com.wyz.nio;

import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @program: nettyPro
 * @description:
 * @author: wyz
 * @create: 2021-09-08 17:18
 **/
public class NIOFileChannel02 {
    public static void main(String[] args) throws Exception{
        File file = new File("d:\\file01.txt");
        FileInputStream stream = new FileInputStream(file);
        FileChannel channel = stream.getChannel();
        ByteBuffer allocate = ByteBuffer.allocate((int) file.length());
        //将数据缓存到缓冲区
        channel.read(allocate);
        System.out.println(new String(allocate.array()));
        stream.close();
    }
}
