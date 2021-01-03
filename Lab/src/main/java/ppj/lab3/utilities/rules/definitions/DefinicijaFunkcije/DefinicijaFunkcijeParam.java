package ppj.lab3.utilities.rules.definitions.DefinicijaFunkcije;

import ppj.lab3.SemanticException;
import ppj.lab3.utilities.SemanticProduction;
import ppj.lab3.utilities.attributes.ListAttribute;
import ppj.lab3.utilities.attributes.SimpleAttribute;
import ppj.lab3.utilities.rules.Action;
import ppj.lab3.utilities.rules.RuleFactory;
import ppj.lab3.utilities.scope.Scope;
import ppj.lab3.utilities.scope.ScopeElement;
import ppj.lab3.utilities.symbols.NonTerminalSymbol;
import ppj.lab3.utilities.symbols.TerminalSymbol;

import java.util.Arrays;

public class DefinicijaFunkcijeParam implements Action {


    @Override
    public void checkProduction(SemanticProduction production, Scope scope) {
        //1. provjeri(<ime_tipa>)
        SemanticProduction productionToCheck = new SemanticProduction(production.getRightStateNodes().get(0));
        RuleFactory ruleFactory= RuleFactory.getRuleFactory();
        Action action= ruleFactory.getRuleMap().get(productionToCheck);
        action.checkProduction(productionToCheck,scope);
        NonTerminalSymbol expression = (NonTerminalSymbol) production.getRightStates().get(0);
        String imeTipa = expression.getAttributeMap().get("type").getAttribute().toString();

        //2. <ime_tipa>.tip != const(T)
        if(imeTipa.startsWith("const"))
            throw new SemanticException(production.toString());

        //3. ne postoji prije definirana funkcija imena IDN.ime
        TerminalSymbol idn = (TerminalSymbol) production.getRightStates().get(1);
        String idnName = idn.getLexicalUnits()[0];
        if(scope.isDefined(idnName))
            throw new SemanticException(production.toString());

        //4. provjeri(<lista_parametara>)
        productionToCheck = new SemanticProduction(production.getRightStateNodes().get(3));
        action= ruleFactory.getRuleMap().get(productionToCheck);
        action.checkProduction(productionToCheck,scope);
        expression = (NonTerminalSymbol) production.getRightStates().get(3);
        String[] paramTypes = (String[]) expression.getAttributeMap().get("types").getAttribute();
        String[] paramNames = (String[]) expression.getAttributeMap().get("names").getAttribute();

        //5. ako postoji deklaracija imena IDN.ime u globalnom djelokrugu onda je pripadni
        //tip te deklaracije funkcija(<lista_parametara>.tipovi -> <ime_tipa>.tip)
        Scope globalScope = scope;
        while(globalScope.getParent() != null)
            globalScope = globalScope.getParent();

        StringBuilder paramTypesString = new StringBuilder();
        for (String string : paramTypes) {
            paramTypesString.append(string);
            if (!Arrays.asList(paramTypes).get(Arrays.asList(paramTypes).size() - 1).equals(string))
                paramTypesString.append(", ");
        }
        String checkType = "funkcija(" + paramTypesString + " -> " + imeTipa + ")";

        ScopeElement idnScope = globalScope.isDeclared(idnName);
        if(idnScope != null) {
            String scopeIdnType = idnScope.getType();
            if (!scopeIdnType.equals(checkType))
                throw new SemanticException(production.toString());
        }

        //6. zabiljezi definiciju i deklaraciju funkcije
        scope.addDefinition(idnName,checkType, false);
        production.getLeftState().addAttribute("type", new SimpleAttribute(checkType));

        //7. provjeri(<slozena_naredba>) uz parametre funkcije koristeci <lista_parametara>.tipovi
        //i <lista_parametara>.imena.
        NonTerminalSymbol slozenaFunkcija = (NonTerminalSymbol) production.getRightStates().get(5);
        slozenaFunkcija.getAttributeMap().put("listaParamNames", new ListAttribute(paramNames));
        slozenaFunkcija.getAttributeMap().put("listaParamTypes", new ListAttribute(paramTypes));
        production.getRightStateNodes().get(5).setValue(slozenaFunkcija);

        productionToCheck = new SemanticProduction(production.getRightStateNodes().get(5));
        action= ruleFactory.getRuleMap().get(productionToCheck);
        action.checkProduction(productionToCheck,scope);
    }
}
