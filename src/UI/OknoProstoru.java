/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import javax.swing.JComponent;
import logika.HerniPlan;
import logika.IHra;
import static org.omg.CORBA.ORB.init;
import utils.Observer;

/**
 *
 * @author Julietta
 */
public class OknoProstoru extends JComponent implements Observer{

    
     OknoProstoru(HerniPlan plan) {
        super();
        init();
        
    }
     
    @Override
    public void update() {
       
    }

    @Override
    public void novaHra(IHra hra) {
        
    }
    
    
}
