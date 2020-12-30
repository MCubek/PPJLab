package ppj.lab3.utilities.symbols;

/**
 * Sučelje objekata stabla
 *
 * @author MatejCubek, FraneB
 * @project PPJLab
 * @created 23/12/2020
 */
public interface Symbol {
    /**
     * Metoda koja vraća ime tipa objekta.
     *
     * @return tip objekta.
     */
    SYMBOL_TYPE getType();

    /**
     * Metoda koja će vratiti ime leksičke jedinke
     *
     * @return ime leksičke jedinke.
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
