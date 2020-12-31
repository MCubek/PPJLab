package ppj.lab3.utilities.rules.definitions.IzravniDeklarator;

import ppj.lab3.SemanticException;
import ppj.lab3.utilities.SemanticProduction;
import ppj.lab3.utilities.rules.Action;
import ppj.lab3.utilities.rules.RuleFactory;
import ppj.lab3.utilities.scope.Scope;
import ppj.lab3.utilities.scope.ScopeElement;
import ppj.lab3.utilities.symbols.NonTerminalSymbol;
import ppj.lab3.utilities.symbols.TerminalSymbol;

import java.util.Arrays;

public class IzravniDeklaratorFuncParam implements Action {


    @Override
    public void checkProduction(SemanticProduction production, Scope scope) {
        //1. provjeri(<lista_parametara>)
        SemanticProduction productionToCheck = new SemanticProduction(production.getRightStateNodes().get(2));
        RuleFactory ruleFactory= RuleFactory.getRuleFactory();
        Action action= ruleFactory.getRuleMap().get(productionToCheck);
        action.checkProduction(productionToCheck,scope);
        NonTerminalSymbol expression = (NonTerminalSymbol) production.getRightStates().get(2);
        String[] types = (String[]) expression.getAttributeMap().get("type").getAttribute();

        //2. ako je IDN.ime deklarirano u lokalnom djelokrugu, tip prethodne deklaracije
        //je jednak funkcija(<lista_parametara>.tipovi → ntip)
        String ntype = production.getLeftState().getAttributeMap().get("ntype").getAttribute().toString();
        TerminalSymbol idn = (TerminalSymbol) production.getRightStates().get(0);
        String idnName = idn.getLexicalUnits()[0];
        ScopeElement declaredIdn = scope.isDeclaredLocally(idnName);
        StringBuilder checkType = new StringBuilder("funkcija(");
        for(String string : types) {
            checkType.append(string);
            if(!Arrays.asList(types).get(Arrays.asList(types).size()-1).equals(string))
                checkType.append(", ");
        }
        checkType.append(" -> ").append(ntype);
        if(declaredIdn != null) {
            if(!declaredIdn.getName().equals(checkType.toString()))
                throw new SemanticException(production.toString());
        } else {
            //3. zabiljezi deklaraciju IDN.ime s odgovarajucim tipom ako ista funkcija vec nije
            //deklarirana u lokalnom djelokrugu
            scope.addScopeElement(new ScopeElement(idnName, checkType.toString(), false, false));
        }
    }
}
