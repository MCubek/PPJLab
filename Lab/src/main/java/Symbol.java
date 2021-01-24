/**
 * Sucelje objekata stabla
 *
 * @author MatejCubek, FraneB
 * @project PPJLab
 * @created 23/12/2020
 */
public interface Symbol {
    /**
     * Metoda koja vraca ime tipa objekta.
     *
     * @return tip objekta.
     */
    SYMBOL_TYPE getType();

    /**
     * Metoda koja ce vratiti ime leksicke jedinke
     *
     * @return ime leksicke jedinke.
     */
    String getSymbolName();


    /**
     * Enumeracija za vrijednosti tipa objekta
     */
    enum SYMBOL_TYPE {
        TERMINAL,
        NON_TERMINAL
    }
}
