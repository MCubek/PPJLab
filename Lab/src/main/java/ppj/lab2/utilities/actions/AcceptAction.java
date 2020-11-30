package ppj.lab2.utilities.actions;

import ppj.lab2.utilities.Production;

/**
 * @author MatejCubek
 * @project PPJLab
 * @created 30/11/2020
 */
public class AcceptAction implements Action {
    private Production startProduction;
    private static final long serialVersionUID = - 1648395265078313279L;

    public AcceptAction(Production startProduction) {
        this.startProduction = startProduction;
    }

    public AcceptAction() {
    }

    public Production getStartProduction() {
        return startProduction;
    }
}
