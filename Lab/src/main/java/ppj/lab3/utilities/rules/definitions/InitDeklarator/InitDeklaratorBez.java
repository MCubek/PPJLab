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

import java.util.Arrays;
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

        //TODO DRUGI UVJEZ
        if (GeneratorKoda.global) {
            String name = null;
            var value = 0;

            var nextList = productionToCheck.getRightStates().stream()
                    .map(Symbol::getSymbolName)
                    .collect(Collectors.toList());

            if (nextList.contains("IDN") && nextList.size() == 1) {
                name = ((TerminalSymbol) productionToCheck.getRightStates().get(0)).getLexicalUnits()[0];

                GeneratorKoda.codeBuilder.addCommand("POP R0");
                GeneratorKoda.codeBuilder.addCommand("STORE RO, (" + GeneratorKoda.getGlobalLabel(name) + ")");
            } else if (nextList.equals(Arrays.asList("IDN", "L_UGL_ZAGRADA", "BROJ", "D_UGL_ZAGRADA"))) {
                //TODO
            }

            if (name != null)
                GeneratorKoda.memoryLocations.put(GeneratorKoda.getGlobalLabel(name), value);
        }

        GeneratorKoda.global = false;
    }
}
