package com.example.baudot;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        /*FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();*/

        stage.setTitle("TextField test");
        Group root = new Group();
        Scene scene = new Scene(root, 650, 450, Color.WHITE);

        TabPane tabPane = new TabPane();
        BorderPane borderPane = new BorderPane();

        Tab tab1 = new Tab();
        Tab tab2 = new Tab();
        tab1.setText("Tab 1");
        tab2.setText("Tab 2");
        tab1.closableProperty().set(false);
        tab2.closableProperty().set(false);
        tab1.setContent(encrypt());
        tab2.setContent(decrypt());
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

    public static VBox encrypt(){
        VBox r = new VBox();
        TextArea ct1 = new TextArea("Cipher Text 1");
        TextArea ct2 = new TextArea("Cipher Text 2");
        Label l1 = new Label("Area 1");
        Label l2 = new Label("Area 2");
        Button button = new Button("Enter");

        EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                ct2.setText(ct1.getText());

            }
        };

        button.setOnAction(event);


        r.getChildren().add(l1);
        r.getChildren().add(ct1);
        r.getChildren().add(l2);
        r.getChildren().add(ct2);
        r.getChildren().add(button);
        r.setAlignment(Pos.CENTER);

        return r;
    }

    public static TilePane decrypt(){
        TilePane r = new TilePane();
        return r;
    }
}