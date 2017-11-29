package logika;

import java.util.ArrayList;
import java.util.List;
import utils.Observer;
import utils.Subject;

/**
 *  Class HerniPlan - třída představující mapu a stav adventury.
 * 
 *  Tato třída inicializuje prvky ze kterých se hra skládá:
 *  vytváří všechny prostory,
 *  propojuje je vzájemně pomocí východů 
 *  a pamatuje si aktuální prostor, ve kterém se hráč právě nachází.
 *
 *@author     Michael Kolling, Lubos Pavlicek, Jarmila Pavlickova
 *@version    pro školní rok 2015/2016
 */
public class HerniPlan implements Subject {
    private Prostor aktualniProstor;
    private Prostor viteznyProstor;
    private Prostor prohravaciProstor;
    private Batoh batoh;
    private int pokus; //číslo pokusu
    private boolean isOtraveny; // zda je jídlo otrávené
    private boolean jedla; // zda princezna něco jedla
    private Hra hra;
    
    private List<Observer> listObserveru = new ArrayList<>();
    
    /**
     *  Konstruktor, který vytváří jednotlivé prostory a propojuje je pomocí východů.
     *  Jako výchozí aktuální prostor nastaví okraj lesa.
     */
    public HerniPlan(Hra hra) {
        zalozProstoryHry();
        batoh = new Batoh();
        pokus = 0;
        isOtraveny = false;
        this.hra = hra;
        listObserveru = new ArrayList<>();
    }
    
    /**
     *  Vytváří jednotlivé prostory a propojuje je pomocí východů.
     *  Jako výchozí aktuální prostor nastaví okraj lesa.
     */
    private void zalozProstoryHry() {
        // vytvářejí se jednotlivé prostory
     
        Prostor okrajLesa = new Prostor("okraj_lesa","Tady jsi mela piknik a je to pro tebe zacatecni bod. Mas tri moznosti, kam se vydat",230,350 );
        Prostor kukuricnePole = new Prostor("kukuricne_pole", "V dali vidis strasidelny dum. Muzes jit pres\n"
            + "hustym porostem kukuricce a jit k ni, anebo se vratit zpet na okraj lesa", 430,280);
        Prostor les = new Prostor("les","Cesta te vede tim dal tim vic do hlubin lesa a zacinas mit strach.\n"
            + "Dal muzes jit po jejich hrbetu do udoli, nebo se vratit na okraj lesa",30,280);
        Prostor taboriste = new Prostor("taboriste","Vsimnes si stare pani, ktera povida: \"Pres taboriste cesta neni,\n"
            + "vrat se na okraj lesa.\" Muze si vsak lhat, proto se musis rozhodnout,\n"
            + "zda ji poslechnes, nebo ne",230,280);
        Prostor udoli = new Prostor("udoli","Nasla jsi ker plny boruvky. Muzes je sebrat a vlozit do batohu",230,210);
        Prostor strasidelnyDum = new Prostor ("strasidelny_dum", "Jsi vycerpana a mas hlad, posilni se!\n"
            +  "Na stole vidis jidlo: jablko, jahody a chleba. Nejdriv jidlo musis vlozit\n"
            +  " do batohu, pak ho muzes snist",230,140);
        Prostor jezero= new Prostor ("jezero", "Vysla jsi z strasidelneho domu a pred tebou je jezero. Musis ji preplavat.\n"
            + "Hrozi, ze te silny proud vody strhne a utopis se. Risknes to?",230,80); 
        Prostor hriste = new Prostor("hriste", "Preplaval jsi jezero. Jsi u detskeho hristi.\n"
            + "Nikdo tam neni krome jednoho bezdomovce. Usmeje se na tebe a rekne ti 'Asi hledas sveho brachu, reknu ti kde, jestli vyresis moji hadanku'.\n"
            + "Polozi ti otazku, na niz je jen jedna spravna odpoved. Odpovis-li spravne,\n"
            + " rekne ti kde je tvuj bratr. Pokud ne, hra skonci. Mas tri pokusy. Otazka zni: 'Patří ti to, ale tvoji přátelé to používají víc než ty. Co je to'",230,10);
             
        prohravaciProstor = strasidelnyDum;
        prohravaciProstor = hriste;
        viteznyProstor = hriste;
        
        // přiřazují se průchody mezi prostory (sousedící prostory)
        okrajLesa.setVychod(kukuricnePole);
        okrajLesa.setVychod(les);
        okrajLesa.setVychod(taboriste);
        les.setVychod(udoli);
        les.setVychod(okrajLesa);
        kukuricnePole.setVychod(okrajLesa);
        kukuricnePole.setVychod(strasidelnyDum);
        taboriste.setVychod(okrajLesa);
        taboriste.setVychod(udoli);
        udoli.setVychod(strasidelnyDum);
        strasidelnyDum.setVychod(jezero);
        jezero.setVychod(hriste);
        
        //věci, které lze vložit do batohu        
        udoli.vlozVec(new Vec("boruvky", true, "boruvky.png"));
        strasidelnyDum.vlozVec(new Vec("jablko", true, "apple.png"));
        strasidelnyDum.vlozVec(new Vec("jahody", true, "jahody.png"));
        strasidelnyDum.vlozVec(new Vec("chleba", true, "chleba.png"));
        
        aktualniProstor = okrajLesa;  // hra začíná na rozcestí     
    }

    /**
     * Metoda rozhodne, zda je jídlo otrávené
     */
    public boolean isOtraveny(){
        return isOtraveny;
    }
    
    /**
     * Metoda nastavuje, která jídla jdou otrávená
     */
    public void setOtraveny(boolean b){
        isOtraveny = b;
    }

    public Hra getHra() {
        return hra;
    }
    
    
    
    /**
     * Metoda rozhodne, zda princezna jedla
     */
    public boolean jedlaJidlo(){
        return jedla;
    }

    /**
     * Metoda nastavuje, že princezna jedla
     */
    public void setJedla(boolean j){
        jedla = j;
    }
  
    /**
     *  Metoda vrací odkaz na aktuální prostor, ve ktetém se hráč právě nachází.
     *
     *@return     aktuální prostor
     */
    
    public Prostor getAktualniProstor() {
        return aktualniProstor;
    }
    
    /**
     *  Metoda nastaví aktuální prostor, používá se nejčastěji při přechodu mezi prostory
     *
     *@param  prostor nový aktuální prostor
     */
    public void setAktualniProstor(Prostor prostor) {
        aktualniProstor = prostor;
        notifyAllObservers();
    }
    
    /**
     * Metoda vrací počet zbývajících pokusů při odpovídání na otázku
     */
    public int getPokus(){
        return pokus;
    }
    
    /**
     * Metoda snižuje (resp. zvyšuje) počet pokusů (pokusy začíají na nule)
     */
    public void spatnaOdpoved(){
        pokus++;
    }
       
    /**
     * Metoda vrací vítězný prostor.
     */
    public Prostor getViteznyProstor() {
        return viteznyProstor;
    }
    
    /**
     * Metoda vrací prohrávací prostor.
     */
    public Prostor getProhravaciProstor() {
        return prohravaciProstor;
    }

    /**
     * Metoda vrací obsah kabelky.
     */
    public Batoh getBatoh() {
        return batoh;
    }

    @Override
    public void registerObserver(Observer observer) {
        listObserveru.add(observer);
    }

    @Override
    public void deleteObserver(Observer observer) {
        listObserveru.remove(observer);
    }

    @Override
    public void notifyAllObservers() {
        for (Observer listObserveruItem : listObserveru) {
            listObserveruItem.update();
        }
    }
    
}
