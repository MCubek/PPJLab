package ppj.lab3.utilities.rules.definitions.ListaIzrazaPridruzivanja;

import ppj.lab3.utilities.SemanticProduction;
import ppj.lab3.utilities.attributes.ListAttribute;
import ppj.lab3.utilities.attributes.SimpleAttribute;
import ppj.lab3.utilities.rules.Action;
import ppj.lab3.utilities.rules.RuleFactory;
import ppj.lab3.utilities.scope.Scope;
import ppj.lab3.utilities.symbols.NonTerminalSymbol;

import java.util.Arrays;

public class ListaIzrazaPridruzivanjaVise implements Action {


    @Override
    public void checkProduction(SemanticProduction production, Scope scope) {
        //1. provjeri(<lista_izraza_pridruzivanja>)
        SemanticProduction productionToCheck = new SemanticProduction(production.getRightStateNodes().get(0));
        RuleFactory ruleFactory= RuleFactory.getRuleFactory();
        Action action= ruleFactory.getRuleMap().get(productionToCheck);
        action.checkProduction(productionToCheck,scope);
        NonTerminalSymbol expression = (NonTerminalSymbol) production.getRightStates().get(0);
        String[] types = (String[]) expression.getAttributeMap().get("types").getAttribute();
        String numElem = expression.getAttributeMap().get("numElem").getAttribute().toString();

        //2. provjeri(<izraz_pridruzivanja>)
        productionToCheck = new SemanticProduction(production.getRightStateNodes().get(2));
        action= ruleFactory.getRuleMap().get(productionToCheck);
        action.checkProduction(productionToCheck,scope);
        expression = (NonTerminalSymbol) production.getRightStates().get(2);
        String type = expression.getAttributeMap().get("type").getAttribute().toString();

        //tipovi ← <lista_izraza_pridruzivanja>.tipovi + [ <izraz_pridruzivanja>.tip ]
        //br-elem ← <lista_izraza_pridruzivanja>.br-elem+ 1
        Arrays.asList(types).add(type);
        production.getLeftState().addAttribute("types", new ListAttribute(types));
        String newNumElem = String.valueOf(Integer.parseInt(numElem) + 1);
        production.getLeftState().addAttribute("numElem", new SimpleAttribute(newNumElem));
    }
}
