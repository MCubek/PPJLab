


import java.util.Arrays;

public class InicijalizatorJedan implements Action {


    @Override
    public void checkProduction(SemanticProduction production, Scope scope) {
        //1. provjeri(<izraz_pridruzivanja>)
        SemanticProduction productionToCheck = new SemanticProduction(production.getRightStateNodes().get(0));
        RuleFactory ruleFactory = RuleFactory.getRuleFactory();
        Action action = ruleFactory.getRuleMap().get(productionToCheck);
        action.checkProduction(productionToCheck, scope);
        NonTerminalSymbol expression = (NonTerminalSymbol) production.getRightStates().get(0);
        String type = expression.getAttributeMap().get("type").getAttribute().toString();

        //ako je <izraz_pridruzivanja> *=> NIZ_ZNAKOVA
        //br-elem <- duljina niza znakova + 1
        //tipovi <- lista duljine br-elem, svi elementi su char
        //inace
        //tip <- <izraz_pridruzivanja>.tip
        int nizZnakovaLength = production.getRightStateNodes().get(0).canGenerateNizZnakova();
        if (nizZnakovaLength != - 1) {
            production.getLeftState().addAttribute("numElem", new SimpleAttribute(String.valueOf(nizZnakovaLength + 1)));
            String[] types = new String[nizZnakovaLength + 1];
            Arrays.fill(types, "char");
            production.getLeftState().addAttribute("types", new ListAttribute(types));
        } else {
            production.getLeftState().addAttribute("type", new SimpleAttribute(type));
        }
    }
}
