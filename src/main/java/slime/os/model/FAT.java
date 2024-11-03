package slime.os.model;
import java.util.Arrays;

/*
模拟磁盘和文件分配表: 使用二维字节数组 diskBuffer 模拟磁盘，文件分配表放在磁盘的前两块。
块分配: 提供了分配和释放磁盘块的方法，包括处理文件的连续块分配。
文件结束和损坏块的处理: 通过特殊值（255 和 254）处理文件结束和损坏块。
空闲块检查: 检查是否有足够的空闲块可用于分配。
*/

public class FAT {

    private byte[][] diskBuffer; // 磁盘缓冲区，用于存储磁盘的内容

    public FAT(Disk disk) throws Exception {
        diskBuffer = disk.getDisk(); // 从磁盘对象获取磁盘缓冲区
    }

     //检测是否有num个空磁盘块。如果有足够的空闲磁盘块则返回true，否则返回false
    protected boolean isEmptyBlockEnough(int num){
        boolean isEnough = false;
        int count = 0; // 计数空闲磁盘块的数量
        for(int i = 0; i < 4; i++){ // 遍历磁盘的每一块
            for(int j = 0; j < 64; j++){
                if(diskBuffer[i][j] == 0){ // 0表示空闲块
                    count++;
                }
            }
        }
        if(count >= num){
            isEnough = true; // 如果找到的空闲块数量大于等于所需数量
        }
        return isEnough;
    }

    /* 返回指定块的下一块盘块号
      blockIndex 当前块的索引
      return int 下一块的索引，如果当前块未分配返回0，如果没有下一块返回1
     */
    protected int getNextBlock(int blockIndex){
        int nextIndex = 0;
        int i = blockIndex / 64; // 计算磁盘中的行索引
        int j = blockIndex % 64; // 计算磁盘中的列索引
        nextIndex = diskBuffer[i][j]; // 获取下一块的索引
        return nextIndex;
    }

    /* 分配指定数量的磁盘块
       headBlock 文件的起始块
       num 需要分配的块数
     */
    public void assignBlocks(int headBlock, int num){
        int index = headBlock;
        for(int i = 0; i < num; i++){
            index = assignNextBlock(index); // 分配每一个块
        }
    }

    /* 查找文件分配表并分配一个空闲盘块
       return int 分配的块的索引，如果磁盘已满返回-1
     */
    public int assignBlock(){
        int blockIndex = -1;
        boolean flag = false;
        for(int i = 0; i < 4 && !flag; i++){
            for(int j = 0; j < 64; j++){
                if(diskBuffer[i][j] == 0){ // 找到空闲块
                    blockIndex = i * 64 + j; // 计算块的索引
                    diskBuffer[i][j] = 1; // 标记块已分配
                    flag = true;
                    break;
                }
            }
        }
        return blockIndex; // 返回分配的块的索引
    }

    /* 给指定块分配下一个空闲盘块，并返回下一个块的索引
       blockIndex 当前块的索引
       return int 分配的下一个块的索引
     */
    public int assignNextBlock(int blockIndex){
        int i = blockIndex / 64; // 计算磁盘中的行索引
        int j = blockIndex % 64; // 计算磁盘中的列索引
        int assignedIndex;
        assignedIndex = assignBlock(); // 分配一个新块
        diskBuffer[i][j] = (byte)assignedIndex; // 更新当前块的分配情况
        return assignedIndex; // 返回分配的新块的索引
    }

    /* 释放指定的磁盘块
       blockIndex 需要释放的块的索引
     */
    public void freeBlock(int blockIndex){
        int i = blockIndex / 64; // 计算磁盘中的行索引
        int j = blockIndex % 64; // 计算磁盘中的列索引
        diskBuffer[i][j] = 0; // 将块标记为未分配
        freeDiskBlock(blockIndex); // 清空块的内容
        return;
    }

    /*
       description: 释放从指定块开始的所有连续磁盘块
       blockIndex 起始块的索引
     */
    public void freeBlocks(int blockIndex){
        int nextBlock = blockIndex;
        int currentBlock = nextBlock;
        while(currentBlock != 1){ // 直到找到结束标志
            nextBlock = getNextBlock(currentBlock); // 获取下一块的索引
            freeBlock(currentBlock); // 释放当前块
            currentBlock = nextBlock; // 继续到下一块
        }
    }

    /* 清空指定的磁盘块
       blockIndex 需要清空的块的索引
     */
    public void freeDiskBlock(int blockIndex){
        byte[] emptyBlock = new byte[64]; // 创建一个空的块
        System.arraycopy(emptyBlock, 0, diskBuffer[blockIndex], 0, 64); // 清空块内容
    }

    /* 更新指定块所在目录盘块的上一块和下一块的连接
       blockIndex 当前块的索引
     */
    public void linkBlock(int blockIndex){
        int nextBlock = getNextBlock(blockIndex); // 获取下一块的索引
        boolean flag = true;
        for(int i = 0; i < 4 && flag; i++){
            for(int j = 0; j < 64; j++){
                if(diskBuffer[i][j] == blockIndex){ // 查找当前块在目录中的位置
                    diskBuffer[i][j] = (byte)nextBlock; // 更新为下一块的索引
                    flag = false;
                    break;
                }
            }
        }
        // 如果只有一块剩余且下一块是结束标志，则不释放空间
        if(!(flag && nextBlock == 1)){
            freeBlock(blockIndex); // 释放当前块
        }
        return;
    }

    public boolean getFAT(){
        return true;
    }
}

