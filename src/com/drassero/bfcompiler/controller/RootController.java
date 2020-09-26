package com.drassero.bfcompiler.controller;

import com.drassero.bfcompiler.Main;
import com.drassero.bfcompiler.util.History;
import com.drassero.bfcompiler.util.Interpreter;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

import java.util.Optional;

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
    private void onRun() {
        Interpreter interpreter = new Interpreter(codeTextArea.getText(), inputTextArea.getText(), Main.BYTES_ARRAY_LENGTH).init();
        String output = interpreter.run();
        Optional<String> error = interpreter.isValid();
        if(error.isPresent()) {
            outputTextArea.setText(error.get());
        } else {
            outputTextArea.setText(output);
        }
    }

    public void setMain(Main main) {
        this.main = main;
    }

}