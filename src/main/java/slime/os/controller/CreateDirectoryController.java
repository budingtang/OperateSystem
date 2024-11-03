package slime.os.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;

public class CreateDirectoryController {
    @FXML
    private TextField directoryNameField;

    public String getDirectoryName() {
        return directoryNameField.getText().trim();
    }

    public boolean createDir() {
        String name = directoryNameField.getText().trim();
        // 检查文件名是否为空
        if (name.trim().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "目录名不能为空", ButtonType.OK).showAndWait();
            return false;
        }
        // TODO 检查磁盘&分配磁盘
        //  这里将涉及文件写入磁盘操作
        return true;
    }

    // 此方法由创建按钮的onAction触发
//    @FXML
//    private void onCreateDirectory() {
//        String directoryName = directoryNameField.getText().trim();
//        if (directoryName.isEmpty()) {
//            new Alert(Alert.AlertType.ERROR, "目录名不能为空", ButtonType.OK).showAndWait();
//            return;
//        }
//        // 这里添加创建目录的逻辑
//    }


}
