


import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

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
        action = ruleFactory.getRuleMap().get(productionToCheck);
        action.checkProduction(productionToCheck, scope);
        expression = (NonTerminalSymbol) production.getRightStates().get(2);
        String type = expression.getAttributeMap().get("type").getAttribute().toString();

        //tipovi <- <lista_izraza_pridruzivanja>.tipovi + [ <izraz_pridruzivanja>.tip ]
        //br-elem <- <lista_izraza_pridruzivanja>.br-elem+ 1
        List<String> tempList = new LinkedList<>(Arrays.asList(types));
        tempList.add(type);
        types = tempList.toArray(new String[0]);
        production.getLeftState().addAttribute("types", new ListAttribute(types));
        String newNumElem = String.valueOf(Integer.parseInt(numElem) + 1);
        production.getLeftState().addAttribute("numElem", new SimpleAttribute(newNumElem));
    }
}
