package logika;

import java.io.File;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 *  Třída PrikazJdi implementuje pro hru příkaz jdi.
 *  Tato třída je součástí jednoduché textové hry.
 *  
 *@author     Jarmila Pavlickova, Luboš Pavlíček
 *@version    pro školní rok 2015/2016
 */
class PrikazJdi implements IPrikaz {
    private static final String NAZEV = "jdi";
    private HerniPlan plan;
    private Hra hra;

    /**
     *  Konstruktor třídy
     *  
     *  @param plan herní plán, ve kterém se bude ve hře "chodit" 
     */    
    public PrikazJdi(HerniPlan plan) {
        this.plan = plan;
    }

    /**
     *  Provádí příkaz "jdi". Zkouší se vyjít do zadaného prostoru. Pokud prostor
     *  existuje, vstoupí se do nového prostoru. Pokud zadaný sousední prostor
     *  (východ) není, vypíše se chybové hlášení.
     *
     *@param parametry - jako  parametr obsahuje jméno prostoru (východu),
     *                         do kterého se má jít.
     *@return zpráva, kterou vypíše hra hráči
     */ 
    @Override
    public String proved(String... parametry) {
        if (parametry.length == 0) {
            // pokud chybí druhé slovo (sousední prostor), tak ....
            return "Kam mam jit? Musis zadat jmeno vychodu.";
        }

        Media sound = new Media(this.getClass().getResource("/zdroje/prichod.mp3").toString());
        
        MediaPlayer mediaPlayer = new MediaPlayer(sound);

        String smer = parametry[0];

        // zkoušíme přejít do sousedního prostoru
        Prostor sousedniProstor = plan.getAktualniProstor().vratSousedniProstor(smer);
       

        if (sousedniProstor == null) {
            return "Tam se odsud jit neda!";
        }
        else {
            if(sousedniProstor.getNazev().equals("druhyBreh") && !plan.jedlaJidlo()){ // pokud princezna neměla nic k jídlu, tak se nemůže přebrodit
                hra.setKonecHry(true);
                return "Neposilnila ses jidlem, nemela jsi dost sil preplavat jezero.";
            }
            if(sousedniProstor.getNazev().equals("les") && plan.isOtraveny()) {
                hra.setKonecHry(true);
                return "Nemuzes jit k jezeru. Zemrela jsi na otravu jablkem.";
            }
            if(sousedniProstor.getNazev().equals("druhyBreh") && plan.isOtraveny()) { //pokud se snědlo jablko, nesnědla houba - pak konec hry
                hra.setKonecHry(true);
                return "Nemuzes jit k hristi. Zemrela jsi na otravu jablkem.";
            }
            mediaPlayer.play();
            plan.setAktualniProstor(sousedniProstor);
            return sousedniProstor.dlouhyPopis();
        }
    }

    /**
     *  Metoda vrací název příkazu (slovo které používá hráč pro jeho vyvolání)
     *  
     *  @ return nazev prikazu
     */
    @Override
    public String getNazev() {
        return NAZEV;
    }

}
