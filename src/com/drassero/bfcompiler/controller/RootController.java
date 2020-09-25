package com.drassero.bfcompiler.controller;

import com.drassero.bfcompiler.Main;
import com.drassero.bfcompiler.util.History;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;

public class RootController {

    public static final int COLUMNS = 10;

    private Main main;

    @FXML
    private TextArea codeTextArea;
    @FXML
    private TextArea inputTextArea;
    @FXML
    private TextArea outputTextArea;
    @FXML
    private ListView<History> historyListView;
    @FXML
    private GridPane memoryViewGridPane;

    @FXML
    private void initialize() {
        memoryViewGridPane.addColumn(COLUMNS - 1);
        memoryViewGridPane.addRow((Main.BYTES_ARRAY_LENGTH - COLUMNS) / COLUMNS, new Label("0"));
    }

    public void setMain(Main main) {
        this.main = main;
    }

}
