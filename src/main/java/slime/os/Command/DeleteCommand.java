package slime.os.Command;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TreeItem;
import slime.os.controller.FileManageController;

import java.io.File;
import java.util.Optional;

public class DeleteCommand implements Command {
    @Override
    public void execute(TreeItem<File> selectedItem) {
        if (selectedItem != null) {
            File file = selectedItem.getValue();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "确定要删除吗？", ButtonType.YES, ButtonType.NO);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.YES) {
                try {
                    boolean deleted = file.delete();
                    // 从已打开文件表中移除文件
                    boolean isRemoved = FileManageController.openFileTable.removeFile(file.getName());
                    if (deleted&&isRemoved) {
                            // 更新UI，刷新文件列表或目录视图
                        selectedItem.getParent().getChildren().remove(selectedItem);
                        //TODO 删除磁盘中的文件或目录
                    } else {
                        Alert alert2 = new Alert(Alert.AlertType.ERROR, "删除失败：文件可能被使用或没有权限", ButtonType.OK);
                        alert2.showAndWait();
                    }
                } catch (SecurityException e) {
                    Alert alert2 = new Alert(Alert.AlertType.ERROR, "删除失败：没有权限", ButtonType.OK);
                    alert2.showAndWait();
                }
            }
        }
    }
}

