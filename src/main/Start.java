/**
 * Trida Start slouzi ke spusteni aplikace.
 *
 * @author  Julietta Mwansová
 * @version pro letní semestr 2015/2016
 */

public class Start
{
    /**
     * Metoda, prostrednictvim niz se spousti cela aplikace.
     *
     * @param args  Parametry prikazoveho radku
     */
    public static void main(String[] args)
    {
        TextoveRozhrani rozhrani = new TextoveRozhrani();
        if(args.length == 0) {
            rozhrani.hraj();
        }
        else {
            rozhrani.hrajZeSouboru(args[0]);
        }
    }
}
