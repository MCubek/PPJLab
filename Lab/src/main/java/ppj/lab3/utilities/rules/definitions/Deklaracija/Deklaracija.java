package ppj.lab3.utilities.rules.definitions.Deklaracija;

import ppj.lab3.utilities.SemanticProduction;
import ppj.lab3.utilities.attributes.SimpleAttribute;
import ppj.lab3.utilities.rules.Action;
import ppj.lab3.utilities.rules.RuleFactory;
import ppj.lab3.utilities.scope.Scope;
import ppj.lab3.utilities.symbols.NonTerminalSymbol;

public class Deklaracija implements Action {


    @Override
    public void checkProduction(SemanticProduction production, Scope scope) {
        //1. provjeri(<ime_tipa>)
        SemanticProduction productionToCheck = new SemanticProduction(production.getRightStateNodes().get(0));
        RuleFactory ruleFactory= RuleFactory.getRuleFactory();
        Action action= ruleFactory.getRuleMap().get(productionToCheck);
        action.checkProduction(productionToCheck,scope);
        NonTerminalSymbol expression = (NonTerminalSymbol) production.getRightStates().get(0);
        String imeTipa = expression.getAttributeMap().get("type").getAttribute().toString();

        //2. provjeri(<lista_init_deklaratora>) uz nasljedno svojstvo
        //<lista_init_deklaratora>.ntip <- <ime_tipa>.tip
        NonTerminalSymbol listaInitDeklaratora = (NonTerminalSymbol) production.getRightStates().get(1);
        listaInitDeklaratora.addAttribute("ntype", new SimpleAttribute(imeTipa));
        production.getRightStateNodes().get(1).setValue(listaInitDeklaratora);

        productionToCheck = new SemanticProduction(production.getRightStateNodes().get(1));
        action= ruleFactory.getRuleMap().get(productionToCheck);
        action.checkProduction(productionToCheck,scope);
    }
}
