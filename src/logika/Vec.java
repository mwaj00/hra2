package logika;
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
    private String url;
    private Map<String, Vec> veci; //klíč a k němu přiřazená hodnota
    /***************************************************************************
     * Konstuktor třídy
     * @param nazev nazev vec
     * @param prenositelnost jestli muzeme vzit vec
     * @param url string url
     */
    public Vec(String nazev, boolean prenositelnost, String url)
    {
        this.nazev = nazev;
        this.prenositelnost = prenositelnost;
        this.url = url;
        veci = new HashMap<>(); //vytvořená nová mapa, do které se vkládají předměty
    }

    /**
     * Metoda vrací název věci
     * @return název věci
     */ 
    public String getNazev() {
        return nazev;
    }
    
    /**
     * Metoda vloží věc do batohu.
     * @param vec vec
     * @return vec ktery je v batohu
     */
    public Vec vlozVec(Vec vec) {
        veci.put(vec.getNazev(),vec); //vloží klíč a hodnotu do mapy
        if(veci.containsKey(vec.getNazev())) return vec;
        return null;
    }
    
     /**
     * Metoda rozhodne, zda v batohu věc je.
     * @param nazev vec
     * @return jestli vec je v batohu
     */
    public boolean obsahujeVec(String nazev) {
        return veci.containsKey(nazev); //pokud je klíč obsažen v mapě, vrací true
    }
    
    /**
     *
     * @return jestli vec je odebrana
     */
    public String odebranaVec(){
        return null;
    }
    
    /**
     *
     * @return jestli vec je prenositelna
     */
    public boolean jePrenositelna(){
        return prenositelnost;
    }

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }
}
