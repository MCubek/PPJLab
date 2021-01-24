


public class NaredbaSkokaReturnBez implements Action {


    @Override
    public void checkProduction(SemanticProduction production, Scope scope) {
        //1. naredba se nalazi unutar funkcije tipa funkcija(params -> void)
        String type = production.getLeftStateNode().returnFunctionType();
        if(type == null)
            throw new SemanticException(production.toString());
        String trimmedType = type.substring(type.indexOf(">") + 1, type.length()-1).trim();
        if(!trimmedType.equals("void"))
            throw new SemanticException(production.toString());
    }
}
