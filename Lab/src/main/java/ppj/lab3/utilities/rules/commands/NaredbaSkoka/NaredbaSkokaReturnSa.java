package ppj.lab3.utilities.rules.commands.NaredbaSkoka;

import ppj.lab3.SemanticException;
import ppj.lab3.utilities.SemanticProduction;
import ppj.lab3.utilities.rules.Action;
import ppj.lab3.utilities.rules.RuleFactory;
import ppj.lab3.utilities.scope.Scope;
import ppj.lab3.utilities.symbols.NonTerminalSymbol;
import ppj.lab4.CodeBuilder;
import ppj.lab4.GeneratorKoda;

public class NaredbaSkokaReturnSa implements Action {
    private final CodeBuilder builder = GeneratorKoda.codeBuilder;

    @Override
    public void checkProduction(SemanticProduction production, Scope scope) {
        //1. provjeri(<izraz>)
        SemanticProduction productionToCheck = new SemanticProduction(production.getRightStateNodes().get(1));
        RuleFactory ruleFactory = RuleFactory.getRuleFactory();
        Action action = ruleFactory.getRuleMap().get(productionToCheck);
        action.checkProduction(productionToCheck, scope);
        NonTerminalSymbol expression = (NonTerminalSymbol) production.getRightStates().get(1);
        String izrazType = expression.getAttributeMap().get("type").getAttribute().toString();

        //2. naredba se nalazi unutar funkcije tipa funkcija(params -> pov) i vrijedi
        //<izraz>.tip ?= pov

        String commandType = production.getLeftStateNode().returnFunctionType();
        if (commandType == null || ! commandType.contains("funkcija"))
            throw new SemanticException(production.toString());
        String trimmedType = commandType.substring(commandType.indexOf(">") + 1, commandType.length() - 1).trim();
        if (! RuleFactory.implicitCast(izrazType, trimmedType))
            throw new SemanticException(production.toString());

        builder.addCommand("POP R0");
        builder.addCommand("MOVE R6, R7");
        builder.addCommand("MOVE R0, R6");
        if (((NonTerminalSymbol) production.getRightStates().get(1)).getAttributeMap().get("lExpression").getAttribute().equals("true"))
            builder.addCommand("LOAD R6, (R6)");
        builder.addCommand("RET");
    }
}
