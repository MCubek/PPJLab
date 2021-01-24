


public class DefinicijaFunkcijeVoid implements Action {


    @Override
    public void checkProduction(SemanticProduction production, Scope scope) {

        GeneratorKoda.codeBuilder.addCommandWithLabel(
                GeneratorKoda.getFunctionLabel(((TerminalSymbol) production.getRightStates().get(1)).getLexicalUnits()[0]),
                "MOVE R7, R6");


        //1. provjeri(<ime_tipa>)
        SemanticProduction productionToCheck = new SemanticProduction(production.getRightStateNodes().get(0));
        RuleFactory ruleFactory = RuleFactory.getRuleFactory();
        Action action = ruleFactory.getRuleMap().get(productionToCheck);
        action.checkProduction(productionToCheck, scope);
        NonTerminalSymbol expression = (NonTerminalSymbol) production.getRightStates().get(0);
        String type = expression.getAttributeMap().get("type").getAttribute().toString();

        //2. <ime_tipa>.tip != const(T)
        if (type.startsWith("const"))
            throw new SemanticException(production.toString());

        //3. ne postoji prije definirana funkcija imena IDN.ime
        TerminalSymbol idn = (TerminalSymbol) production.getRightStates().get(1);
        String idnName = idn.getLexicalUnits()[0];
        if (scope.isDefined(idnName))
            throw new SemanticException(production.toString());

        //4. ako postoji deklaracija imena IDN.ime u globalnom djelokrugu onda je pripadni
        //tip te deklaracije funkcija(void -> <ime_tipa>.tip)
        Scope globalScope = scope;
        while (globalScope.getParent() != null)
            globalScope = globalScope.getParent();
        ScopeElement idnScope = globalScope.isDeclared(idnName);
        String checkType = "funkcija(void -> " + type + ")";

        if (idnScope != null) {
            String scopeIdnType = idnScope.getType();
            if (! scopeIdnType.equals(checkType))
                throw new SemanticException(production.toString());

        }
        //5. zabiljezi definiciju i deklaraciju funkcije
        scope.addDefinition(idnName, checkType, false);
        production.getLeftState().addAttribute("type", new SimpleAttribute(checkType));

        //6. provjeri(<slozena_naredba>)
        productionToCheck = new SemanticProduction(production.getRightStateNodes().get(5));
        action = ruleFactory.getRuleMap().get(productionToCheck);
        action.checkProduction(productionToCheck, scope);

        GeneratorKoda.codeBuilder.addCommand("MOVE R6, R7");
        GeneratorKoda.codeBuilder.addCommand("RET");
    }
}
