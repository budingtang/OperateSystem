package slime.os.controller;

import javafx.fxml.Initializable;

import javafx.scene.control.TreeView;

import slime.os.model.FileTree;
import slime.os.model.OpenFileTable;

import java.net.URL;
import java.util.ResourceBundle;

public class FileManageController implements Initializable {


    public FileManageController fileManageController;
    public TreeView treeView;
    public static OpenFileTable openFileTable;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        init();
    }

    /**
     * 初始化
     */
    public void init(){
        treeView = new FileTree(fileManageController, treeView).getTreeView();
        openFileTable = new OpenFileTable();


    }
}
