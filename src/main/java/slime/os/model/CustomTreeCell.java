package slime.os.model;

import javafx.scene.control.TreeCell;


import java.io.File;
/**
 * 自定义的树形单元格类，用于显示文件系统中的文件和目录。
 * <p>
 * 继承自 JavaFX 的 TreeCell 类，重写了 updateItem 方法，以便自定义如何显示文件和目录。
 * </p>
 */
class CustomTreeCell extends TreeCell<File> {
    /**
     * 更新树形单元格显示的文件项。
     * @param item 当前的文件项，可能为 null。
     * @param empty 标志指示当前单元格是否为空。
     */
    @Override
    protected void updateItem(File item, boolean empty) {
        // 调用超类的 updateItem 方法，这是 JavaFX 树形单元格的标准做法
        super.updateItem(item, empty);
        if (empty||item == null) {
            setText(null);
            setGraphic(null);
        } else {
            // 显示内容的逻辑
            setText(item.getName()); // 如果item为null，显示"Root"

//            TODO
            // 如果有图形组件，可以在这里设置
            // setGraphic(...);
        }
    }
}