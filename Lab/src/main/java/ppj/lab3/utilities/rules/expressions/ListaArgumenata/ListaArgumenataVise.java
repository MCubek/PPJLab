package ppj.lab3.utilities.rules.expressions.ListaArgumenata;

import ppj.lab3.utilities.SemanticProduction;
import ppj.lab3.utilities.attributes.ListAttribute;
import ppj.lab3.utilities.rules.Action;
import ppj.lab3.utilities.rules.RuleFactory;
import ppj.lab3.utilities.scope.Scope;
import ppj.lab3.utilities.symbols.NonTerminalSymbol;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class ListaArgumenataVise implements Action {


    @Override
    public void checkProduction(SemanticProduction production, Scope scope) {
        //1. provjeri(<lista_argumenata>)
        SemanticProduction productionToCheck = new SemanticProduction(production.getRightStateNodes().get(0));
        RuleFactory ruleFactory= RuleFactory.getRuleFactory();
        Action action= ruleFactory.getRuleMap().get(productionToCheck);
        action.checkProduction(productionToCheck,scope);
        NonTerminalSymbol expression = (NonTerminalSymbol) production.getRightStates().get(0);
        String[] types = (String[]) expression.getAttributeMap().get("types").getAttribute();

        //2. provjeri(<izraz_pridruzivanja>)
        productionToCheck = new SemanticProduction(production.getRightStateNodes().get(2));
        action = ruleFactory.getRuleMap().get(productionToCheck);
        action.checkProduction(productionToCheck,scope);
        expression = (NonTerminalSymbol) production.getRightStates().get(2);
        String type = expression.getAttributeMap().get("type").getAttribute().toString();

        //tipovi ← <lista_argumenata>.tipovi + [ <izraz_pridruzivanja>.tip ]
        List<String> supportList = new LinkedList<>(Arrays.asList(types));
        supportList.add(type);
        types = supportList.toArray(new String[0]);
        production.getLeftState().addAttribute("types", new ListAttribute(types));
    }
}
