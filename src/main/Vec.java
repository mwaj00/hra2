import java.util.Map;
import java.util.HashMap;
/**
 * Instance třídy věc představují jednotlivé předměty.
 * 
 * Tato třída je součástí jednoduché textové hry.
 *  
 * 
 * @author    Julietta Mwansová
 * @version   pro letní semestr 2015/2016
 */
public class Vec
{
    private String nazev;
    private boolean prenositelnost;
    private Map<String, Vec> veci; //klíč a k němu přiřazená hodnota
    /***************************************************************************
     * Konstuktor třídy
     */
    public Vec(String nazev, boolean prenositelnost)
    {
        this.nazev = nazev;
        this.prenositelnost = prenositelnost;
        veci = new HashMap<>(); //vytvořená nová mapa, do které se vkládají předměty
    }

    /**
     * Metoda vrací název věci
     */ 
    public String getNazev() {
        return nazev;
    }
    
    /**
     * Metoda vloží věc do batohu.
     */
    public Vec vlozVec(Vec vec) {
        veci.put(vec.getNazev(),vec); //vloží klíč a hodnotu do mapy
        if(veci.containsKey(vec.getNazev())) return vec;
        return null;
    }
    
     /**
     * Metoda rozhodne, zda v batohu věc je.
     */
    public boolean obsahujeVec(String nazev) {
        return veci.containsKey(nazev); //pokud je klíč obsažen v mapě, vrací true
    }
    
    public String odebranaVec(){
        return null;
    }
    
    public boolean jePrenositelna(){
        return prenositelnost;
    }
}
