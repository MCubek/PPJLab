package ppj.lab3.utilities.rules.commands.NaredbaGrananja;

import ppj.lab3.SemanticException;
import ppj.lab3.utilities.SemanticProduction;
import ppj.lab3.utilities.rules.Action;
import ppj.lab3.utilities.rules.RuleFactory;
import ppj.lab3.utilities.scope.Scope;
import ppj.lab3.utilities.symbols.NonTerminalSymbol;
import ppj.lab4.GeneratorKoda;

public class NaredbaGrananjaIf implements Action {


    @Override
    public void checkProduction(SemanticProduction production, Scope scope) {
        //1. provjeri(<izraz>)
        SemanticProduction productionToCheck = new SemanticProduction(production.getRightStateNodes().get(2));
        RuleFactory ruleFactory = RuleFactory.getRuleFactory();
        Action action = ruleFactory.getRuleMap().get(productionToCheck);
        action.checkProduction(productionToCheck, scope);
        NonTerminalSymbol symbol = (NonTerminalSymbol) production.getRightStates().get(2);
        String type = symbol.getAttributeMap().get("type").getAttribute().toString();

        //2. <izraz>.tip ?= int
        if (! RuleFactory.implicitCast(type, "int")) {
            throw new SemanticException(production.toString());
        }

        String jumpLabel = GeneratorKoda.calculateNextLabel();

        GeneratorKoda.codeBuilder.addCommand("POP R0");
        GeneratorKoda.codeBuilder.addCommand("CMP R0, 0");
        GeneratorKoda.codeBuilder.addCommand("JP_EQ " + jumpLabel);

        //3. provjeri(<naredba>)
        productionToCheck = new SemanticProduction(production.getRightStateNodes().get(4));
        action = ruleFactory.getRuleMap().get(productionToCheck);
        action.checkProduction(productionToCheck, scope);

        GeneratorKoda.codeBuilder.addCommandWithLabel(jumpLabel, "");
    }
}
