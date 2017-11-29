/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import UI.Mapa;
import UI.MenuPole;
import UI.PanelBatohu;
import UI.PanelVeci;
import UI.Vychody;
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

        Label lBatoh = new Label("Batoh");
        lBatoh.setFont(Font.font("Arial", FontWeight.BOLD, 16));      
        
        Label lVychod = new Label("Východy");
        lVychod.setFont(Font.font("Arial", FontWeight.BOLD, 16));         
        
        Label lVeci = new Label("Věci v místnosti");
        lVeci.setFont(Font.font("Arial", FontWeight.BOLD, 16));         
                
        FlowPane dolniPanel = new FlowPane();
        dolniPanel.setAlignment(Pos.CENTER);
        dolniPanel.getChildren().addAll(zadejPrikazLabel, zadejPrikazTextField);
        
        
        BorderPane levy = new BorderPane();
        
        FlowPane l1 = new FlowPane();
        FlowPane l2 = new FlowPane();
        FlowPane l3 = new FlowPane();
        
        l1.setPrefWidth(100);
        l2.setPrefWidth(100);
        l3.setPrefWidth(100);
        
        PanelBatohu panelBatohu = new PanelBatohu(hra.getHerniPlan(),centerText);
        Vychody vychody = new Vychody(hra.getHerniPlan(),centerText,zadejPrikazTextField);
        PanelVeci panelVeci = new PanelVeci(hra.getHerniPlan(),centerText);

        l1.getChildren().addAll(lBatoh,panelBatohu.getList());
        l2.getChildren().addAll(lVychod,vychody.getList());
        l3.getChildren().addAll(lVeci,panelVeci.getList());
        
        
        levy.setTop(mapa);
        levy.setCenter(l2);
        levy.setLeft(l1);
        levy.setRight(l3);
        
        
        //panel prikaz
        borderPane.setBottom(dolniPanel);
        //obrazek s mapou
        borderPane.setLeft(levy);
        //menu adventury
        borderPane.setTop(menu);
        

        Scene scene = new Scene(borderPane, 1200, 870);

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
        start(primaryStage);
    }
    
    public Stage getPrimaryStage() {
        return primaryStage;
    }
    
}
