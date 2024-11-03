package slime.os.Command;


import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import slime.os.controller.CreateDirectoryController;

import java.io.File;
import java.io.IOException;


public class CreateDirectoryCommand implements Command {

    @Override
    public void execute(TreeItem<File> selectedItem) throws IOException {
        if (selectedItem != null && selectedItem.getValue().isDirectory()) {
            // 创建一个新的对话框
            Dialog<ButtonType> dialog = new Dialog<>();
            // 初始化对话框的所有者，这里设置为null
            dialog.initOwner(null);
            // 设置对话框标题
            dialog.setTitle("创建新目录");

            // 创建FXML加载器并加载create-dir.fxml布局文件
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/slime/os/create-dir.fxml"));

            // 加载FXML内容并设置为对话框的内容
            dialog.getDialogPane().setContent(fxmlLoader.load());
            // 为对话框添加确认和取消按钮
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

            // 显示对话框并等待用户响应
            dialog.showAndWait().ifPresent(buttonType -> {
                if (buttonType == ButtonType.OK) {
                    // 获取FXML控制器
                    CreateDirectoryController controller = fxmlLoader.getController();
                    // 从控制器获取用户输入的目录名
                    String directoryName = controller.getDirectoryName(); // 假设这是获取目录名的方法
                    // 创建新目录对象，路径为选中目录下的新建目录名
                    // 检查文件名是否有效，以及是否能够创建文件
                    if(!controller.createDir()) {
                        // 如果文件创建失败，直接返回
                        return;
                    }

                    File newDirectory = new File(selectedItem.getValue(), directoryName);

                    try {
                        // 尝试创建新目录
                        if (newDirectory.mkdir()) {
                            // 创建成功，将新目录作为TreeItem添加到选中目录的子项列表中
                            TreeItem<File> newDirectoryItem = new TreeItem<>(newDirectory);
                            selectedItem.getChildren().add(newDirectoryItem);

                            // 展开父节点以显示新目录
                            selectedItem.setExpanded(true);

                            // 可以在这里添加其他逻辑，例如更新UI或文件管理表
                        } else {
                            // 如果目录已存在，显示错误信息
                            new Alert(Alert.AlertType.ERROR, "创建目录失败：目录已存在", ButtonType.OK).showAndWait();
                        }
                    } catch (SecurityException e) {
                        // 如果创建目录时发生安全异常（如权限问题），显示错误信息
                        new Alert(Alert.AlertType.ERROR, "创建目录失败：" + e.getMessage(), ButtonType.OK).showAndWait();
                    }
                }
            });
        }
    }
}