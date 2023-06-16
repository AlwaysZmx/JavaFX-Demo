package io.github.alwayszmx;

import com.google.zxing.client.j2se.MatrixToImageWriter;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import javafx.embed.swing.SwingFXUtils;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import javax.imageio.ImageIO;

public class QRCodeGenerator extends Application {

    @Override
    public void start(Stage stage) {
        // 创建UI界面元素
        Label label = new Label("请输入文本:");
        TextArea textArea = new TextArea();
        Slider sizeSlider = new Slider(0, 1000, 250); // 设置初始值和范围
        Label sizeLabel = new Label("二维码尺寸: " + (int) sizeSlider.getValue());
        Button generateButton = new Button("生成");
        ImageView imageView = new ImageView();

        // 将TextArea包装在ScrollPane中
        VBox textBox = new VBox(textArea);
        ScrollPane scrollPane = new ScrollPane(textBox);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        VBox.setVgrow(scrollPane, Priority.ALWAYS);

        // 处理滑块值改变事件
        sizeSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            sizeLabel.setText("二维码尺寸: " + (int) sizeSlider.getValue());
        });

        // 处理生成按钮的点击事件
        generateButton.setOnAction(event -> {
            String text = textArea.getText();
            if (text.isEmpty()) {
                return;
            }
            int width = (int) sizeSlider.getValue(); // 使用滑块的值设置二维码宽度和高度
            int height = (int) sizeSlider.getValue();
            String format = "png"; // 设置二维码格式
            QRCodeWriter writer = new QRCodeWriter();
            BitMatrix bitMatrix;
            try {
                bitMatrix = writer.encode(new String(text.getBytes("UTF-8"),"ISO-8859-1"), BarcodeFormat.QR_CODE, width, height);
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            BufferedImage image = MatrixToImageWriter.toBufferedImage(bitMatrix);
            try {
                ImageIO.write(image, format, baos);
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
            byte[] bytes = baos.toByteArray();
            String encoded = Base64.getEncoder().encodeToString(bytes);
            imageView.setImage(SwingFXUtils.toFXImage(image, null));
        });

        // 创建UI界面布局
        GridPane layout = new GridPane();
        layout.setPadding(new Insets(10));
        layout.setHgap(10);
        layout.setVgap(10);

        layout.add(label, 0, 0);
        layout.add(scrollPane, 0, 1);
        layout.add(sizeSlider, 0, 2);
        layout.add(sizeLabel, 0, 3);
        layout.add(generateButton, 0, 4);
        layout.add(imageView, 0, 5);

        // 显示UI界面
        Scene scene = new Scene(layout, 400, 500);
        stage.setScene(scene);
        stage.setTitle("QR Code Generator");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
