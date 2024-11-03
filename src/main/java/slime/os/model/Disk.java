package slime.os.model;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Arrays;

//磁盘管理模拟器

public class Disk {

    // 二维字节数组，用来模拟磁盘的存储空间
    private byte[][] disk;


     //throws Exception 如果读取磁盘文件失败，将抛出异常

    public Disk() throws Exception {
        // 初始化磁盘数组并写入文件
        disk = new byte[256][64];
        for(int i=0; i<5; i++){
            disk[0][i] = 1;
        }
        writeDisk();
        // 从文件中读取磁盘数据
        readDisk();
    }

    /**
     * 将整数转换为两个字节的数组，低位在前
     * @param number 要转换的整数
     * @return 2字节的数组
     */
    public static byte[] lengthToBytes(int number){
        byte[] bytes = new byte[2];
        // 取整数的低8位
        int temp = number & 0x000000ff;
        bytes[0] = (byte)temp;
        // 右移8位，取高8位
        temp = number >> 8;
        bytes[1] = (byte)temp;
        return bytes;
    }

    /**
     * 将有符号字节转换为无符号整数
     * @param BYTE 要转换的字节
     * @return 无符号整数
     */
    public static int byteToUnsigned(byte BYTE){
        int UNSIGNED_NUM = BYTE;
        // 将字节转换为无符号整数
        UNSIGNED_NUM &= 0XFF;
        return UNSIGNED_NUM;
    }

    /**
     * 将当前的磁盘数据写入文件
     * @throws Exception 如果文件操作失败，将抛出异常
     */
    public void writeDisk() throws Exception{
        // 创建文件输出流
        FileOutputStream disk = new FileOutputStream("disk.sim");
        // 创建对象输出流
        ObjectOutputStream oos = new ObjectOutputStream(disk);
        // 写入磁盘数据
        oos.writeObject(this.disk);
        // 关闭流
        oos.close();
    }

    /**
     * 从文件中读取磁盘数据
     * @throws Exception 如果文件操作失败，将抛出异常
     */
    public void readDisk() throws Exception{
        // 创建文件输入流
        FileInputStream disk = new FileInputStream("disk.sim");
        // 创建对象输入流
        ObjectInputStream ois = new ObjectInputStream(disk);
        // 读取磁盘数据
        this.disk = (byte[][]) ois.readObject();
        // 关闭流
        ois.close();
    }

    /**
     * 获取当前的磁盘数据
     * @return 当前的磁盘数据
     * @throws Exception 如果读取磁盘数据失败，将抛出异常
     */
    public byte[][] getDisk() throws Exception {
        // 返回磁盘数据
        return disk;
    }

    /**
     * 将字符串转换为UTF-8编码的字节数组
     * @param string 要转换的字符串
     * @return UTF-8编码的字节数组
     */
    public byte[] stringToBytes(String string){
        // 使用UTF-8编码将字符串转换为字节数组
        byte[] bytes = string.getBytes(Charset.forName("UTF-8"));
        return bytes;
    }

    /**
     * 将UTF-8编码的字节数组转换为字符串
     * @param bytes 要转换的字节数组
     * @return 字符串
     */
    public String bytesToString(byte[] bytes){
        // 使用UTF-8编码将字节数组转换为字符串
        String string = new String(bytes, Charset.forName("UTF-8"));
        return string;
    }

    /**
     * 将路径字符串格式化为字节型二维数组，每行代表文件或目录的名字数据
     * @param path 路径字符串
     * @return 格式化后的二维字节数组
     */
    public byte[][] formatPath(String path){
        // 将路径中的文件扩展名去掉
        String temp= path.split("\\.")[0];
        // 按目录或文件名分割路径
        String[] directory;
        byte[][] bytePath;
        directory = temp.split("/");
        // 如果路径以根目录开始，去掉第一个空元素
        if(directory[0].equals("")){
            directory = Arrays.copyOfRange(directory,1,directory.length);
        }
        // 创建一个二维字节数组，每行的长度为3
        bytePath = new byte[directory.length][3];
        for(int i=0; i<directory.length; i++){
            // 将目录或文件名转换为字节数组
            byte[] bytes = this.stringToBytes(directory[i]);
            int destPos = 0;
            // 如果字节数组长度不足3，则进行填充
            if(bytes.length!=3){
                destPos=3-bytes.length;
            }
            // 将字节数组拷贝到目标数组
            System.arraycopy(bytes,0, bytePath[i], destPos,bytes.length);
        }
        return bytePath;
    }

    /**
     * 打印磁盘的内容
     */
    public void printDisk(){
        // 遍历磁盘数组并打印内容
        for(int i=0; i<256; i++){
            for(byte b:disk[i]){
                System.out.print(byteToUnsigned(b) + " ");
            }
            System.out.println();
        }
    }
}
