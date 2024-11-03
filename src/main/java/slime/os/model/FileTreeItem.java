package slime.os.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;

import java.io.File;


public class FileTreeItem extends TreeItem<File> {

    private boolean isLeaf;
    // 标记是否是第一次获取子节点
    private boolean isFirstChildren = true;
    // 标记是否是第一次判断叶子节点
    private boolean isFirstLeaf = true;

    // 构造函数，传入一个File对象作为节点的值
    public FileTreeItem(File file) {
        super(file);
    }
    @Override
    public String toString() {
        // 返回节点值的名称，即文件或目录的名字
        System.out.println(getValue());
        System.out.println(getValue().getName());
        return getValue().getName();
    }

    @Override
    public ObservableList<TreeItem<File>> getChildren() {
        // 如果是第一次获取子节点，则构建子节点
        if (isFirstChildren) {
            isFirstChildren = false;
            // 调用buildChildren方法构建子节点，并设置给super.getChildren()
            super.getChildren().setAll(buildChildren(this));
        }
        // 返回子节点列表
        return super.getChildren();
    }

    // 重写isLeaf方法，用于判断节点是否为叶子节点
    @Override
    public boolean isLeaf() {
        // 如果是第一次判断叶子节点，则进行判断
        if (isFirstLeaf) {
            isFirstLeaf = false;
            File file = getValue(); // 获取节点的值
            // 获取节点值的子文件
            File[] files = file.listFiles();
            // 如果没有子文件或者子文件长度为0，则为叶子节点
            if (files == null || files.length == 0) {
                isLeaf = true;
            } else {
                // 初始设置为叶子节点
                isLeaf = true;
                // 遍历子文件，如果存在目录，则不是叶子节点
                for (File file2 : files) {
                    if (file2.isDirectory()) {
                        isLeaf = false;
                    }
                }
            }
        }
        // 返回是否为叶子节点
        return isLeaf;
    }
    
    // 构建子节点的方法
    private ObservableList<TreeItem<File>> buildChildren(TreeItem<File> TreeItem) {
        // 获取传入节点的值
        File file = TreeItem.getValue();
        // 如果节点的值是一个目录
        if (file != null && file.isDirectory()) {
            // 获取目录下的文件
            File[] files = file.listFiles();
            // 如果存在文件
            if (files != null && files.length != 0) {
                // 创建一个可观察的列表用于存放子节点
                ObservableList<TreeItem<File>> children = FXCollections.observableArrayList();
                for (File childFile : files) {
                    // 忽略隐藏文件和文件，只添加目录
                    if (childFile.isHidden()) {
                        continue;
                    }
                    children.add(new FileTreeItem(childFile)); // 添加子节点
                }
                // 返回子节点列表
                return children;
            }
        }
        // 如果没有子节点，返回空的可观察列表
        return FXCollections.emptyObservableList();
    }
}
