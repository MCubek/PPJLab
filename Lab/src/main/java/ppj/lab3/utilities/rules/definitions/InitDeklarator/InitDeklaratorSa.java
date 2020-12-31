package ppj.lab3.utilities.rules.definitions.InitDeklarator;

import ppj.lab3.SemanticException;
import ppj.lab3.utilities.SemanticProduction;
import ppj.lab3.utilities.attributes.SimpleAttribute;
import ppj.lab3.utilities.rules.Action;
import ppj.lab3.utilities.rules.RuleFactory;
import ppj.lab3.utilities.scope.Scope;
import ppj.lab3.utilities.symbols.NonTerminalSymbol;

import java.util.Arrays;

import static ppj.lab3.utilities.rules.RuleFactory.implicitCast;

public class InitDeklaratorSa implements Action {


    @Override
    public void checkProduction(SemanticProduction production, Scope scope) {
        //1. provjeri(<izravni_deklarator>) uz nasljedno svojstvo
        //<izravni_deklarator>.ntip ← <init_deklarator>.ntip
        String listaNTip = production.getLeftState().getAttributeMap().get("ntype").getAttribute().toString();
        NonTerminalSymbol izravniDeklarator = (NonTerminalSymbol) production.getRightStates().get(0);
        izravniDeklarator.addAttribute("ntype", new SimpleAttribute(listaNTip));
        production.getRightStateNodes().get(0).setValue(izravniDeklarator);

        SemanticProduction productionToCheck = new SemanticProduction(production.getRightStateNodes().get(0));
        RuleFactory ruleFactory= RuleFactory.getRuleFactory();
        Action action= ruleFactory.getRuleMap().get(productionToCheck);
        action.checkProduction(productionToCheck,scope);
        NonTerminalSymbol expression = (NonTerminalSymbol) production.getRightStates().get(0);
        String deklaratorType = expression.getAttributeMap().get("type").getAttribute().toString();
        String deklaratorNumElem = expression.getAttributeMap().get("numElem").getAttribute().toString();

        //2. provjeri(<incijalizator>)
        productionToCheck = new SemanticProduction(production.getRightStateNodes().get(2));
        action= ruleFactory.getRuleMap().get(productionToCheck);
        action.checkProduction(productionToCheck,scope);
        expression = (NonTerminalSymbol) production.getRightStates().get(2);
        String inicijalizatorType = expression.getAttributeMap().get("type").getAttribute().toString();
        String inicijalizatorNumElem = expression.getAttributeMap().get("numElem").getAttribute().toString();
        String[] inicijalizatorTypes = (String[]) expression.getAttributeMap().get("types").getAttribute();

        //3. ako je <izravni_deklarator>.tip T ili const(T)
        //<inicijalizator>.tip ∼ T
        //inace ako je <izravni_deklarator>.tip niz (T) ili niz (const(T))
        //<inicijalizator>.br-elem ≤ <izravni_deklarator>.br-elem
        //za svaki U iz <inicijalizator>.tipovi vrijedi U ∼ T
        //inace greska
        String[] acceptableTypes = new String[] {"int","char","const(int)","const(char)"};
        if(Arrays.asList(acceptableTypes).contains(deklaratorType)) {
            if(!implicitCast(inicijalizatorType,"int") || !implicitCast(inicijalizatorType,"char"))
                throw new SemanticException(production.toString());
        } else if(deklaratorType.startsWith("niz")){
            if(!(Integer.parseInt(inicijalizatorNumElem) <= Integer.parseInt(deklaratorNumElem)))
                throw new SemanticException(production.toString());
            for(String s : inicijalizatorTypes)
                if(!implicitCast(s,"int") || !implicitCast(s,"char"))
                    throw new SemanticException(production.toString());
        } else {
            throw new SemanticException(production.toString());
        }
    }
}
