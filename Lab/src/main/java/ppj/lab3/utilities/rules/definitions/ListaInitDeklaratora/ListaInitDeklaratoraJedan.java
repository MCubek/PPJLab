package ppj.lab3.utilities.rules.definitions.ListaInitDeklaratora;

import ppj.lab3.utilities.SemanticProduction;
import ppj.lab3.utilities.attributes.SimpleAttribute;
import ppj.lab3.utilities.rules.Action;
import ppj.lab3.utilities.rules.RuleFactory;
import ppj.lab3.utilities.scope.Scope;
import ppj.lab3.utilities.symbols.NonTerminalSymbol;

public class ListaInitDeklaratoraJedan implements Action {


    @Override
    public void checkProduction(SemanticProduction production, Scope scope) {
        //1. provjeri(<init_deklarator>) uz nasljedno svojstvo
        //<init_deklarator>.ntip <- <lista_init_deklaratora>.ntip
        String listaNTip = production.getLeftState().getAttributeMap().get("ntype").getAttribute().toString();
        NonTerminalSymbol initDeklarator = (NonTerminalSymbol) production.getRightStates().get(0);
        initDeklarator.addAttribute("ntype", new SimpleAttribute(listaNTip));
        production.getRightStateNodes().get(0).setValue(initDeklarator);

        SemanticProduction productionToCheck = new SemanticProduction(production.getRightStateNodes().get(0));
        RuleFactory ruleFactory= RuleFactory.getRuleFactory();
        Action action= ruleFactory.getRuleMap().get(productionToCheck);
        action.checkProduction(productionToCheck,scope);
    }
}
