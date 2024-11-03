package slime.os.Command;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TreeItem;
import slime.os.model.OpenFileTable;
import slime.os.model.OFTLE;


import java.io.File;

public class OpenCommand implements Command {
    private OpenFileTable openFileTable; // 已打开文件表
    private String fileName; // 文件名
    private char operationType; // 操作类型，r 代表读，w 代表写

    // 构造函数，注入必要的依赖项
    public OpenCommand(OpenFileTable openFileTable, String fileName, char operationType) {
        this.openFileTable = openFileTable;
        this.fileName = fileName;
        this.operationType = operationType;
    }

    @Override
    public void execute(TreeItem<File> selectedItem) {
        File file = selectedItem.getValue();
        if (!file.exists()) {
            // 文件不存在
            new Alert(Alert.AlertType.ERROR, "打开文件失败：文件不存在", ButtonType.OK).showAndWait();
            return;
        }

        // 检查文件是否只读且尝试以写模式打开
        if (!file.canWrite() && operationType == 1) {
            new Alert(Alert.AlertType.ERROR, "打开文件失败：文件是只读的", ButtonType.OK).showAndWait();
            return;
        }

        // 检查文件是否已经打开
        OFTLE existingFile = openFileTable.getFileByName(fileName);
        if (existingFile != null) {
            // 文件已打开，更新操作类型和指针
            existingFile.setFlag(operationType);
            // 更新读/写指针逻辑（根据需要实现）
        } else {
            // 文件未打开，添加到已打开文件表
            OFTLE newFile = new OFTLE(fileName, file.canWrite() ? 'w' : 'r', operationType, 0, (int) file.length());
            if (openFileTable.addFile(newFile)) {
                new Alert(Alert.AlertType.INFORMATION, "文件已成功打开", ButtonType.OK).showAndWait();
            } else {
                // 打开文件表已满
                new Alert(Alert.AlertType.ERROR, "打开文件失败：打开文件表已满", ButtonType.OK).showAndWait();
            }
        }
    }
}