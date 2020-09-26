package com.drassero.bfinterpreter.controller;

import com.drassero.bfinterpreter.Main;
import com.drassero.bfinterpreter.util.History;
import com.drassero.bfinterpreter.util.Interpreter;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.util.Duration;

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

    private PauseTransition clearLogTransition = new PauseTransition(Duration.seconds(4));

    @FXML
    private void initialize() {
        clearLogTransition.setOnFinished(event -> {
            outputTextArea.clear();
        });
    }

    @FXML
    private void onRun() {
        Interpreter interpreter = new Interpreter(codeTextArea.getText(), inputTextArea.getText(), Main.BYTES_ARRAY_LENGTH);
        Optional<String> validStatement = interpreter.isStatementValid();
        if(validStatement.isPresent()) {
            sendLog(validStatement.get());
            return;
        }
        Optional<String> validInput = interpreter.isInputValid();
        if(validInput.isPresent()) {
            sendLog(validInput.get());
            return;
        }
        String output = interpreter.init().run();
        Optional<String> outputStatement = interpreter.isOutputValid();
        if(outputStatement.isPresent()) {
            sendLog(outputStatement.get());
            return;
        }
        printText(output);
    }

    private void printText(String text) {
        outputTextArea.setText(text);
    }

    private void sendLog(String log) {
        outputTextArea.setText(log);
        clearLogTransition.playFromStart();
    }

    public void setMain(Main main) {
        this.main = main;
    }

}