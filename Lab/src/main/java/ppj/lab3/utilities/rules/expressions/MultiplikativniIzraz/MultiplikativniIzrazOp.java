package ppj.lab3.utilities.rules.expressions.MultiplikativniIzraz;

import ppj.lab3.SemanticException;
import ppj.lab3.utilities.SemanticProduction;
import ppj.lab3.utilities.attributes.SimpleAttribute;
import ppj.lab3.utilities.rules.Action;
import ppj.lab3.utilities.rules.RuleFactory;
import ppj.lab3.utilities.scope.Scope;
import ppj.lab3.utilities.symbols.NonTerminalSymbol;
import ppj.lab4.GeneratorKoda;

public class MultiplikativniIzrazOp implements Action {


    @Override
    public void checkProduction(SemanticProduction production, Scope scope) {
        //1. provjeri(<multiplikativni_izraz>)
        SemanticProduction productionToCheck = new SemanticProduction(production.getRightStateNodes().get(0));
        RuleFactory ruleFactory = RuleFactory.getRuleFactory();
        Action action = ruleFactory.getRuleMap().get(productionToCheck);
        action.checkProduction(productionToCheck, scope);
        NonTerminalSymbol expression = (NonTerminalSymbol) production.getRightStates().get(0);
        String multiType = expression.getAttributeMap().get("type").getAttribute().toString();

        //2. <multiplikativni_izraz>.tip ?= int
        if (! RuleFactory.implicitCast(multiType, "int")) {
            throw new SemanticException(production.toString());
        }

        //3. provjeri(<cast_izraz>)
        productionToCheck = new SemanticProduction(production.getRightStateNodes().get(2));
        action = ruleFactory.getRuleMap().get(productionToCheck);
        action.checkProduction(productionToCheck, scope);
        expression = (NonTerminalSymbol) production.getRightStates().get(2);
        String castType = expression.getAttributeMap().get("type").getAttribute().toString();

        //4. <cast_izraz>.tip ?= int
        if (! RuleFactory.implicitCast(castType, "int")) {
            throw new SemanticException(production.toString());
        }

        //tip <- int
        //l-izraz <- 0
        production.getLeftState().addAttribute("type", new SimpleAttribute("int"));
        production.getLeftState().addAttribute("lExpression", new SimpleAttribute("false"));

        GeneratorKoda.codeBuilder.addCommand("POP R1");
        if (((NonTerminalSymbol) production.getRightStateNodes().get(0).getValue()).getAttributeMap().get("lExpression").getAttribute().equals("true")) {
            GeneratorKoda.codeBuilder.addCommand("LOAD R1, (R1)");
        }
        GeneratorKoda.codeBuilder.addCommand("POP R0");
        if (((NonTerminalSymbol) production.getRightStateNodes().get(2).getValue()).getAttributeMap().get("lExpression").getAttribute().equals("true")) {
            GeneratorKoda.codeBuilder.addCommand("LOAD R0, (R0)");
        }

        GeneratorKoda.codeBuilder.addCommand("PUSH R1");
        GeneratorKoda.codeBuilder.addCommand("PUSH R0");

        switch (production.getRightStates().get(1).getSymbolName()) {
            case "OP_PUTA":
                GeneratorKoda.codeBuilder.addCommand("CALL " + GeneratorKoda.MUL_LABEL);
                GeneratorKoda.includeFunctions.put(GeneratorKoda.MUL_LABEL, true);
                break;
            case "OP_DIJELI":
                GeneratorKoda.codeBuilder.addCommand("CALL " + GeneratorKoda.DIV_LABEL);
                GeneratorKoda.includeFunctions.put(GeneratorKoda.DIV_LABEL, true);
                break;
            default:
                GeneratorKoda.codeBuilder.addCommand("CALL " + GeneratorKoda.MOD_LABEL);
                GeneratorKoda.includeFunctions.put(GeneratorKoda.MOD_LABEL, true);
        }

        GeneratorKoda.codeBuilder.addCommand("MOVE R6, R0");
        GeneratorKoda.codeBuilder.addCommand("ADD R7, 8, R7");
        GeneratorKoda.codeBuilder.addCommand("PUSH R0");
    }
}
