package io.github.alwayszmx;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONTool extends Application {
    private TextArea inputTextArea;
    private TextArea outputTextArea;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("JSON Tool");

        // 创建输入框和输出框
        inputTextArea = new TextArea();
        outputTextArea = new TextArea();
        outputTextArea.setEditable(false);

        // 创建按钮
        Button formatButton = new Button("格式化");
        Button compressButton = new Button("压缩");
        Button compareButton = new Button("对比差异");

        // 设置按钮点击事件处理程序
        formatButton.setOnAction(e -> formatJSON());
        compressButton.setOnAction(e -> compressJSON());
        compareButton.setOnAction(e -> compareJSON());

        // 创建顶部工具栏
        ToolBar toolBar = new ToolBar(formatButton, compressButton, compareButton);

        // 创建主布局
        BorderPane mainLayout = new BorderPane();
        mainLayout.setTop(toolBar);
        mainLayout.setCenter(inputTextArea);
        mainLayout.setBottom(outputTextArea);
        BorderPane.setMargin(toolBar, new Insets(5));
        BorderPane.setMargin(outputTextArea, new Insets(5));

        // 创建场景
        Scene scene = new Scene(mainLayout, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void formatJSON() {
        String input = inputTextArea.getText();
        try {
            JSONObject jsonObject = new JSONObject(input);
            String formattedJSON = jsonObject.toString(4); // 使用四个空格进行缩进
            outputTextArea.setText(formattedJSON);
            outputTextArea.setStyle(null);  // 清除高亮样式
        } catch (JSONException e) {
            outputTextArea.setText("输入的JSON格式不正确！");
            outputTextArea.setStyle("-fx-text-fill: red;"); // 设置红色错误提示
        }
    }

    private void compressJSON() {
        String input = inputTextArea.getText();
        try {
            JSONObject jsonObject = new JSONObject(input);
            String compressedJSON = jsonObject.toString();
            outputTextArea.setText(compressedJSON);
            outputTextArea.setStyle(null);  // 清除高亮样式
        } catch (JSONException e) {
            outputTextArea.setText("输入的JSON格式不正确！");
            outputTextArea.setStyle("-fx-text-fill: red;"); // 设置红色错误提示
        }
    }

    private void compareJSON() {
        String input = inputTextArea.getText();
        try {
            JSONObject json1 = new JSONObject(input);

            String input2 = showInputDialog("请输入第二个JSON字符串：");
            JSONObject json2 = new JSONObject(input2);

            JSONObject diff = getDiff(json1, json2);
            String diffString = diff.toString(4); // 使用四个空格进行缩进

            outputTextArea.setText(diffString);
            outputTextArea.setStyle("-fx-control-inner-background: #FFFF99;"); // 设置黄色背景高亮显示

        } catch (JSONException e) {
            outputTextArea.setText("输入的JSON格式不正确！");
            outputTextArea.setStyle("-fx-text-fill: red;"); // 设置红色错误提示
        }
    }

    private JSONObject getDiff(JSONObject json1, JSONObject json2) throws JSONException {
        JSONObject diff = new JSONObject();

        for (String key : json1.keySet()) {
            if (!json2.has(key)) {
                diff.put(key, json1.get(key));
            } else {
                Object val1 = json1.get(key);
                Object val2 = json2.get(key);

                if (val1 instanceof JSONObject && val2 instanceof JSONObject) {
                    JSONObject nestedDiff = getDiff((JSONObject) val1, (JSONObject) val2);
                    if (nestedDiff.length() > 0) {
                        diff.put(key, nestedDiff);
                    }
                } else if (!val1.equals(val2)) {
                    diff.put(key, val1.toString() + " -> " + val2.toString());
                }
            }
        }

        for (String key : json2.keySet()) {
            if (!json1.has(key)) {
                diff.put(key, "null -> " + json2.get(key).toString());
            }
        }

        return diff;
    }

    private String showInputDialog(String prompt) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setHeaderText(null);
        dialog.setContentText(prompt);

        return dialog.showAndWait().orElse("");
    }
}
