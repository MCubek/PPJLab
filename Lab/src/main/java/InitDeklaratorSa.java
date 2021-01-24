


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class InitDeklaratorSa implements Action {


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
        String deklaratorType = expression.getAttributeMap().get("type").getAttribute().toString();
        String deklaratorNumElem = "";
        if (deklaratorType.startsWith("niz"))
            deklaratorNumElem = expression.getAttributeMap().get("numElem").getAttribute().toString();

        //2. provjeri(<incijalizator>)
        productionToCheck = new SemanticProduction(production.getRightStateNodes().get(2));
        action = ruleFactory.getRuleMap().get(productionToCheck);
        action.checkProduction(productionToCheck, scope);
        expression = (NonTerminalSymbol) production.getRightStates().get(2);
        String[] acceptableTypes = new String[]{"int", "char", "const(int)", "const(char)"};
        String inicijalizatorType = "";
        if (Arrays.asList(acceptableTypes).contains(deklaratorType))
            inicijalizatorType = expression.getAttributeMap().get("type").getAttribute().toString();
        String inicijalizatorNumElem = "";
        String[] inicijalizatorTypes = new String[]{};
        if (deklaratorType.startsWith("niz")) {
            inicijalizatorNumElem = expression.getAttributeMap().get("numElem").getAttribute().toString();
            inicijalizatorTypes = (String[]) expression.getAttributeMap().get("types").getAttribute();
        }

        //3. ako je <izravni_deklarator>.tip T ili const(T)
        //<inicijalizator>.tip ?= T
        //inace ako je <izravni_deklarator>.tip niz (T) ili niz (const(T))
        //<inicijalizator>.br-elem <= <izravni_deklarator>.br-elem
        //za svaki U iz <inicijalizator>.tipovi vrijedi U ?= T
        //inace greska
        String T;
        if (Arrays.asList(acceptableTypes).contains(deklaratorType)) {
            if (! deklaratorType.startsWith("const"))
                T = deklaratorType;
            else
                T = deklaratorType.substring(6, deklaratorType.length() - 1);
            if (! RuleFactory.implicitCast(inicijalizatorType, T))
                throw new SemanticException(production.toString());
        } else if (deklaratorType.startsWith("niz")) {
            if (deklaratorType.contains("const"))
                T = deklaratorType.substring(10, deklaratorType.length() - 2);
            else
                T = deklaratorType.substring(4, deklaratorType.length() - 1);
            if (! (Integer.parseInt(inicijalizatorNumElem) <= Integer.parseInt(deklaratorNumElem)))
                throw new SemanticException(production.toString());
            for (String s : inicijalizatorTypes)
                if (! RuleFactory.implicitCast(s, T))
                    throw new SemanticException(production.toString());
        } else {
            throw new SemanticException(production.toString());
        }

        if (! deklaratorType.startsWith("niz")) {
            GeneratorKoda.codeBuilder.addCommand("POP R0");

            if (((NonTerminalSymbol) productionToCheck.getRightStateNodes().get(0).getValue()).getAttributeMap().get("lExpression").getAttribute().equals("true")) {
                GeneratorKoda.codeBuilder.addCommand("LOAD R0, (R0)");
            }

        } else {
            GeneratorKoda.codeBuilder.addCommand("ADD R7, %D " +
                    4 * (Integer.parseInt((String) expression.getAttributeMap().get("numElem").getAttribute()) - 1)
                    + ", R0")
            ;
        }

        GeneratorKoda.codeBuilder.addCommand("PUSH R0");
        if (GeneratorKoda.global) {
            String name;

            var nextList = production.getRightStateNodes().get(0).getChildren().stream()
                    .map(Node::getValue)
                    .map(Symbol::getSymbolName)
                    .collect(Collectors.toList());

            if (nextList.contains("IDN") && nextList.size() == 1) {
                var value = 0;
                name = ((TerminalSymbol) production.getRightStateNodes().get(0).getChildren().get(0).getValue()).getLexicalUnits()[0];

                GeneratorKoda.codeBuilder.addCommand("POP R0");
                GeneratorKoda.codeBuilder.addCommand("STORE R0, (" + GeneratorKoda.getGlobalLabel(name) + ")");
                GeneratorKoda.memoryLocations.put(GeneratorKoda.getGlobalLabel(name), value);

            } else if (nextList.equals(Arrays.asList("IDN", "L_UGL_ZAGRADA", "BROJ", "D_UGL_ZAGRADA"))) {

                List<Integer> list = new ArrayList<>();
                name = ((TerminalSymbol) production.getRightStateNodes().get(0).getChildren().get(0).getValue()).getLexicalUnits()[0];

                GeneratorKoda.codeBuilder.addCommand("POP R0");
                GeneratorKoda.codeBuilder.addCommand("MOVE " + GeneratorKoda.getGlobalLabel(name) + ", R1");

                int num = (Integer.parseInt((String) expression.getAttributeMap().get("numElem").getAttribute()));

                GeneratorKoda.codeBuilder.addCommand("ADD R1, %D " + 4 * (num - 1) + ", R1");

                for (int i = 0; i < num; i++) {
                    list.add(0);
                    GeneratorKoda.codeBuilder.addCommand("POP R0");
                    GeneratorKoda.codeBuilder.addCommand("STORE R0, (R1)");
                    GeneratorKoda.codeBuilder.addCommand("SUB R1, 4, R1");
                }
                GeneratorKoda.memoryArrays.put(GeneratorKoda.getGlobalLabel(name), list);
            }

            GeneratorKoda.global = false;
        }
    }
}
