package ppj.lab3.utilities.rules;

import ppj.lab3.utilities.SemanticProduction;
import ppj.lab3.utilities.scope.Scope;

public interface Action {

    void checkProduction(SemanticProduction production, Scope scope);
}
