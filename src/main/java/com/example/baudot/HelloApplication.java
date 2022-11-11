package com.example.baudot;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) {
        /*FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();*/

        stage.setTitle("Baudot");
        Group root = new Group();
        Scene scene = new Scene(root, 650, 700, Color.WHITE);

        TabPane tabPane = new TabPane();
        BorderPane borderPane = new BorderPane();

        Tab tab1 = new Tab();
        Tab tab2 = new Tab();
        tab1.setText("Encode / Decode");
        tab2.setText("Cryptanalysis");
        tab1.closableProperty().set(false);
        tab2.closableProperty().set(false);
        tab1.setContent(encrypt());
        tab2.setContent(cryptanalysis());
        tabPane.getTabs().add(tab1);
        tabPane.getTabs().add(tab2);

        borderPane.prefHeightProperty().bind(scene.heightProperty());
        borderPane.prefWidthProperty().bind(scene.widthProperty());

        borderPane.setCenter(tabPane);
        root.getChildren().add(borderPane);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    public static HBox encrypt(){
        HBox area = new HBox();
        VBox vb1 = new VBox();
        VBox vb2 = new VBox();

        TextArea ct1 = new TextArea();
        ct1.setPromptText("Cipher Text");
        TextArea ct2 = new TextArea();
        ct2.setPromptText("Key");
        TextArea pt = new TextArea();
        pt.setPromptText("Plain Text");

        Label l1 = new Label("Cipher Text");
        Label l2 = new Label("Key");
        Label l3 = new Label("Plain Text");

        Button b1 = new Button("Decrypt");
        Button b2 = new Button("Encrypt");
        Button b3 = new Button("Decrypt");

        EventHandler<ActionEvent> event = actionEvent -> pt.setText(ct1.getText()+ct2.getText());

        EventHandler<ActionEvent> encrypt = actionEvent -> {

            b1.setText("Encrypt");
            l1.setText("Plain Text");
            l3.setText("Cipher Text");
            ct1.setPromptText("Plain Text");
            pt.setPromptText("Cipher Text");

        };

        EventHandler<ActionEvent> decrypt = actionEvent -> {

            b1.setText("Decrypt");
            l3.setText("Plain Text");
            l1.setText("Cipher Text");
            pt.setPromptText("Plain Text");
            ct1.setPromptText("Cipher Text");

        };

        b1.setOnAction(event);
        b2.setOnAction(encrypt);
        b3.setOnAction(decrypt);

        vb1.getChildren().add(l1);
        vb1.getChildren().add(ct1);
        vb1.getChildren().add(l2);
        vb1.getChildren().add(ct2);
        vb1.getChildren().add(b1);
        vb1.getChildren().add(l3);
        vb1.getChildren().add(pt);
        vb1.setAlignment(Pos.CENTER);

        vb2.getChildren().add(b2);
        vb2.getChildren().add(b3);
        vb2.setAlignment(Pos.CENTER);

        area.getChildren().add(vb1);
        area.getChildren().add(vb2);


        return area;
    }

    public static TilePane cryptanalysis(){
        return new TilePane();
    }
}