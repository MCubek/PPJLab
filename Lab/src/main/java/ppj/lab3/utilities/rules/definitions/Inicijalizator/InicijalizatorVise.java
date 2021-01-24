package ppj.lab3.utilities.rules.definitions.Inicijalizator;

import ppj.lab3.utilities.SemanticProduction;
import ppj.lab3.utilities.attributes.ListAttribute;
import ppj.lab3.utilities.attributes.SimpleAttribute;
import ppj.lab3.utilities.rules.Action;
import ppj.lab3.utilities.rules.RuleFactory;
import ppj.lab3.utilities.scope.Scope;
import ppj.lab3.utilities.symbols.NonTerminalSymbol;

public class InicijalizatorVise implements Action {


    @Override
    public void checkProduction(SemanticProduction production, Scope scope) {
        //1. provjeri(<lista_izraza_pridruzivanja>)
        SemanticProduction productionToCheck = new SemanticProduction(production.getRightStateNodes().get(1));
        RuleFactory ruleFactory = RuleFactory.getRuleFactory();
        Action action = ruleFactory.getRuleMap().get(productionToCheck);
        action.checkProduction(productionToCheck, scope);
        NonTerminalSymbol expression = (NonTerminalSymbol) production.getRightStates().get(1);

        //br-elem <- <lista_izraza_pridruzivanja>.br-elem
        //tipovi <- <lista_izraza_pridruzivanja>.tipovi
        production.getLeftState().addAttribute("numElem", new SimpleAttribute(expression.getAttributeMap().get("numElem").getAttribute().toString()));
        production.getLeftState().addAttribute("types", new ListAttribute((String[]) expression.getAttributeMap().get("types").getAttribute()));
    }
}
