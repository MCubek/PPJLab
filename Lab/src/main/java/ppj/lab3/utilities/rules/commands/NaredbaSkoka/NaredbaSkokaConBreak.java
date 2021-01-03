package ppj.lab3.utilities.rules.commands.NaredbaSkoka;

import ppj.lab3.SemanticException;
import ppj.lab3.utilities.SemanticProduction;
import ppj.lab3.utilities.rules.Action;
import ppj.lab3.utilities.scope.Scope;

public class NaredbaSkokaConBreak implements Action {


    @Override
    public void checkProduction(SemanticProduction production, Scope scope) {

        //1. naredba se nalazi unutar petlje ili unutar bloka koji je ugnijezden u petlji
        if(!production.getLeftStateNode().isInLoop()) {
            throw new SemanticException(production.toString());
        }
    }
}
