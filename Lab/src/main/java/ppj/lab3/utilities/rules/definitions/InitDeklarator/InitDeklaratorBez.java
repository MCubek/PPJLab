package ppj.lab3.utilities.rules.definitions.InitDeklarator;

import ppj.lab3.SemanticException;
import ppj.lab3.utilities.SemanticProduction;
import ppj.lab3.utilities.attributes.SimpleAttribute;
import ppj.lab3.utilities.rules.Action;
import ppj.lab3.utilities.rules.RuleFactory;
import ppj.lab3.utilities.scope.Scope;
import ppj.lab3.utilities.symbols.NonTerminalSymbol;

public class InitDeklaratorBez implements Action {


    @Override
    public void checkProduction(SemanticProduction production, Scope scope) {
        //1. provjeri(<izravni_deklarator>) uz nasljedno svojstvo
        //<izravni_deklarator>.ntip <- <init_deklarator>.ntip
        String listaNTip = production.getLeftState().getAttributeMap().get("ntype").getAttribute().toString();
        NonTerminalSymbol izravniDeklarator = (NonTerminalSymbol) production.getRightStates().get(0);
        izravniDeklarator.addAttribute("ntype", new SimpleAttribute(listaNTip));
        production.getRightStateNodes().get(0).setValue(izravniDeklarator);

        SemanticProduction productionToCheck = new SemanticProduction(production.getRightStateNodes().get(0));
        RuleFactory ruleFactory= RuleFactory.getRuleFactory();
        Action action= ruleFactory.getRuleMap().get(productionToCheck);
        action.checkProduction(productionToCheck,scope);
        NonTerminalSymbol expression = (NonTerminalSymbol) production.getRightStates().get(0);
        String type = expression.getAttributeMap().get("type").getAttribute().toString();

        //2. <izravni_deklarator>.tip 6= const(T)
        //i
        //<izravni_deklarator>.tip 6= niz (const(T))
        if(type.contains("const"))
            throw new SemanticException(production.toString());
    }
}
