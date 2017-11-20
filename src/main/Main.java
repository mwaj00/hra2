/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import UI.Mapa;
import UI.MenuPole;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import logika.Hra;
import logika.IHra;
import uiText.TextoveRozhrani;

/**
 *
 * @author mwaj00
 */
import javafx.scene.text.FontWeight;
import uiText.TextoveRozhrani;

public class Main extends Application {

    private MenuPole menu;
    private IHra hra;
    private TextArea centerText;
    private Stage primaryStage;
    private Mapa mapa;
    
    @Override
    public void start(Stage primaryStage) {
        
        
        this.primaryStage = primaryStage;
        hra = new Hra();
        menu = new MenuPole(this);
        mapa = new Mapa(hra);

        BorderPane borderPane = new BorderPane();
        
        centerText = new TextArea();
        centerText.setText(hra.vratUvitani());
        centerText.setEditable(false);
        borderPane.setCenter(centerText);

        

        Label zadejPrikazLabel = new Label("Zadej prikaz");
        zadejPrikazLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));

        TextField zadejPrikazTextField = new TextField("Sem zadej prikaz");

        zadejPrikazTextField.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {

                String zadanyPrikaz = zadejPrikazTextField.getText();
                String odpoved = hra.zpracujPrikaz(zadanyPrikaz);

                centerText.appendText("\n\n" + zadanyPrikaz + "\n\n");
                centerText.appendText("\n\n" + odpoved + "\n\n");

                zadejPrikazTextField.setText("");

                if (hra.konecHry()) {
                    zadejPrikazTextField.setEditable(false);
                }
            }
        });

                 
                
        FlowPane dolniPanel = new FlowPane();
        dolniPanel.setAlignment(Pos.CENTER);
        dolniPanel.getChildren().addAll(zadejPrikazLabel, zadejPrikazTextField);
        
        
      /*  //obrazek s mapou
        FlowPane obrazekPane = new FlowPane();
        ImageView obrazek = new ImageView(new Image(Main.class.getResourceAsStream("/zdroje/prostredy.png"),400,420,false,false));
           
        obrazekPane.getChildren().add(obrazek);*/
        
        //panel prikaz
        borderPane.setBottom(dolniPanel);
        //obrazek s mapou
        borderPane.setLeft(mapa);
        //menu adventury
        borderPane.setTop(menu);
        

                //  TextoveRozhrani textoverozhrani = new TextoveRozhrani(hra);
        // textoveRozhrani.hra();
        // StackPane root = new StackPane();
        // root.getChildren().add(btn);
        Scene scene = new Scene(borderPane, 800, 650);

        primaryStage.setTitle("Moje adventura");
        primaryStage.setScene(scene);

        primaryStage.show();
        
        zadejPrikazTextField.requestFocus();
    }


    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        if (args.length == 0) {
            launch(args);
        } else {
            if (args[0].equals("-text")) {
                IHra hra = new Hra();
                TextoveRozhrani textoveRozhrani = new TextoveRozhrani(hra);
                textoveRozhrani.hraj();
            } else {
                System.out.println("Neplatny parametr");
                System.exit(1);
            }
        }
    }

    public void novaHra() {
        hra = new Hra();
        centerText.setText(hra.vratUvitani());
        //to same pro vsechny observery
        mapa.novaHra(hra);
    }
    
    public Stage getPrimaryStage() {
        return primaryStage;
    }
    
}
