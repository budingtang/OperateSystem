package slime.os.model;
/**
 * 模拟文件表条目（Open File Table Entry，OFTLE）的类。
 * 此类用于表示文件系统中一个打开文件的所有相关信息。
 */
public class OFTLE {
    // 文件的名称，即文件的绝对路径
    private String name;

    // 文件的属性，目录‘d’，文件‘f’
    private char attribute;

    // 文件在存储设备上的起始块号，用于定位文件数据的起始位置
    private int startBlockNumber;

    // 文件的总长度，即文件所占字节数
    private int fileLength;

    // 操作类型标志，'r'表示以读模式打开，'w'表示以写模式打开
    private char flag;

    // 读指针，用于记录当前读取文件的位置
    private Pointer readPointer;

    // 写指针，用于记录当前写入文件的位置
    private Pointer writePointer;

    /**
     * OFTLE类的构造函数，用于初始化文件表条目。
     * @param name 文件的名称或路径。
     * @param attribute 文件的属性。
     * @param flag 操作类型，0代表读模式，1代表写模式。
     * @param startBlockNumber 文件数据在存储设备上的起始块号。
     * @param fileLength 文件的总长度。
     */
    public OFTLE(String name, char attribute, char flag, int startBlockNumber, int fileLength) {
        this.name = name;
        this.attribute = attribute;
        this.flag = flag;
        this.startBlockNumber = startBlockNumber;
        this.fileLength = fileLength;
        // 初始化读指针和写指针，这里假设初始位置为文件开始处
        this.readPointer = new Pointer(startBlockNumber, 0);
        this.writePointer = new Pointer(startBlockNumber, 0);
    }

    public String getName() {
        return name;
    }

    public char getAttribute() {
        return attribute;
    }

    public int getStartBlockNumber() {
        return startBlockNumber;
    }

    public int getFileLength() {
        return fileLength;
    }

    public int getFlag() {
        return flag;
    }

    public Pointer getReadPointer() {
        return readPointer;
    }

    public Pointer getWritePointer() {
        return writePointer;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAttribute(char attribute) {
        this.attribute = attribute;
    }

    public void setStartBlockNumber(int startBlockNumber) {
        this.startBlockNumber = startBlockNumber;
    }

    public void setFileLength(int fileLength) {
        this.fileLength = fileLength;
    }

    public void setFlag(char flag) {
        this.flag = flag;
    }

    public void setReadPointer(Pointer readPointer) {
        this.readPointer = readPointer;
    }

    public void setWritePointer(Pointer writePointer) {
        this.writePointer = writePointer;
    }


}