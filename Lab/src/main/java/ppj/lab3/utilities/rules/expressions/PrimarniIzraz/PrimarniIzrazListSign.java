package ppj.lab3.utilities.rules.expressions.PrimarniIzraz;

import ppj.lab3.utilities.SemanticProduction;
import ppj.lab3.utilities.rules.Action;
import ppj.lab3.utilities.scope.Scope;
import ppj.lab3.utilities.symbols.Symbol;

public class PrimarniIzrazListSign implements Action {
    @Override
    public void checkProduction(SemanticProduction production, Scope scope) {
        Symbol listSign = production.getRightStates().get(0);

        //TODO dovrsiti provjeru za ListChar
    }
}
