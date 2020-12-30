package ppj.lab3.utilities.rules.expressions.PostfiksIzraz;

import ppj.lab3.SemanticException;
import ppj.lab3.utilities.SemanticProduction;
import ppj.lab3.utilities.attributes.ListAttribute;
import ppj.lab3.utilities.attributes.SimpleAttribute;
import ppj.lab3.utilities.rules.Action;
import ppj.lab3.utilities.rules.RuleFactory;
import ppj.lab3.utilities.scope.Scope;
import ppj.lab3.utilities.symbols.NonTerminalSymbol;

import static ppj.lab3.utilities.rules.RuleFactory.implicitCast;

public class PostfiksIzrazArg implements Action {


    @Override
    public void checkProduction(SemanticProduction production, Scope scope) {
        //1. provjeri(<postfiks_izraz>)
        SemanticProduction productionToCheck = new SemanticProduction(production.getRightStateNodes().get(0));
        //pozovi funkciju iz mape
        RuleFactory ruleFactory= new RuleFactory();
        Action action= (Action) ruleFactory.getRuleMap().get(productionToCheck);
        action.checkProduction(productionToCheck,scope);
        NonTerminalSymbol expression = (NonTerminalSymbol) production.getRightStates().get(0);
        String typePostfix = expression.getAttributeMap().get("type").getAttribute().toString();

        //2. provjeri(<lista_argumenata>)
        productionToCheck = new SemanticProduction(production.getRightStateNodes().get(2));
        action= (Action) ruleFactory.getRuleMap().get(productionToCheck);
        action.checkProduction(productionToCheck,scope);
        expression = (NonTerminalSymbol) production.getRightStates().get(2);

        //3. <postfiks_izraz>.tip = funkcija(params → pov) i redom po elementima
        //arg-tip iz <lista_argumenata>.tipovi i param-tip iz params vrijedi arg-tip
        //∼ param-tip
        if(!typePostfix.contains("funkcija(")) {
            throw new SemanticException(production.toString());
        }

        typePostfix = typePostfix.replace("funkcija(","");
        String trimmedTypePostfix = typePostfix.substring(0, typePostfix.indexOf("-")).trim();
        String[] params = trimmedTypePostfix.split(",");
        ListAttribute argTypesAttribute = (ListAttribute) expression.getAttributeMap().get("types");
        String[] argTypes = (String[]) argTypesAttribute.getAttribute();

        if(params.length == argTypes.length) {
            for(int i = 0; i < params.length; i++) {
                if(!implicitCast(argTypes[i],params[i])) {
                    throw new SemanticException(production.toString());
                }
            }
        } else {
            throw new SemanticException(production.toString());
        }

        //tip ← pov
        //l-izraz ← 0
        String pov = typePostfix.substring(typePostfix.indexOf(">") + 1, typePostfix.length()-1).trim();
        production.getLeftState().addAttribute("type", new SimpleAttribute(pov));
        production.getLeftState().addAttribute("lExpression", new SimpleAttribute("false"));

    }
}
