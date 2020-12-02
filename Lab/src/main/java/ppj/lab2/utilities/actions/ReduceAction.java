package ppj.lab2.utilities.actions;

import ppj.lab2.utilities.Production;

/**
 * Akcija redukcije
 *
 * @author MatejCubek
 * @project PPJLab
 * @created 30/11/2020
 */
public class ReduceAction implements Action {
    private static final long serialVersionUID = - 6387348805232163677L;
    private final Production production;

    public ReduceAction(Production production) {
        this.production = production;
    }

    public Production getProduction() {
        return production;
    }

    @Override
    public String getName() {
        return "Reduce";
    }
}
