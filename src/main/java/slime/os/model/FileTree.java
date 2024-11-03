package slime.os.model;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import slime.os.Command.*;
import slime.os.controller.FileManageController;

import java.io.File;
import java.io.IOException;


public class FileTree {
    FileManageController fileManageController;

    // 文件树视图
    private TreeView<File> treeView;
    // 文件树的根节点
    private TreeItem<File> root;



    // 创建菜单项


    // 系统的所有根目录
    private final File[] rootFile = new File("src/main/resources/test").listFiles();

    /**
     * 构造函数，初始化FileTree对象
     * @param fileManageController 控制器对象，用于协调文件树视图与应用程序其他部分的交互。
     * @param treeView 当前界面传过来的树视图
     */
    public FileTree(FileManageController fileManageController, TreeView<File> treeView) {
        this.fileManageController = fileManageController;

        // 初始化根节点
        TreeItem<File> root;
        this.treeView = treeView;
//      TODO 根目录，磁盘分配，占用模拟磁盘第 2 块
        root = new TreeItem<>(new File("磁盘文件"));

        root.setExpanded(true); // 展开根节点
        this.root = root;
        treeView.setRoot(root); // 设置文件树的根节点
        treeView.setCellFactory(tv -> new CustomTreeCell());
        treeView.setShowRoot(false); // 不显示根节点
        setTreeMenu();
        buildFileTree(); // 构建文件树
        getSelected();
    }

    /**
     * 构建文件树的方法
      */
    public void buildFileTree() {
        // 遍历所有根目录，并为每个根目录创建一个树节点
        for (File file : rootFile) {
            FileTreeItem item = new FileTreeItem(new File(String.valueOf(file)));
            root.getChildren().add(item);
        }
    }

    /**
     * 获取文件树视图的方法
     * @return 树视图
     */
    public TreeView<File> getTreeView() {
        return treeView;
    }


    /**
     * 获取选中项的方法
      */
    private void getSelected() {
        // 监听选中项的变化
        treeView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {

            File file = treeView.getSelectionModel().getSelectedItem().getValue();

        });
    }

    /**
     * 设置目录树菜单，实现用户接口
     * 采 用“右击快捷菜单”方式提供“创建删除文件 /目录，修改属性”等命令
     */
    private void setTreeMenu(){
//        TODO 等待完善：
//         需要实现的命令包括：
//         创建文件： create 例 如 $ create \aa\bb.e
//         删除文件： delete 例 如 $ delete \aa\yy
//         显示文件： type 例 如 $ type \zz
//         拷贝文件： copy 例 如 $ copy \xx \aa\yy
//         建立目录： mkdir 例 如 $ mkdir \dd
//         删除空目录： rmdir 目录非空时，要报错。
//         可选实现的命令包括：
//         改变目录路径： chdir
//         删除目录： deldir（既可删除空目录又可删除非空目录）
//         移动文件： move
//         改变文件属性： change
//         磁盘格式化： format
//         磁盘分区命令： fdisk

        // 创建上下文菜单
        ContextMenu contextMenu = new ContextMenu();
        MenuItem newDirectoryItem = new MenuItem("新建目录");
        MenuItem newFileItem = new MenuItem("新建文件");
        MenuItem deleteDirItem = new MenuItem("删除目录");

        MenuItem openItem = new MenuItem("打开");
        MenuItem deleteFileItem = new MenuItem("删除");
        MenuItem viewProperties = new MenuItem("属性");

        newFileItem.setOnAction(event -> {
            // 新建文件
            try {
                executeCommand(new CreateFileCommand());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        newDirectoryItem.setOnAction(event -> {
            // 新建目录
            try {
                executeCommand(new CreateDirectoryCommand());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        deleteFileItem.setOnAction(event -> {
            // 删除
            try {
                executeCommand(new DeleteCommand());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        viewProperties.setOnAction(event -> {
            // 属性
            try {
                executeCommand(new ViewPropertiesCommand());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        deleteDirItem.setOnAction(event -> {
            // 删除目录
            try {
                executeCommand(new DeleteCommand());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        openItem.setOnAction(event -> {
            // 打开
            //executeCommand(new OpenCommand());
        });

        // 设置TreeView的上下文菜单
        treeView.setContextMenu(contextMenu);
        // 监听TreeView的右键点击事件
        treeView.setOnContextMenuRequested(event -> {
            TreeItem<File> selectedItem = treeView.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                File value = selectedItem.getValue();
                if (value.isDirectory()) {
                    // 目录项的菜单
                    contextMenu.getItems().clear();
                    contextMenu.getItems().addAll(newDirectoryItem, newFileItem, deleteDirItem,viewProperties);
                } else {
                    // 文件项的菜单
                    contextMenu.getItems().clear();
                    contextMenu.getItems().addAll(openItem,deleteFileItem,viewProperties); // 假设文件只有删除菜单项
                }
            }
            event.consume();
        });

    }

    /**
     *
     * @param command 要执行的命令对象，实现了 {Command} 接口。
     */
    private void executeCommand(Command command) throws IOException {
        // 获取当前在TreeView中选中的项
        TreeItem<File> selectedItem = treeView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            command.execute(selectedItem);
        }
    }
}
