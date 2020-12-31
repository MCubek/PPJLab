package ppj.lab3.utilities.rules.definitions.DefinicijaFunkcije;

import ppj.lab3.SemanticException;
import ppj.lab3.utilities.SemanticProduction;
import ppj.lab3.utilities.rules.Action;
import ppj.lab3.utilities.rules.RuleFactory;
import ppj.lab3.utilities.scope.Scope;
import ppj.lab3.utilities.scope.ScopeElement;
import ppj.lab3.utilities.symbols.NonTerminalSymbol;
import ppj.lab3.utilities.symbols.TerminalSymbol;

public class DefinicijaFunkcijeVoid implements Action {


    @Override
    public void checkProduction(SemanticProduction production, Scope scope) {
        //1. provjeri(<ime_tipa>)
        SemanticProduction productionToCheck = new SemanticProduction(production.getRightStateNodes().get(0));
        RuleFactory ruleFactory= RuleFactory.getRuleFactory();
        Action action= ruleFactory.getRuleMap().get(productionToCheck);
        action.checkProduction(productionToCheck,scope);
        NonTerminalSymbol expression = (NonTerminalSymbol) production.getRightStates().get(0);
        String type = expression.getAttributeMap().get("type").getAttribute().toString();

        //2. <ime_tipa>.tip != const(T)
        if(type.startsWith("const"))
            throw new SemanticException(production.toString());

        //3. ne postoji prije definirana funkcija imena IDN.ime
        TerminalSymbol idn = (TerminalSymbol) production.getRightStates().get(1);
        String idnName = idn.getLexicalUnits()[0];
        if(scope.isDefined(idnName))
            throw new SemanticException(production.toString());

        //4. ako postoji deklaracija imena IDN.ime u globalnom djelokrugu onda je pripadni
        //tip te deklaracije funkcija(void → <ime_tipa>.tip)
        Scope globalScope = scope;
        while(globalScope.getParent() != null)
            globalScope = globalScope.getParent();
        ScopeElement idnScope = globalScope.isDeclared(idnName);
        if(idnScope == null)
            throw new SemanticException(production.toString());
        String scopeIdnType = idnScope.getType();
        String checkType = "funkcija(void -> " + type + ")";
        if(!scopeIdnType.equals(checkType))
            throw new SemanticException(production.toString());

        //5. zabiljezi definiciju i deklaraciju funkcije
        scope.addDefinition(idnName,scopeIdnType, false);

        //6. provjeri(<slozena_naredba>)
         productionToCheck = new SemanticProduction(production.getRightStateNodes().get(5));
         action= ruleFactory.getRuleMap().get(productionToCheck);
         action.checkProduction(productionToCheck,scope);
    }
}
