package slime.os.Command;

import javafx.scene.control.TreeItem;

import java.io.File;
import java.io.IOException;

/**
 * 定义一个命令接口，所有的操作都将实现这个接口。
 */
public interface Command {
    void execute(TreeItem<File> selectedItem) throws IOException;
}
