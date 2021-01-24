package ppj.lab3.utilities.rules.definitions.InitDeklarator;

import ppj.lab3.SemanticException;
import ppj.lab3.utilities.SemanticProduction;
import ppj.lab3.utilities.attributes.SimpleAttribute;
import ppj.lab3.utilities.rules.Action;
import ppj.lab3.utilities.rules.RuleFactory;
import ppj.lab3.utilities.scope.Scope;
import ppj.lab3.utilities.symbols.NonTerminalSymbol;
import ppj.lab3.utilities.symbols.Symbol;
import ppj.lab3.utilities.symbols.TerminalSymbol;
import ppj.lab4.GeneratorKoda;
import ppj.utilities.Node;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class InitDeklaratorBez implements Action {


    @Override
    public void checkProduction(SemanticProduction production, Scope scope) {
        //1. provjeri(<izravni_deklarator>) uz nasljedno svojstvo
        //<izravni_deklarator>.ntip <- <init_deklarator>.ntip

        if (scope.getParent() == null) {
            GeneratorKoda.global = true;
        }

        String listaNTip = production.getLeftState().getAttributeMap().get("ntype").getAttribute().toString();
        NonTerminalSymbol izravniDeklarator = (NonTerminalSymbol) production.getRightStates().get(0);
        izravniDeklarator.addAttribute("ntype", new SimpleAttribute(listaNTip));
        production.getRightStateNodes().get(0).setValue(izravniDeklarator);

        SemanticProduction productionToCheck = new SemanticProduction(production.getRightStateNodes().get(0));
        RuleFactory ruleFactory = RuleFactory.getRuleFactory();
        Action action = ruleFactory.getRuleMap().get(productionToCheck);
        action.checkProduction(productionToCheck, scope);
        NonTerminalSymbol expression = (NonTerminalSymbol) production.getRightStates().get(0);
        String type = expression.getAttributeMap().get("type").getAttribute().toString();

        //2. <izravni_deklarator>.tip 6= const(T)
        //i
        //<izravni_deklarator>.tip 6= niz (const(T))
        if (type.contains("const"))
            throw new SemanticException(production.toString());

        var nextList = production.getRightStateNodes().get(0).getChildren().stream()
                .map(Node::getValue)
                .map(Symbol::getSymbolName)
                .collect(Collectors.toList());

        if (GeneratorKoda.global) {
            String name;


            if (nextList.contains("IDN") && nextList.size() == 1) {
                var value = 0;
                name = ((TerminalSymbol) production.getRightStateNodes().get(0).getChildren().get(0).getValue()).getLexicalUnits()[0];

                GeneratorKoda.codeBuilder.addCommand("POP R0");
                GeneratorKoda.memoryLocations.put(GeneratorKoda.getGlobalLabel(name), value);

            } else if (nextList.equals(Arrays.asList("IDN", "L_UGL_ZAGRADA", "BROJ", "D_UGL_ZAGRADA"))) {
                List<Integer> list = new ArrayList<>();
                name = ((TerminalSymbol) production.getRightStateNodes().get(0).getChildren().get(0).getValue()).getLexicalUnits()[0];

                int numOfEl = Integer.parseInt((String) izravniDeklarator.getAttributeMap().get("numElem").getAttribute());
                for (int i = 0; i < numOfEl; i++) {
                    list.add(0);
                }
                GeneratorKoda.memoryArrays.put(GeneratorKoda.getGlobalLabel(name), list);
            }

            GeneratorKoda.global = false;
        }
    }
}
