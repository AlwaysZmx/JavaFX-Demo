package io.github.alwayszmx;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class NumberConverter extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // 创建布局容器
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        // 添加控件
        Label inputLabel = new Label("输入数值:");
        grid.add(inputLabel, 0, 0);

        TextField inputTextField = new TextField();
        grid.add(inputTextField, 1, 0);

        Label fromBaseLabel = new Label("源进制:");
        grid.add(fromBaseLabel, 0, 1);

        ComboBox<Integer> fromBaseComboBox = new ComboBox<>(FXCollections.observableArrayList(
                2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20,
                21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36
        ));
        fromBaseComboBox.setValue(10);
        grid.add(fromBaseComboBox, 1, 1);

        Label toBaseLabel = new Label("目标进制:");
        grid.add(toBaseLabel, 0, 2);

        ComboBox<Integer> toBaseComboBox = new ComboBox<>(FXCollections.observableArrayList(
                2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20,
                21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36
        ));
        toBaseComboBox.setValue(10);
        grid.add(toBaseComboBox, 1, 2);

        Label outputLabel = new Label("结果:");
        grid.add(outputLabel, 0, 3);

        TextField outputTextField = new TextField();
        outputTextField.setEditable(false);
        grid.add(outputTextField, 1, 3);

        Button convertButton = new Button("转换");
        convertButton.setOnAction(event -> {
            try {
                String input = inputTextField.getText();
                int fromBase = fromBaseComboBox.getValue();
                int toBase = toBaseComboBox.getValue();

                String output = convert(input, fromBase, toBase);
                outputTextField.setText(output);
            } catch (IllegalArgumentException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }
        });
        grid.add(convertButton, 1, 4);

        // 创建场景
        Scene scene = new Scene(grid, 400, 250);

        // 设置窗口标题和场景
        primaryStage.setTitle("进制转换器");
        primaryStage.setScene(scene);

        // 显示窗口
        primaryStage.show();
    }

    private String convert(String input, int fromBase, int toBase) {
        if (fromBase < 2 || fromBase > 36 || toBase < 2 || toBase > 36) {
            throw new IllegalArgumentException("进制必须在2到36之间");
        }

        try {
            int decimal = Integer.parseInt(input, fromBase);
            return Integer.toString(decimal, toBase);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("无效的数值或进制");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
