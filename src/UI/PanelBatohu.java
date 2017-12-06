/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import java.util.Map;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import logika.HerniPlan;
import logika.Vec;
import utils.Observer;

/**
 * Panel zobrazí obsah batohu hráče 
 *
 * @author Julietta Mwansová
 */
public class PanelBatohu implements Observer{
    
    private HerniPlan plan;
    ListView<Object> list;
    ObservableList<Object> data;
    
    private TextArea centralText;

    /**
     * konstruktur
     *
     * @param plan hru
     * @param text umisteni textu
     */
    
    public PanelBatohu(HerniPlan plan,TextArea text) {
       this.plan = plan;
       plan.registerObserver(this);
       centralText = text;
        init();
    }

     /**
     * Metoda pro inicializaci komponent kliknutí mýši v panelu.
     */
    
    
    private void init() {
        list = new ListView<>();
        data = FXCollections.observableArrayList();
        list.setItems(data);
        list.setPrefWidth(200);
        
        list.setOnMouseClicked(new EventHandler<MouseEvent>() 
        {
            @Override
            public void handle(MouseEvent click)
            {
                //kolik kr8t kliknout
                if (click.getClickCount() == 2) 
                {
                    // cislo policka
                    int index = list.getSelectionModel().getSelectedIndex();
                    
                    Map<String, Vec> seznam;
                    seznam = plan.getBatoh().getVeci();
                    
                    String nazev = "";
                    int pomocna = 0;
                    for (String x : seznam.keySet()) 
                    {
                       if(pomocna == index)
                       {
                           nazev = x;
                       }
                       pomocna++;
                    }
                    
                    String vstupniPrikaz = "jez "+nazev;
                    String odpovedHry = plan.getHra().zpracujPrikaz("jez "+nazev);

                
                    centralText.appendText("\n" + vstupniPrikaz + "\n");
                    centralText.appendText("\n" + odpovedHry + "\n");
               //nacte se to znovu-odebrani z batohu
                    plan.notifyAllObservers();
                }
            }
        });
        
        
        
        update();
    }
    
    /*
    * Metoda vrací list.
    */

    /**
     *
     * @return vraci list
     */

    public ListView<Object> getList() {
        return list;
    }
 /**
     * metoda pro aktulizaci panelu
     */
    
    @Override 
    public void update() 
    {        
        Map<String, Vec> seznam;
        seznam = plan.getBatoh().getVeci();
        data.clear();
        for (String x : seznam.keySet()) 
        {
        Vec pomocna = seznam.get(x);
        ImageView obrazek = new ImageView(new Image(main.Main.class.getResourceAsStream("/zdroje/"+pomocna.getUrl()), 100, 100, false, false));
        data.add(obrazek);
        }
    }
    
    /**
     * Metoda zaregistruje pozorovatele k hernímu plánu při spuštění nové hry.
     * @param plan zaregistruje pozorovatele k hernímu plánu při spuštění nové hry
     */
    public void nastaveniHernihoPlanu (HerniPlan plan){
        this.plan = plan;
        plan.registerObserver(this);
        this.update();
    }



}
