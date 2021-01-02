package ppj.lab3.utilities.rules.definitions.ListaParametara;

import ppj.lab3.SemanticException;
import ppj.lab3.utilities.SemanticProduction;
import ppj.lab3.utilities.attributes.ListAttribute;
import ppj.lab3.utilities.rules.Action;
import ppj.lab3.utilities.rules.RuleFactory;
import ppj.lab3.utilities.scope.Scope;
import ppj.lab3.utilities.symbols.NonTerminalSymbol;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class ListaParametaraVise implements Action {


    @Override
    public void checkProduction(SemanticProduction production, Scope scope) {
        //1. provjeri(<lista_parametara>)
        SemanticProduction productionToCheck = new SemanticProduction(production.getRightStateNodes().get(0));
        RuleFactory ruleFactory= RuleFactory.getRuleFactory();
        Action action= ruleFactory.getRuleMap().get(productionToCheck);
        action.checkProduction(productionToCheck,scope);
        NonTerminalSymbol expression = (NonTerminalSymbol) production.getRightStates().get(0);
        String[] types = (String[]) expression.getAttributeMap().get("types").getAttribute();
        String[] names = (String[]) expression.getAttributeMap().get("names").getAttribute();

        //2. provjeri(<deklaracija_parametra>)
        productionToCheck = new SemanticProduction(production.getRightStateNodes().get(2));
        action = ruleFactory.getRuleMap().get(productionToCheck);
        action.checkProduction(productionToCheck,scope);
        expression = (NonTerminalSymbol) production.getRightStates().get(2);
        String deklName = expression.getAttributeMap().get("name").getAttribute().toString();
        String deklType = expression.getAttributeMap().get("type").getAttribute().toString();

        //3. <deklaracija_parametra>.ime ne postoji u <lista_parametara>.imena
        if(Arrays.asList(names).contains(deklName))
            throw new SemanticException(production.toString());

        //tipovi ← <lista_parametara>.tipovi + [ <deklaracija_parametra>.tip ]
        //imena ← <lista_parametara>.imena + [ <deklaracija_parametra>.ime ]

        List<String> tempNamesList = new LinkedList<>(Arrays.asList(names));
        List<String> tempTypesList = new LinkedList<>(Arrays.asList(types));
        tempNamesList.add(deklName);
        tempTypesList.add(deklType);
        names = tempNamesList.toArray(new String[0]);
        types = tempTypesList.toArray(new String[0]);
        production.getLeftState().addAttribute("names", new ListAttribute(names));
        production.getLeftState().addAttribute("types", new ListAttribute(types));
    }
}
