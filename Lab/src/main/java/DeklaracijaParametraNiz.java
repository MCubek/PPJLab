


public class DeklaracijaParametraNiz implements Action {


    @Override
    public void checkProduction(SemanticProduction production, Scope scope) {
        //1. provjeri(<ime_tipa>)
        SemanticProduction productionToCheck = new SemanticProduction(production.getRightStateNodes().get(0));
        RuleFactory ruleFactory= RuleFactory.getRuleFactory();
        Action action= ruleFactory.getRuleMap().get(productionToCheck);
        action.checkProduction(productionToCheck, scope);
        NonTerminalSymbol expression = (NonTerminalSymbol) production.getRightStates().get(0);
        TerminalSymbol idn = (TerminalSymbol) production.getRightStates().get(1);
        String idnName = idn.getLexicalUnits()[0];
        String type = expression.getAttributeMap().get("type").getAttribute().toString();

        //2. <ime_tipa>.tip != void
        if (type.equals("void"))
            throw new SemanticException(production.toString());

        //tip <- <ime_tipa>.tip
        //ime <- IDN.ime
        String newType = "niz(" + type + ")";
        production.getLeftState().addAttribute("type", new SimpleAttribute(newType));
        production.getLeftState().addAttribute("name", new SimpleAttribute(idnName));
    }
}
