package slime.os.Command;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TreeItem;

import java.io.File;

public class ViewPropertiesCommand implements Command {
    @Override
    public void execute(TreeItem<File> selectedItem) {
//        TODO 更改属性显示的内容
        if (selectedItem != null) {
            File file = selectedItem.getValue();
            String properties = "文件路径: " + file.getAbsolutePath() + "\n" +
                    "文件大小: " + file.length() + " 字节\n" +
                    "是否目录: " + file.isDirectory() + "\n" +
                    "是否文件: " + file.isFile() + "\n" +
                    "是否隐藏: " + file.isHidden();
            Alert alert = new Alert(Alert.AlertType.INFORMATION, properties, ButtonType.OK);
            alert.showAndWait();
        }
    }
}