package com.wyz.nio;

import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

/**
 * @program: nettyPro
 * @description:
 * @author: wyz
 * @create: 2021-09-08 17:35
 **/
public class NIOFileChannel03 {
    public static void main(String[] args) throws Exception{
        /*File file = new File("d:\\file01.txt");
        FileInputStream inputStream = new FileInputStream(file);
        FileOutputStream outputStream = new FileOutputStream("d:\\test1.txt");*/
        RandomAccessFile inputStream = new RandomAccessFile("d:\\file01.txt", "rw");
        RandomAccessFile outputStream = new RandomAccessFile("d:\\test1.txt", "rw");
        FileChannel inputStreamChannel = inputStream.getChannel();
        FileChannel outputStreamChannel = outputStream.getChannel();
        //方式一
        /*ByteBuffer allocate = ByteBuffer.allocate(512);
        while (true){
            //清除缓存
            allocate.clear();
            int read = inputStreamChannel.read(allocate);
            if (read==-1){
                break;
            }
            allocate.flip();
            outputStreamChannel.write(allocate);
        }*/
        //方式二
        outputStreamChannel.transferFrom(inputStreamChannel,0,inputStreamChannel.size());
        inputStream.close();
        outputStream.close();

    }
}
