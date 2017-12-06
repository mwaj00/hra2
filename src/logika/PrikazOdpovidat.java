package logika;
/**
 *  Třída PrikazNapoveda implementuje pro hru příkaz odpovez.
 *  Tato třída je součástí jednoduché textové hry.
 *  
 * @author    Julietta Mwansová
 * @version   pro letní semestr 2015/2016
 */
public class PrikazOdpovidat implements IPrikaz
{
    private static final String NAZEV = "odpovidat";
    private HerniPlan plan;
    private Hra hra;
     
    /**
     *  Konstruktor třídy
     *  
     * @param plan herní plán, ve kterém se bude ve hře "odpovídat" 
     * @param hra hra 
     */ 
    public PrikazOdpovidat(HerniPlan plan, Hra hra)
    {
        this.plan = plan;
        this.hra = hra;
    }

    /**
     * Metoda, která slouží k vyhodnocení, zda bylo na otázku odpovězeno ve správném prostoru a správně. Spravná odpoveď je pouze 7 nebo sedm, jinak 
     * konec hry po třech pokusech. Odpoví-li hráč správně, vyhrává.
     */
    @Override
    public String proved(String... parametry) {
        if (parametry.length == 0) {
            // pokud chybí druhé slovo, tak ....
            return "Musis zadat odpoved.";
        }
        if (parametry.length == 1) {
            if(plan.getAktualniProstor().getNazev().equals("hriste") && plan.isOtraveny()) 
            { 
                hra.setKonecHry(true);
                return "Nemuzes odpovedet. Zemrela jsi na otravu jablkem. - KONEC HRY";
            }
            
            if(plan.getAktualniProstor().getNazev().equals("hriste")) 
            { 
                    if(plan.getPokus()>=2)
                    {
                            hra.setKonecHry(true);                    
                            return "Došly ti pokusy - KONEC HRY";  
                    }
                
                    if((parametry[0].equals("jmeno") || parametry[0].equals("jméno")))
                    {
                            hra.setKonecHry(true);                    
                            return "Spravne! Tvuj bratr hraje v pisku za velkym tresnem.";
                    }
                    else 
                    { 
                            plan.spatnaOdpoved();
                            return "Spatna odpoved, muzes hadat jeste " + (2 - plan.getPokus()) + "krat";
                    }
            }
            else 
            {
                //pokud se příkaz odpovidat zadá v jiném prostoru než hriste tak...
                return "Tady nemas komu odpovedet."; 
            }
        }
        return "odpoved";
    }

    /**
     *  Metoda vrací název příkazu (slovo které používá hráč pro jeho vyvolání)
     *  
     *  @return nazev prikazu
     */
    public String getNazev() {
        return NAZEV;
    }
}