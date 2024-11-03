package slime.os.model;

import java.util.HashMap;
import java.util.Map;

/**
 * 模拟已打开文件登记表（Open File Table，OFT）的类。
 * 使用哈希表来跟踪当前系统中打开的文件状态。
 */
public class OpenFileTable {
    // 系统允许同时打开的最大文件数量
    private static final int MAX_OPEN_FILES = 5;

    // 文件登记项数组，存储每个打开文件的信息
    private OFTLE[] files = new OFTLE[MAX_OPEN_FILES];

    // 已打开文件的数量
    private int length = 0;

    // 使用哈希表来快速访问文件登记项
    private Map<String, OFTLE> fileMap = new HashMap<>();

    /**
     * 添加新文件登记项到表中。
     * @param file 新建的文件登记项。
     * @return 如果添加成功，返回true；如果表已满或文件已存在，返回false。
     */
    public synchronized boolean addFile(OFTLE file) {
        if (length >= MAX_OPEN_FILES || fileMap.containsKey(file.getName())) {
            return false;
        }
        files[length] = file;
        fileMap.put(file.getName(), file);
        length++;
        return true;
    }

    /**
     * 根据索引获取文件登记项。
     * @param index 要获取的文件登记项的索引。
     * @return 如果索引有效，返回对应的文件登记项；否则返回null。
     */
    public OFTLE getFile(int index) {
        if (index >= 0 && index < length) {
            return files[index];
        }
        return null;
    }

    /**
     * 从表中移除文件登记项。
     * @param fileName 要移除的文件名。
     * @return 如果文件登记项被成功移除，返回true；如果未找到文件，返回false。
     */
    public synchronized boolean removeFile(String fileName) {
        OFTLE file = fileMap.remove(fileName);
        if (file != null) {
            for (int i = 0; i < length; i++) {
                if (files[i].getName().equals(fileName)) {
                    for (int j = i; j < length - 1; j++) {
                        files[j] = files[j + 1];
                    }
                    files[length - 1] = null;
                    length--;
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 根据文件名获取文件登记项。
     * @param fileName 要查找的文件名。
     * @return 如果找到，返回对应的OFTLE对象；否则返回null。
     */
    public OFTLE getFileByName(String fileName) {
        return fileMap.get(fileName);
    }

    // TODO 根据需要添加其他必要的方法
}