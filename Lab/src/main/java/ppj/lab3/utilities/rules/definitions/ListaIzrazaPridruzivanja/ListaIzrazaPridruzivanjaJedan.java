package ppj.lab3.utilities.rules.definitions.ListaIzrazaPridruzivanja;

import ppj.lab3.utilities.SemanticProduction;
import ppj.lab3.utilities.attributes.ListAttribute;
import ppj.lab3.utilities.attributes.SimpleAttribute;
import ppj.lab3.utilities.rules.Action;
import ppj.lab3.utilities.rules.RuleFactory;
import ppj.lab3.utilities.scope.Scope;
import ppj.lab3.utilities.symbols.NonTerminalSymbol;

public class ListaIzrazaPridruzivanjaJedan implements Action {


    @Override
    public void checkProduction(SemanticProduction production, Scope scope) {
        //1. provjeri(<izraz_pridruzivanja>)
        SemanticProduction productionToCheck = new SemanticProduction(production.getRightStateNodes().get(0));
        RuleFactory ruleFactory= RuleFactory.getRuleFactory();
        Action action= ruleFactory.getRuleMap().get(productionToCheck);
        action.checkProduction(productionToCheck,scope);
        NonTerminalSymbol expression = (NonTerminalSymbol) production.getRightStates().get(0);

        //tipovi ← [ <izraz_pridruzivanja>.tip ]
        //br-elem ← 1
        production.getLeftState().addAttribute("numElem", new SimpleAttribute("1"));
        production.getLeftState().addAttribute("types", new ListAttribute(expression.getAttributeMap().get("type").getAttribute().toString()));
    }
}
