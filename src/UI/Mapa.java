/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import logika.IHra;
import main.Main;
import utils.Observer;
import utils.ObserverNovaHra;
/**
 *
 * @author Julietta Mwansová
 */
public class Mapa extends AnchorPane implements Observer{

    private IHra hra;
    //private Circle tecka;
    private ImageView tecka;
    
    /**
     *
     * @param hra hleda hru
     */
    public Mapa(IHra hra){
        this.hra = hra;
        hra.getHerniPlan().registerObserver(this);
        init();
    }
    
    private void init(){
        ImageView obrazek = new ImageView(new Image(Main.class.getResourceAsStream("/zdroje/mapa.png"),500,400,false,false));
       //tecka = new Circle(10, Paint.valueOf("red"));
        tecka = new ImageView(new Image(Main.class.getResourceAsStream("/zdroje/postava.png"),50,50,false,false));
        this.getChildren().addAll(obrazek, tecka);
        update();
    }
    
    /**
     * metoda pro aktulizaci panelu.
     */
    
    @Override
    public void update() {
        this.setTopAnchor(tecka, hra.getHerniPlan().getAktualniProstor().getPosY());
        this.setLeftAnchor(tecka, hra.getHerniPlan().getAktualniProstor().getPosX());
    }

    
    
    
}
