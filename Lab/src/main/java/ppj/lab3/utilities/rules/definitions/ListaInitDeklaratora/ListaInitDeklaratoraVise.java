package ppj.lab3.utilities.rules.definitions.ListaInitDeklaratora;

import ppj.lab3.utilities.SemanticProduction;
import ppj.lab3.utilities.attributes.SimpleAttribute;
import ppj.lab3.utilities.rules.Action;
import ppj.lab3.utilities.rules.RuleFactory;
import ppj.lab3.utilities.scope.Scope;
import ppj.lab3.utilities.symbols.NonTerminalSymbol;

public class ListaInitDeklaratoraVise implements Action {


    @Override
    public void checkProduction(SemanticProduction production, Scope scope) {
        //1. provjeri(<lista_init_deklaratora>2) uz nasljedno svojstvo
        //<lista_init_deklaratora>2.ntip ← <lista_init_deklaratora>1.ntip
        String listaNTip = production.getLeftState().getAttributeMap().get("ntype").getAttribute().toString();
        NonTerminalSymbol listaInitDeklaratora = (NonTerminalSymbol) production.getRightStates().get(0);
        listaInitDeklaratora.addAttribute("ntype", new SimpleAttribute(listaNTip));
        production.getRightStateNodes().get(0).setValue(listaInitDeklaratora);

        SemanticProduction productionToCheck = new SemanticProduction(production.getRightStateNodes().get(0));
        RuleFactory ruleFactory= RuleFactory.getRuleFactory();
        Action action= ruleFactory.getRuleMap().get(productionToCheck);
        action.checkProduction(productionToCheck,scope);

        //provjeri(<init_deklarator>) uz nasljedno svojstvo
        //<init_deklarator>.ntip ← <lista_init_deklaratora>1.ntip
        NonTerminalSymbol initDeklarator = (NonTerminalSymbol) production.getRightStates().get(2);
        initDeklarator.addAttribute("ntype", new SimpleAttribute(listaNTip));
        production.getRightStateNodes().get(2).setValue(initDeklarator);

        productionToCheck = new SemanticProduction(production.getRightStateNodes().get(2));
        action= ruleFactory.getRuleMap().get(productionToCheck);
        action.checkProduction(productionToCheck,scope);
    }
}
