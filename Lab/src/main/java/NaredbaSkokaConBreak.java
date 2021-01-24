


public class NaredbaSkokaConBreak implements Action {


    @Override
    public void checkProduction(SemanticProduction production, Scope scope) {

        //1. naredba se nalazi unutar petlje ili unutar bloka koji je ugnijezden u petlji
        if(!production.getLeftStateNode().isInLoop()) {
            throw new SemanticException(production.toString());
        }
    }
}
