/*
Baudot code encryption, decryption, and cryptanalysis tool
By: Cameron Bradshaw
    Paul Huffman
    Ethan Ison

    This tool will allow the user to encrypt and decrypt messages using the Baudot character encoding.
 */
package com.example.baudot;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class BaudotApplication extends Application{
    @Override
    public void start(Stage stage) {

        stage.setTitle("Baudot App");
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

    public static VBox encrypt(){
        VBox area = new VBox();


        TextArea ct1 = new TextArea();
        ct1.setPromptText("Cipher Text");
        TextArea ct2 = new TextArea();
        ct2.setPromptText("Key");
        TextArea pt = new TextArea();
        pt.setPromptText("Plain Text");

        Label l1 = new Label("Cipher Text");
        Label l2 = new Label("Key");
        Label l3 = new Label("Plain Text");

        Button b1 = new Button("Print");

        EventHandler<ActionEvent> event = actionEvent -> {
            if(HelloApplication.formatString(ct1.getText()).length() == HelloApplication.formatString(ct2.getText()).length()){
                pt.setText(add(ct1.getText(), ct2.getText()));
            }
            else {
                pt.setText("Ciphertext and key need to be the same length");
            }
        };



        b1.setOnAction(event);

        area.getChildren().add(l1);
        area.getChildren().add(ct1);
        area.getChildren().add(l2);
        area.getChildren().add(ct2);
        area.getChildren().add(b1);
        area.getChildren().add(l3);
        area.getChildren().add(pt);
        area.setAlignment(Pos.CENTER);




        return area;
    }

    public static HBox cryptanalysis(){
        HBox area = new HBox();
        VBox vb1 = new VBox();
        VBox vb2 = new VBox();
        VBox vb3 = new VBox();
        VBox vb4 = new VBox();
        HBox row1 = new HBox();
        HBox row2 = new HBox();
        HBox row3 = new HBox();
        HBox row4 = new HBox();
        HBox scroll = new HBox();

        TextArea ct1 = new TextArea();
        ct1.setPromptText("Cipher Text 1");
        TextArea ct2 = new TextArea();
        ct2.setPromptText("Cipher Text 2");
        TextArea pt = new TextArea();
        pt.setPromptText("Crib");
        TextArea out = new TextArea();
        out.setPromptText("Output");

        int lenmess = 100000;
        TextArea[] firstrow = new TextArea[lenmess];
        TextArea[] secrow = new TextArea[lenmess];
        TextArea[] thirow = new TextArea[lenmess];
        for (int i = 0; i < lenmess; i++) {
            TextArea temp = new TextArea();
            temp.setPrefHeight(20);
            temp.setPrefWidth(20);
            //listener will not allow character fields in cryptanalysis to exceed 1 character
            temp.lengthProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observableValue, Number oldvalue, Number newvalue) {
                    if (newvalue.intValue() > oldvalue.intValue()) {
                        if (temp.getText().length() >= 1) {
                            temp.setText(temp.getText().substring(0, 1));
                        }
                    }
                }
            });
            firstrow[i] = temp;
        }
        //TextArea[] secrow = new TextArea[lenmess];
        for (int i = 0; i < lenmess; i++) {
            TextArea temp = new TextArea();
            temp.setPrefHeight(20);
            temp.setPrefWidth(20);
            int finalI = i;
            //this listener will also keep character fields at a single character but also allows
            //user to type in characters for cryptanalysis that will update the second message
            temp.lengthProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observableValue, Number oldvalue, Number newvalue) {
                    if (newvalue.intValue() > oldvalue.intValue()) {
                        if (temp.getText().length() >= 1) {
                            temp.setText(temp.getText().substring(0, 1));
                        }
                    }
                    if(secrow[finalI].getText().equals("")) {
                        secrow[finalI].setText("");
                        thirow[finalI].setText("");
                    }
                    else{
                        thirow[finalI].setText(add(String.valueOf(firstrow[finalI].getText().charAt(0)), String.valueOf(secrow[finalI].getText().charAt(0))));
                    }
                }
            });

            secrow[i] = temp;
        }
        //TextArea[] thirow = new TextArea[lenmess];
        for (int i = 0; i < lenmess; i++) {
            TextArea temp = new TextArea();
            temp.setPrefHeight(20);
            temp.setPrefWidth(20);
            int finalI = i;
            //this listener will also keep character fields at a single character but also allows
            //user to type in characters for cryptanalysis that will update the second message
            temp.lengthProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observableValue, Number oldvalue, Number newvalue) {
                    if (newvalue.intValue() > oldvalue.intValue()) {
                        if (temp.getText().length() >= 1) {
                            temp.setText(temp.getText().substring(0, 1));
                        }
                    }

                    if(thirow[finalI].getText().equals("")) {
                        secrow[finalI].setText("");
                        thirow[finalI].setText("");
                    }
                    else{
                        secrow[finalI].setText(add(String.valueOf(firstrow[finalI].getText().charAt(0)), String.valueOf(thirow[finalI].getText().charAt(0))));
                    }
                }
            });
            thirow[i] = temp;
        }
        TextArea pt2 = new TextArea();
        pt2.setPromptText("Plaintext");

        Label l1 = new Label("Cipher Text 1");
        Label l2 = new Label("Cipher Text 2");
        Label l3 = new Label("Crib");
        Label l4 = new Label("Output");
        Label l5 = new Label("CT1 + CT2");
        Label l6 = new Label("Crib");
        Label l7 = new Label("PT");
        Label l8 = new Label("Plaintext");

        Button b1 = new Button("Setup");
        Button b2 = new Button("Shift Left");
        Button b3 = new Button("Shift Right");
        Button b4 = new Button("Print");

        AtomicInteger position = new AtomicInteger();
        AtomicInteger alert = new AtomicInteger();
        AtomicReference<String> addition = new AtomicReference<String>("");
        AtomicReference<String> result = new AtomicReference<>("");
        AtomicReference<String> crib = new AtomicReference<>("");

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setHmax(30);

        //this is the handler for the Set button during cryptanalysis
        EventHandler<ActionEvent> event = actionEvent -> {
            //this if statement won't allow the ciphertext messages to be different length as they must
            //be the same for the encoding to work
            if(HelloApplication.formatString(ct1.getText()).length() == HelloApplication.formatString(ct2.getText()).length()) {
                crib.set(pt.getText());
                String temp = "";
                out.setText("");
                result.set(add(ct1.getText(), ct2.getText()));
                position.set(0);

                for (int i = 0; i < result.get().length(); i++) {
                    row1.getChildren().add(firstrow[i]);
                }

                for (int i = 0; i < result.get().length(); i++) {
                    row2.getChildren().add(secrow[i]);
                }

                for (int i = 0; i < result.get().length(); i++) {
                    row3.getChildren().add(thirow[i]);
                }


                scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

                vb3.getChildren().add(scrollPane);
                vb3.getChildren().add(scroll);
                vb3.getChildren().add(row4);
                vb3.getChildren().add(l8);
                vb3.getChildren().add(pt2);

                area.getChildren().add(vb3);


                for (int i = 0; i < crib.get().length(); i++) {
                    temp = temp + result.get().charAt(i);
                }
                addition.set(add(temp, crib.get()));
                for (int i = 0; i < result.get().length(); i++) {
                    firstrow[i].setText(String.valueOf(result.get().charAt(i)));
                }
                for (int i = 0; i < crib.get().length(); i++) {
                    secrow[i].setText(String.valueOf(crib.get().charAt(i)));
                }
                for (int i = 0; i < addition.get().length(); i++) {
                    thirow[i].setText(String.valueOf(addition.get().charAt(i)));
                }
            }
            else{
                out.setText("Ciphertext 1 and Ciphertext 2 need to be the same length");
            }
        };

        /*
        EventHandler<ActionEvent> event2 = actionEvent -> {
            if (position.get() > 0) {
                String temp = "";
                for (int i = position.get(); i < pt.getText().length(); i++) {
                    temp = temp + result.get().charAt(i);
                }
                addition.set(add(temp, result.get()));
                position.getAndDecrement();
                for (int i = 0; i < lenmess; i++) {
                    secrow[i].setText("");
                }
                for (int i = 0; i < lenmess; i++) {
                    thirow[i].setText("");
                }
                int j = 0;
                for (int i = position.get(); i < ct1.getText().length(); i++) {
                    if(j < pt.getText().length()) {
                        secrow[i].setText(String.valueOf(pt.getText().charAt(j)));
                        thirow[i].setText(String.valueOf(addition.get().charAt(j)));
                        j++;
                    }
                }
                pt2.setText(addition.get());
            }
        };


        EventHandler<ActionEvent> event3 = actionEvent -> {
            if (position.get() < lenmess - pt.getText().length()) {
                String temp = "";
                for (int i = position.get(); i < pt.getText().length(); i++) {
                    temp = temp + result.get().charAt(i);
                }
                addition.set(add(temp, result.get()));
                position.getAndIncrement();
                for (int i = 0; i < lenmess; i++) {
                    secrow[i].setText("");
                }
                for (int i = 0; i < lenmess; i++) {
                    thirow[i].setText("");
                }
                int j = 0;
                for (int i = position.get(); i  <= ct1.getText().length(); i++) {
                    //if (j < ct1.getText().length() - pt.getText().length()) {
                    if(j < pt.getText().length()){
                        secrow[i].setText(String.valueOf(pt.getText().charAt(j)));
                        thirow[i].setText(String.valueOf(addition.get().charAt(j)));
                        j++;
                    }
                }
            }
        };
        */

        //handler for the shift left button in cryptanalysis
        EventHandler<ActionEvent> event2 = ActionEvent -> {
            if(!(position.get() - 1 < 0)){
                position.getAndDecrement();
                for (int i = 0; i < lenmess; i++) {
                    secrow[i].setText("");
                }
                for (int i = 0; i < lenmess; i++) {
                    thirow[i].setText("");
                }
                //this is what XORs the crib with the cipher message during a shift so the crib will shift
                //along the ciphertext message
                int j = position.get();
                for (int i = 0; i < pt.getText().length(); i++) {
                    secrow[i + j].setText(String.valueOf(crib.get().charAt(i)));
                    thirow[i + j].setText(String.valueOf(add(String.valueOf(crib.get().charAt(i)), String.valueOf(result.get().charAt(i + j)))));
                }
            }
        };

        EventHandler<ActionEvent> event3 = ActionEvent -> {
            if(!(position.get() + crib.get().length() + 1 > result.get().length())){
                position.getAndIncrement();
                for (int i = 0; i < lenmess; i++) {
                    secrow[i].setText("");
                }
                for (int i = 0; i < lenmess; i++) {
                    thirow[i].setText("");
                }
                //this is what XORs the crib with the cipher message during a shift so the crib will shift
                //along the ciphertext message
                int j = position.get();
                for (int i = 0; i < crib.get().length(); i++) {
                    secrow[i+j].setText(String.valueOf(crib.get().charAt(i)));
                    thirow[i+j].setText(String.valueOf(add(String.valueOf(crib.get().charAt(i)), String.valueOf(result.get().charAt(i+j)))));
                }

            }
        };

        //handler for the print button during cryptanalysis
        EventHandler<ActionEvent> event4 = ActionEvent -> {

            String temp = "";
            String temp2 = "";

            for(int i = 0; i < secrow.length; i++){
                temp = temp + secrow[i].getText();
            }

            for (int i = 0; i < thirow.length; i++) {
                temp2 = temp2 + thirow[i].getText();
            }


            pt2.setText(temp + "\n\n" + temp2);
        };

        b1.setOnAction(event);

        b2.setOnAction(event2);
        b3.setOnAction(event3);
        b4.setOnAction(event4);


        vb1.getChildren().add(l1);
        vb1.getChildren().add(ct1);
        vb1.getChildren().add(l2);
        vb1.getChildren().add(ct2);
        vb1.getChildren().add(b1);
        vb1.getChildren().add(l3);
        vb1.getChildren().add(pt);
        vb1.setAlignment(Pos.CENTER);

        vb2.getChildren().add(l1);
        vb2.getChildren().add(ct1);
        vb2.getChildren().add(l2);
        vb2.getChildren().add(ct2);
        vb2.getChildren().add(l3);
        vb2.getChildren().add(pt);
        vb2.getChildren().add(b1);
        vb2.getChildren().add(l4);
        vb2.getChildren().add(out);
        vb2.setAlignment(Pos.CENTER);

        /*for (int i = 0; i < 20; i++) {
            row1.getChildren().add(firstrow[i]);
        }

        for (int i = 0; i < 20; i++) {
            row2.getChildren().add(secrow[i]);
        }

        for (int i = 0; i < 20; i++) {
            row3.getChildren().add(thirow[i]);
        }*/

        row4.getChildren().add(b2);
        row4.getChildren().add(b3);
        row4.getChildren().add(b4);



        vb4.getChildren().add(l5);
        vb4.getChildren().add(row1);
        vb4.getChildren().add(l6);
        vb4.getChildren().add(row2);
        vb4.getChildren().add(l7);
        vb4.getChildren().add(row3);

        scrollPane.setContent(vb4);

        /*vb3.getChildren().add(scroll);
        vb3.getChildren().add(row4);
        vb3.getChildren().add(l8);
        vb3.getChildren().add(pt2);*/

        area.getChildren().add(vb1);
        area.getChildren().add(vb2);
        //area.getChildren().add(vb3);

        return area;
    }

    //this function XORs the characters together to encode and decode the messages
    //using the Baudot addition table.
    public static String add(String string1, String string2) {
        int xValue;
        int yValue;
        String ans = "";

        //formatString eliminates spaces from the string so the ciphertext messages are essentially a
        //constant string
        String ct1 = formatString(string1);
        String ct2 = formatString(string2);

        //Baudot addition table, the slot at M[x][y] = x XOR y
        char[][] additionTable = {
                {'/', 'G', 'F', 'R', '4', 'C', 'B', 'Q', 'S', '3', 'N', 'Z', '8', 'K', '5', 'Y', 'H', 'D', 'I', 'W', '9', 'X', 'T', 'V',
                        'P', 'L', 'M', 'O', 'J', 'E', 'U', 'A'},
                {'G', '/', 'Q', 'T', 'O', 'H', 'A', 'F', '8', 'L', 'P', 'J', 'S', 'Y', 'E', 'K', 'C', 'W', 'M', 'D', 'V', 'U', 'R', '9',
                        'N', '3', 'I', '4', 'Z', '5', 'X', 'B'},
                {'F', 'Q', '/', 'U', 'K', 'A', 'H', 'G', '3', 'S', 'E', 'M', '4', 'L', 'P', 'O', 'B', '9', 'J', 'V', 'D', 'T', 'X', 'W',
                        '5', '8', 'Z', 'Y', 'I', 'N', 'R', 'C'},
                {'R', 'T', 'U', '/', '3', '9', 'W', 'X', 'K', '4', 'I', '5', 'Y', 'S', 'Z', '8', 'V', 'A', 'N', 'B', 'C', 'Q', 'G', 'H',
                        'M', 'O', 'P', 'L', 'E', 'J', 'F', 'D'},
                {'4', 'O', 'K', '3', '/', 'N', '5', 'Y', 'U', 'R', 'C', 'W', 'X', 'F', 'B', 'Q', 'P', 'J', '9', 'Z', 'I', '8', 'L', 'M',
                        'H', 'T', 'V', 'G', 'D', 'A', 'S', 'E'},
                {'C', 'H', 'A', '9', 'N', '/', 'Q', 'B', 'J', 'I', '4', '8', 'Z', 'E', 'Y', '5', 'G', 'U', '3', 'X', 'R', 'W', 'V', 'T',
                        'O', 'M', 'L', 'P', 'S', 'K', 'D', 'F'},
                {'B', 'A', 'H', 'W', '5', 'Q', '/', 'C', 'M', 'Z', 'Y', '3', 'I', 'P', '4', 'N', 'F', 'T', '8', 'R', 'X', '9', 'D', 'U',
                        'K', 'J', 'S', 'E', 'L', 'O', 'V', 'G'},
                {'Q', 'F', 'G', 'X', 'Y', 'B', 'C', '/', 'L', '8', '5', 'I', '3', 'O', 'N', '4', 'A', 'V', 'Z', '9', 'W', 'R', 'U', 'D',
                        'E', 'S', 'J', 'K', 'M', 'P', 'T', 'H'},
                {'S', '8', '3', 'K', 'U', 'J', 'M', 'L', '/', 'F', 'D', 'H', 'G', 'R', 'V', 'T', 'Z', 'N', 'A', 'P', 'E', 'O', 'Y', '5',
                        'W', 'Q', 'B', 'X', 'C', '9', '4', 'I'},
                {'3', 'L', 'S', '4', 'R', 'I', 'Z', '8', 'F', '/', '9', 'B', 'Q', 'U', 'W', 'X', 'M', 'E', 'C', '5', 'N', 'Y', 'O', 'P',
                        'V', 'G', 'H', 'T', 'A', 'D', 'K', 'J'},
                {'N', 'P', 'E', 'I', 'C', '4', 'Y', '5', 'D', '9', '/', 'X', 'W', 'A', 'Q', 'B', 'O', 'S', 'R', '8', '3', 'Z', 'M', 'L',
                        'G', 'V', 'T', 'H', 'U', 'F', 'J', 'K'},
                {'Z', 'J', 'M', '5', 'W', '8', '3', 'I', 'H', 'B', 'X', '/', 'C', 'V', 'R', '9', 'S', 'O', 'Q', '4', 'Y', 'N', 'E', 'K',
                        'U', 'A', 'F', 'D', 'G', 'T', 'P', 'L'},
                {'8', 'S', 'L', 'Y', 'X', 'Z', 'I', '3', 'G', 'Q', 'W', 'C', '/', 'T', '9', 'R', 'J', 'P', 'B', 'N', '5', '4', 'K', 'E',
                        'D', 'F', 'A', 'U', 'H', 'V', 'O', 'M'},
                {'K', 'Y', '4', 'S', 'F', 'E', 'P', 'O', 'R', 'U', 'A', 'V', 'T', '/', 'H', 'G', '5', 'I', 'D', 'M', 'J', 'L', '8', 'Z',
                        'B', 'X', 'W', 'Q', '9', 'C', '3', 'N'},
                {'5', 'E', 'P', 'Z', 'B', 'Y', '4', 'N', 'V', 'W', 'Q', 'R', '9', 'H', '/', 'C', 'K', 'L', 'X', '3', '8', 'I', 'J', 'S',
                        'F', 'D', 'U', 'A', 'T', 'G', 'M', 'O'},
                {'Y', 'K', 'O', '8', 'Q', '5', 'N', '4', 'T', 'X', 'B', '9', 'R', 'G', 'C', '/', 'E', 'M', 'W', 'I', 'Z', '3', 'S', 'J',
                        'A', 'U', 'D', 'F', 'V', 'H', 'L', 'P'},
                {'H', 'C', 'B', 'V', 'P', 'G', 'F', 'A', 'Z', 'M', 'O', 'S', 'J', '5', 'K', 'E', '/', 'X', 'L', 'U', 'T', 'D', '9', 'R',
                        '4', 'I', '3', 'N', 'B', 'Y', 'W', 'Q'},
                {'D', 'W', '9', 'A', 'J', 'U', 'T', 'V', 'N', 'E', 'S', 'O', 'P', 'I', 'L', 'M', 'X', '/', 'K', 'G', 'F', 'H', 'B', 'Q',
                        '8', '5', 'Y', 'Z', '4', '3', 'C', 'R'},
                {'I', 'M', 'J', 'N', '9', '3', '8', 'Z', 'A', 'C', 'R', 'Q', 'B', 'D', 'X', 'W', 'L', 'K', '/', 'Y', '4', '5', 'P', 'O',
                        'T', 'H', 'G', 'V', 'F', 'U', 'E', 'S'},
                {'W', 'D', 'V', 'B', 'Z', 'X', 'R', '9', 'P', '5', '8', '4', 'N', 'M', '3', 'I', 'U', 'G', 'Y', '/', 'Q', 'C', 'A', 'F',
                        'S', 'E', 'K', 'J', 'O', 'L', 'H', 'T'},
                {'9', 'V', 'D', 'C', 'I', 'R', 'X', 'W', 'E', 'N', '3', 'Y', '5', 'J', '8', 'Z', 'T', 'F', '4', 'Q', '/', 'B', 'H', 'G',
                        'L', 'P', 'O', 'M', 'K', 'S', 'A', 'U'},
                {'X', 'U', 'T', 'Q', '8', 'W', '9', 'R', 'O', 'Y', 'Z', 'N', '4', 'L', 'I', '3', 'D', 'H', '5', 'C', 'B', '/', 'F', 'A',
                        'J', 'K', 'E', 'S', 'P', 'M', 'G', 'V'},
                {'T', 'R', 'X', 'G', 'L', 'V', 'D', 'U', 'Y', 'O', 'M', 'E', 'K', '8', 'J', 'S', '9', 'B', 'P', 'A', 'H', 'F', '/', 'C',
                        'I', '4', 'N', '3', '5', 'Z', 'Q', 'W'},
                {'V', '9', 'W', 'H', 'M', 'T', 'U', 'D', '5', 'P', 'L', 'K', 'E', 'Z', 'S', 'J', 'R', 'Q', 'O', 'F', 'G', 'A', 'C', '/',
                        '3', 'N', '4', 'I', 'Y', '8', 'B', 'X'},
                {'P', 'N', '5', 'M', 'H', 'O', 'K', 'E', 'W', 'V', 'G', 'U', 'D', 'B', 'F', 'A', '4', '8', 'T', 'S', 'L', 'J', 'I', '3',
                        '/', '9', 'R', 'C', 'X', 'Q', 'Z', 'Y'},
                {'L', '3', '8', 'O', 'T', 'M', 'J', 'S', 'Q', 'G', 'V', 'A', 'F', 'X', 'D', 'U', 'I', '5', 'H', 'E', 'P', 'K', '4', 'N',
                        '9', '/', 'C', 'R', 'B', 'W', 'Y', 'Z'},
                {'M', 'I', 'Z', 'P', 'V', 'L', 'S', 'J', 'B', 'H', 'T', 'F', 'A', 'W', 'U', 'D', '3', 'Y', 'G', 'K', 'O', 'E', 'N', '4',
                        'R', 'C', '/', '9', 'Q', 'X', '5', '8'},
                {'O', '4', 'Y', 'L', 'G', 'P', 'E', 'K', 'X', 'T', 'H', 'D', 'U', 'Q', 'A', 'F', 'N', 'Z', 'V', 'J', 'M', 'S', '3', 'I',
                        'C', 'R', '9', '/', 'W', 'B', '8', '5'},
                {'J', 'Z', 'I', 'E', 'D', 'S', 'L', 'M', 'C', 'A', 'U', 'G', 'H', '9', 'T', 'V', '8', '4', 'F', 'O', 'K', 'P', '5', 'Y',
                        'X', 'B', 'Q', 'W', '/', 'R', 'N', '3'},
                {'E', '5', 'N', 'J', 'A', 'K', 'O', 'P', '9', 'D', 'F', 'T', 'V', 'C', 'G', 'H', 'Y', '3', 'U', 'L', 'S', 'M', 'Z', '8',
                        'Q', 'W', 'X', 'B', 'R', '/', 'I', '4'},
                {'U', 'X', 'R', 'F', 'S', 'D', 'V', 'T', '4', 'K', 'J', 'P', 'O', '3', 'M', 'L', 'W', 'C', 'E', 'H', 'A', 'G', 'Q', 'B',
                        'Z', 'Y', '5', '8', 'N', 'I', '/', '9'},
                {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X',
                        'Y', 'Z', '8', '5', '3', '4', '9', '/'},
        };


        /*
        for(int i = 0; i < ct1.length(); i++){
            if(ct1.charAt(i) != ' ' && ct2.charAt(i) != ' ') {
                //System.out.print(add(ct1.charAt(i), ct2.charAt(i)));
                combinedCT += add(ct1.charAt(i), ct2.charAt(i));
            }
        }
        */


        for(int i = 0; i < ct1.length(); i++) {
            /*if (Character.isUpperCase(ct1.charAt(i))) {
                xValue = ct1.charAt(i) - 65;
            } else if (Character.isLowerCase(ct1.charAt(i))) {
                xValue = ct1.charAt(i) - 97;
            } else {
                if (ct1.charAt(i) == '8') {
                    xValue = 26;
                }
                if (ct1.charAt(i) == '5') {
                    xValue = 27;
                }
                if (ct1.charAt(i) == '3') {
                    xValue = 28;
                }
                if (ct1.charAt(i) == '4') {
                    xValue = 29;
                }
                if (ct1.charAt(i) == '9') {
                    xValue = 30;
                }
                if (ct1.charAt(i) == '/') {
                    xValue = 31;
                }
            }

            if (Character.isUpperCase(ct2.charAt(i))) {
                yValue = ct2.charAt(i) - 65;
            } else if (Character.isLowerCase(ct2.charAt(i))) {
                yValue = ct2.charAt(i) - 97;
            } else {
                if (ct2.charAt(i) == '8') {
                    yValue = 26;
                }
                if (ct2.charAt(i) == '5') {
                    yValue = 27;
                }
                if (ct2.charAt(i) == '3') {
                    yValue = 28;
                }
                if (ct2.charAt(i) == '4') {
                    yValue = 29;
                }
                if (ct2.charAt(i) == '9') {
                    yValue = 30;
                }
                if (ct2.charAt(i) == '/') {
                    yValue = 31;
                }
            }*/

            xValue = checkString(ct1, i);
            yValue = checkString(ct2, i);

            ans = ans + additionTable[xValue][yValue];
        }
        return ans;

    }

    //takes in a string and goes through it character by character eliminating spaces and tabs
    //because there are no provisions for spaces in the table for Baudot code
    public static String formatString(String string){
        String tmp = new String();
        for(int i = 0; i < string.length(); i++){
            if(string.charAt(i) != ' ' && string.charAt(i) != '\t') {
                tmp = tmp + string.charAt(i);
            }
        }
        return tmp;
    }

    //This converts the character to ASCII and then subtracts either 65 if it is Upper Case or 97 if it isn't
    //this makes A = 0 B = 1 etc and a = 0 b = 1 etc. This way the characters can be used to for indices in
    //Baudot table. Then because it is Baudot code we also need to check for the 6 unprintable characters
    //that were rendered as 8, 5, 3, 4, 9, and / as table rows 26 - 31
    public static int checkString(String ct, int i){
        int Value = 0;
        if (Character.isUpperCase(ct.charAt(i))) {
            Value = ct.charAt(i) - 65;
        } else if (Character.isLowerCase(ct.charAt(i))) {
            Value = ct.charAt(i) - 97;
        } else {
            if (ct.charAt(i) == '8') {
                Value = 26;
            }
            if (ct.charAt(i) == '5') {
                Value = 27;
            }
            if (ct.charAt(i) == '3') {
                Value = 28;
            }
            if (ct.charAt(i) == '4') {
                Value = 29;
            }
            if (ct.charAt(i) == '9') {
                Value = 30;
            }
            if (ct.charAt(i) == '/') {
                Value = 31;
            }
        }
        return Value;
    }

}