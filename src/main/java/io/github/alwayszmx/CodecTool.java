package io.github.alwayszmx;

import io.github.alwayszmx.utils.CodecUtils;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class CodecTool extends Application {
    private ComboBox<String> codecComboBox;
    private TextField inputTextField;
    private TextArea outputTextArea;

    @Override
    public void start(Stage primaryStage) {
        // 创建布局
        GridPane root = new GridPane();
        root.setPadding(new Insets(10));
        root.setHgap(10);
        root.setVgap(10);

        // 创建控件
        Label codecLabel = new Label("选择编码:");
        codecComboBox = new ComboBox<>();
        codecComboBox.getItems().addAll(
                "Hex",
                "Base64",
                "Unicode",
                "URL",
                "UTF16",
                "Gzip");
        codecComboBox.setValue("Hex");

        Label inputLabel = new Label("输入文本:");
        inputTextField = new TextField();

        Label outputLabel = new Label("输出结果:");
        outputTextArea = new TextArea();
        outputTextArea.setEditable(false);

        Button encodeButton = new Button("编码");
        encodeButton.setOnAction(e -> encode());

        Button decodeButton = new Button("解码");
        decodeButton.setOnAction(e -> decode());

        HBox buttonBox = new HBox(10);
        buttonBox.getChildren().addAll(encodeButton, decodeButton);

        // 将控件添加到布局
        root.add(codecLabel, 0, 0);
        root.add(codecComboBox, 1, 0);

        root.add(inputLabel, 0, 1);
        root.add(inputTextField, 1, 1);

        root.add(outputLabel, 0, 2);
        root.add(outputTextArea, 1, 2);

        root.add(buttonBox, 1, 3);

        // 创建场景
        Scene scene = new Scene(root, 750, 400);

        // 设置舞台
        primaryStage.setTitle("编解码工具");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void encode() {
        String input = inputTextField.getText();
        String output = "";
        String codec = codecComboBox.getValue();
        switch (codec) {
            case "Unicode":
                output = CodecUtils.encodeUnicode(input);
                break;
            case "URL":
                output = CodecUtils.encodeURL(input);
                break;
            case "UTF16":
                output = CodecUtils.encodeUTF16(input);
                break;
            case "Base64":
                output = CodecUtils.encodeBase64(input);
                break;
            case "Hex":
                output = CodecUtils.encodeHex(input);
                break;
            case "Gzip":
                output = CodecUtils.compressGzip(input);
                break;
        }
        outputTextArea.setText(output);
    }

    private void decode() {
        String input = inputTextField.getText();
        String output = "";
        String codec = codecComboBox.getValue();
        switch (codec) {
            case "Unicode":
                output = CodecUtils.decodeUnicode(input);
                break;
            case "URL":
                output = CodecUtils.decodeURL(input);
                break;
            case "UTF16":
                output = CodecUtils.decodeUTF16(input);
                break;
            case "Base64":
                output = CodecUtils.decodeBase64(input);
                break;
            case "Hex":
                output = CodecUtils.decodeHex(input);
                break;
            case "Gzip":
                output = CodecUtils.decompressGzip(input);
                break;
        }
        outputTextArea.setText(output);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
