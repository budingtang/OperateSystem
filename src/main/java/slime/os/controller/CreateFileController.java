package slime.os.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;

public class CreateFileController {
    @FXML
    private TextField nameField;
    @FXML
    private ChoiceBox<String> fileTypeChoiceBox;
    @FXML
    private TextArea textArea;
//    @FXML
//    private Button saveButton;
//    @FXML
//    private Button cancelButton;
//
//    private String directoryPath; // 用于存储选择的目录路径

//    控制器的构造函数应该是无参的，因为JavaFX框架会负责调用initialize()方法来初始化控制器。
    @FXML
    private void initialize() {
        fileTypeChoiceBox.getItems().addAll("txt", "exe");
        fileTypeChoiceBox.getSelectionModel().select("txt");
    }

//    @FXML
//    private void onSaveButtonClicked() {
//        String fileName = nameField.getText().trim();
//        if (fileName.isEmpty()) {
//            new Alert(Alert.AlertType.ERROR, "文件名不能为空", ButtonType.OK).showAndWait();
//            return;
//        }
//
//        // 假设文件扩展名根据ChoiceBox选择动态添加
//        String extension = fileTypeChoiceBox.getValue();
//        String filePath = directoryPath + "/" + fileName + "." + extension;
//
//        // 这里添加创建文件的逻辑
//        try {
//            // 模拟创建文件过程（实际应用中可能涉及文件I/O操作）
//            boolean isCreated = createFile(filePath, textArea.getText());
//            if (isCreated) {
//                new Alert(Alert.AlertType.INFORMATION, "文件创建成功", ButtonType.OK).showAndWait();
//                // 关闭对话框
//                // 这里需要一个方法来通知父窗口关闭对话框
//            } else {
//                new Alert(Alert.AlertType.ERROR, "文件创建失败", ButtonType.OK).showAndWait();
//            }
//        } catch (Exception e) {
//            new Alert(Alert.AlertType.ERROR, "文件创建异常: " + e.getMessage(), ButtonType.OK).showAndWait();
//        }
//    }
//
//    @FXML
//    private void onCancleButtonClicked() {
//        // 关闭对话框
//        // 这里需要一个方法来通知父窗口关闭对话框
//    }

    // 模拟创建文件的方法
    public boolean createFile() {
        String name = this.getFileName();
        String content = this.getFileContent();
        // 检查文件名是否为空
        if (name.trim().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "文件名不能为空", ButtonType.OK).showAndWait();
            return false;
        }


        // TODO 检查磁盘&分配磁盘
        //  这里将涉及文件写入磁盘操作



        return true; // 模拟创建成功
    }

    // 提供方法供外部获取用户输入的文件名和内容
    public String getFileName() {
        return nameField.getText().trim();
    }

    public String getFileContent() {
        return textArea.getText();
    }
}