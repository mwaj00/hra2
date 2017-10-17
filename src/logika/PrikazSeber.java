package logika;
/**
 *  Třída PrikazSeber implementuje pro hru příkaz seber.
 *  Tato třída je součástí jednoduché textové hry.
 *  
 * @author    Julietta Mwansová
 * @version   pro letní semestr 2015/2016
 */
public class PrikazSeber implements IPrikaz
{
    private static final String NAZEV = "seber";
    private HerniPlan plan;

    /***************************************************************************
     * Konstuktor třídy
     * 
     * @param plan herní plán, ve kterém se bude ve hře "sbírat" 
     */
    public PrikazSeber(HerniPlan plan)
    {
        this.plan = plan;
    }

    /**
     * Provádí příkaz seber. Zkouší sebrat věci z prostoru a vkládá je do batohu.
     */
    @Override
    public String proved(String... parametry) {
        if (parametry.length == 0) {
            // pokud chybí druhé slovo, tak ....
            return "Co mam vzit? Musite zadat vec z prostoru.";
        }
        else {
            if(parametry.length == 1 && plan.getAktualniProstor().obsahujeVec(parametry[0])){
                // pokud je druhe slovo takové, které lze vložit do batohu
                plan.getBatoh().vlozVec(plan.getAktualniProstor().odeberVec(parametry[0]));
                return "Vlozila jsi do batohu "+ parametry[0] + ".";
            }
        }
        return "Tento predmet neznam.";
    }

    
     /**
     *  Metoda vrací název příkazu (slovo které používá hráč pro jeho vyvolání)
     *  
     *  @ return nazev prikazu
     */
    public String getNazev() {
        return NAZEV;
    }

}
