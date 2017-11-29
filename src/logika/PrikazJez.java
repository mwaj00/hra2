package logika;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 *  Třída PrikazSnez implementuje pro hru příkaz jez.
 *  Tato třída je součástí jednoduché textové hry.
 *  
 * @author    Julietta Mwansová
 * @version   pro letní semestr 2015/2016
 */
public class PrikazJez implements IPrikaz
{
    private static final String NAZEV = "jez";
    private HerniPlan plan;   
    private MediaPlayer mediaPlayer;
    
    /**
     *  Konstruktor třídy
     *  
     * @param plan herní plán, ve kterém se bude ve hře "jez" 
     */    
    public PrikazJez(HerniPlan plan) {
        this.plan = plan;
    }

    /**
     * Provádí příkaz jez. Zkouší sníst věci, které jsou už vložené v batohu.
     */
    @Override
    public String proved(String... parametry) {
        if (parametry.length == 0) {
            // pokud chybí druhé slovo (sousední prostor), tak ..
            return "Co mam snist?";
        }

        Media sound = new Media(this.getClass().getResource("/zdroje/jist.mp3").toString());
        
        mediaPlayer = new MediaPlayer(sound);

        if(parametry.length == 1) {
            switch (parametry[0]) {
                //vyhodnocuje se, zda napsaná hodnota (parametr) odpovídá jedné z těchto
                case "jahody":  return jahody();
                case "chleba":  return chleba();
                case "boruvky": return boruvky();
                case "jablko": return jablko(); 
                default: return "Tento predmet jsi jeste nesebrala."; //pokud se nenašla žádná z výše uvedených věcí
            }
        }
        return "Nic neprobehlo v jez.";
    }

    /**
     *  Metoda vrací název příkazu (slovo které používá hráč pro jeho vyvolání)
     *  
     *  @ return nazev prikazu
     */
    public String getNazev() {
        return NAZEV;
    }

    /**
     * Metoda zajišťuje, že věc (jahody) lze sníst pouze pokud je již v batohu; "nastaví", že sestra se už najedla.
     */
    private String jahody(){
        if(plan.getBatoh().obsahujeVec("jahody")){
            plan.getBatoh().odeberVec("jahody");
            plan.setJedla(true); 
            plan.notifyAllObservers();
            mediaPlayer.play();
            return "Posilnila ses jahodama.";
        }
        return "Nemas jahody.";
    }

     /**
     * Metoda zajišťuje, že věc (chleba) lze sníst pouze pokud je již v batohu; "nastaví", že sestra se už najedla..
     */
    private String chleba(){
        if(plan.getBatoh().obsahujeVec("chleba")){
            plan.getBatoh().odeberVec("chleba");
            plan.setJedla(true);
            plan.notifyAllObservers();
            mediaPlayer.play();
            return "Posilnila ses chlebem.";
        }
        return "Nemas chleba.";
    }

     /**
     * Metoda zajišťuje, že věc (jablko) lze sníst pouze pokud je již v batohu; "nastaví", že sestra se už najedla..
     */
    public String jablko(){
        if(plan.getBatoh().obsahujeVec("jablko")){
            plan.setOtraveny(true);
            plan.getBatoh().odeberVec("jablko");
            plan.setJedla(true);
            plan.notifyAllObservers();
            mediaPlayer.play();
            return "Posilnila ses jablkem, ale bylo otravene. Zemres, pokud nesnis boruvky.";
        }
        return "Nemas jablko.";
    }

     /**
     * Metoda zajišťuje, že věc (borůvky) lze sníst pouze pokud je již v batohu.
     * Pokud je snězeneno otrávené jídlo, musí se sníst borůvky. Jde ji však snízt i v případě, že nebylo požito jablko.
     */
    public String boruvky(){
        if(plan.getBatoh().obsahujeVec("boruvky"))
        {
            if(plan.isOtraveny()){
                plan.getBatoh().odeberVec("boruvky");
                plan.setOtraveny(false);
                plan.notifyAllObservers();
                mediaPlayer.play();
                return "Boruvky te zachranila.";
            }
            plan.getBatoh().odeberVec("boruvky");
            plan.notifyAllObservers();
            mediaPlayer.play();
            return "Posilnila ses boruvkama.";
        }
        return "Nemas boruvky.";
    }
}