package slime.os.Command;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import slime.os.controller.CreateFileController;
import slime.os.controller.FileManageController;
import slime.os.model.OFTLE;
import java.io.File;
import java.io.IOException;

public class CreateFileCommand implements Command {

    @Override
    public void execute(TreeItem<File> selectedItem) throws IOException {
        // 检查选中的项是否为目录且不为空
        if (selectedItem != null && selectedItem.getValue().isDirectory()) {
            // 创建一个新的对话框
            Dialog<ButtonType> dialog = new Dialog<>();
            // 初始化对话框的所有者，这里设置为null
            dialog.initOwner(null);
            // 设置对话框标题
            dialog.setTitle("创建新文件");

            // 创建FXML加载器并加载create-file.fxml布局文件
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/slime/os/create-file.fxml"));

            // 加载FXML内容并设置为对话框的内容
            dialog.getDialogPane().setContent(fxmlLoader.load());
            // 为对话框添加确认和取消按钮
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

            // 显示对话框并等待用户响应
            dialog.showAndWait().ifPresent(buttonType -> {
                if (buttonType == ButtonType.OK) {
                    // 获取FXML控制器
                    CreateFileController controller = fxmlLoader.getController();
                    // 从控制器获取用户输入的文件名
                    String name = controller.getFileName();

                    // 检查文件名是否有效，以及是否能够创建文件
                    if(!controller.createFile()) {
                        // 如果文件创建失败，直接返回
                        return;
                    }

                    // 创建新文件对象，路径为选中目录下的新文件名
                    File newFile = new File(selectedItem.getValue(), name);
                    try {
                        // 尝试创建新文件
                        if (newFile.createNewFile()) {
                            // 创建成功，将新文件作为TreeItem添加到选中目录的子项列表中
                            TreeItem<File> newFileItem = new TreeItem<>(newFile);
                            selectedItem.getChildren().add(newFileItem);

                            // 更新UI和打开文件表，添加新文件条目
                            OFTLE newFileEntry = new OFTLE(newFile.getName(), 'f', 'w', 0, 1);
                            if (FileManageController.openFileTable.addFile(newFileEntry)) {
                                new Alert(Alert.AlertType.INFORMATION, "文件创建成功", ButtonType.OK).showAndWait();
                            } else {
                                // 如果打开文件表已满，显示错误信息
                                new Alert(Alert.AlertType.ERROR, "无法创建文件：打开文件表已满", ButtonType.OK).showAndWait();
                            }

                            // 对选中目录的子项进行排序
                            selectedItem.getChildren().sort((o1, o2) -> o1.getValue().getName().compareTo(o2.getValue().getName()));

                            // 展开选中目录以显示新创建的文件
                            selectedItem.setExpanded(true);
                        } else {
                            // 如果文件已存在，显示错误信息
                            new Alert(Alert.AlertType.ERROR, "创建文件失败：文件已存在", ButtonType.OK).showAndWait();
                        }
                    } catch (IOException e) {
                        // 如果创建文件时发生异常，显示错误信息
                        new Alert(Alert.AlertType.ERROR, "创建文件失败：" + e.getMessage(), ButtonType.OK).showAndWait();
                    }
                }
            });
        }
    }
}