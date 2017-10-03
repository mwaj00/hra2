/**
 *  Třída PrikazBatoh implementuje pro hru příkaz batoh.
 *  Tato třída je součástí jednoduché textové hry.
 *  
 * @author    Julietta Mwansová
 * @version   pro letní semestr 2015/2016
 */
public class PrikazBatoh implements IPrikaz
{
    private static final String NAZEV = "batoh";
    private HerniPlan plan;

    /***************************************************************************
     * Konstruktor třídy
     * 
     * @param plan herní plán, ve kterém se bude ve hře zobrazovat obsah batohu 
     */
    public PrikazBatoh(HerniPlan plan)
    {
        this.plan = plan;
    }

    /**
     * Metoda slouží pro vypsání obsahu kabelky po zadání příkazu batoh.
     */
    @Override
    public String proved(String... parametry) {
        String result = "";
        if (parametry.length > 0) {
            // pokud se zadá druhý parametr
            return "Tento prikaz je bez parametru.";
        } else {
            result = plan.getBatoh().toString();     
        }
        return result; //vypíše obsah batohu
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
