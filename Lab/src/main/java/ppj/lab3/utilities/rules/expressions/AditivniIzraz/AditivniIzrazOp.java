package ppj.lab3.utilities.rules.expressions.AditivniIzraz;

import ppj.lab3.SemanticException;
import ppj.lab3.utilities.SemanticProduction;
import ppj.lab3.utilities.attributes.SimpleAttribute;
import ppj.lab3.utilities.rules.Action;
import ppj.lab3.utilities.rules.RuleFactory;
import ppj.lab3.utilities.scope.Scope;
import ppj.lab3.utilities.symbols.NonTerminalSymbol;
import ppj.lab4.GeneratorKoda;

public class AditivniIzrazOp implements Action {


    @Override
    public void checkProduction(SemanticProduction production, Scope scope) {
        //1. provjeri(<aditivni_izraz>)
        SemanticProduction productionToCheck = new SemanticProduction(production.getRightStateNodes().get(0));
        RuleFactory ruleFactory = RuleFactory.getRuleFactory();
        Action action = ruleFactory.getRuleMap().get(productionToCheck);
        action.checkProduction(productionToCheck, scope);
        NonTerminalSymbol expression = (NonTerminalSymbol) production.getRightStates().get(0);
        String multiType = expression.getAttributeMap().get("type").getAttribute().toString();

        //2. <aditivni_izraz>.tip ?= int
        if (!RuleFactory.implicitCast(multiType, "int")) {
            throw new SemanticException(production.toString());
        }

        //3. provjeri(<multiplikativni_izraz>)
        productionToCheck = new SemanticProduction(production.getRightStateNodes().get(2));
        action = ruleFactory.getRuleMap().get(productionToCheck);
        action.checkProduction(productionToCheck, scope);
        expression = (NonTerminalSymbol) production.getRightStates().get(2);
        String castType = expression.getAttributeMap().get("type").getAttribute().toString();

        //4. <multiplikativni_izraz>.tip ?= int
        if (! RuleFactory.implicitCast(castType, "int")) {
            throw new SemanticException(production.toString());
        }

        //tip <- int
        //l-izraz <- 0
        production.getLeftState().addAttribute("type", new SimpleAttribute("int"));
        production.getLeftState().addAttribute("lExpression", new SimpleAttribute("false"));

        GeneratorKoda.codeBuilder.addCommand("POP R0");
        if (((NonTerminalSymbol) production.getRightStates().get(2)).getAttributeMap().get("lExpression").getAttribute().equals("true"))
            GeneratorKoda.codeBuilder.addCommand("LOAD R0, (R0)");

        GeneratorKoda.codeBuilder.addCommand("POP R1");
        if (((NonTerminalSymbol) production.getRightStates().get(0)).getAttributeMap().get("lExpression").getAttribute().equals("true"))
            GeneratorKoda.codeBuilder.addCommand("LOAD R1, (R1)");

        String operator = production.getRightStates().get(1).getSymbolName();

        if (operator.equals("PLUS")) {
            GeneratorKoda.codeBuilder.addCommand("ADD R0, R1, R0");
        } else {
            GeneratorKoda.codeBuilder.addCommand("SUB R1, R0, R0");
        }
        GeneratorKoda.codeBuilder.addCommand("PUSH R0");
    }
}
