package ppj.lab3.utilities.rules.expressions.PostfiksIzraz;

import ppj.lab3.SemanticException;
import ppj.lab3.utilities.SemanticProduction;
import ppj.lab3.utilities.attributes.SimpleAttribute;
import ppj.lab3.utilities.rules.Action;
import ppj.lab3.utilities.rules.RuleFactory;
import ppj.lab3.utilities.scope.Scope;
import ppj.lab3.utilities.symbols.NonTerminalSymbol;

import java.util.Arrays;
import java.util.List;

import static ppj.lab3.utilities.rules.RuleFactory.implicitCast;

public class PostfiksIzrazNiz implements Action {


    @Override
    public void checkProduction(SemanticProduction production, Scope scope) {
        //1. provjeri(<postfiks_izraz>)
        SemanticProduction productionToCheck = new SemanticProduction(production.getRightStateNodes().get(0));
        //pozovi funkciju iz mape
        RuleFactory ruleFactory = RuleFactory.getRuleFactory();
        Action action = (Action) ruleFactory.getRuleMap().get(productionToCheck);
        action.checkProduction(productionToCheck,scope);
        NonTerminalSymbol expression = (NonTerminalSymbol) production.getRightStates().get(0);

        //2. <postfiks_izraz>.tip = niz (X )
        String[] X = {"niz(int)","niz(char)","niz(const(int))","niz(const(char))"};
        List<String> listX = Arrays.asList(X);
        String type = expression.getAttributeMap().get("type").getAttribute().toString();
        if(!listX.contains(type)){
            throw new SemanticException(production.toString());
        }

        //3. provjeri(<izraz>)
        productionToCheck = new SemanticProduction(production.getRightStateNodes().get(2));
        action = (Action) ruleFactory.getRuleMap().get(productionToCheck);
        action.checkProduction(productionToCheck,scope);
        expression = (NonTerminalSymbol) production.getRightStates().get(2);

        //4. <izraz>.tip ∼ int
        if(!implicitCast(expression.getAttributeMap().get("type").getAttribute().toString(),"int")) {
            throw new SemanticException(production.toString());
        }

        //tip ← X
        //l-izraz ← X != const(T)
        String typeToAd = type.substring(4,type.length()-1);
        production.getLeftState().addAttribute("type", new SimpleAttribute(typeToAd));
        if(typeToAd.startsWith("const")) {
            production.getLeftState().addAttribute("lExpression", new SimpleAttribute("false"));
        } else {
            production.getLeftState().addAttribute("lExpression", new SimpleAttribute("true"));
        }
    }
}
