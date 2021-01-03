package ppj.lab3.utilities.rules.definitions.IzravniDeklarator;

import ppj.lab3.SemanticException;
import ppj.lab3.utilities.SemanticProduction;
import ppj.lab3.utilities.attributes.SimpleAttribute;
import ppj.lab3.utilities.rules.Action;
import ppj.lab3.utilities.scope.Scope;
import ppj.lab3.utilities.scope.ScopeElement;
import ppj.lab3.utilities.symbols.TerminalSymbol;

public class IzravniDeklaratorIdn implements Action {


    @Override
    public void checkProduction(SemanticProduction production, Scope scope) {
        //1. ntip != void
         String ntype = production.getLeftState().getAttributeMap().get("ntype").getAttribute().toString();
         if(ntype.equals("void"))
             throw new SemanticException(production.toString());

         //2. IDN.ime nije deklarirano u lokalnom djelokrugu
        TerminalSymbol idn = (TerminalSymbol) production.getRightStates().get(0);
        String idnName = idn.getLexicalUnits()[0];
        if(scope.isDeclaredLocally(idnName) != null)
            throw new SemanticException(production.toString());

        //3. zabiljezi deklaraciju IDN.ime s odgovarajucim tipom
        scope.addScopeElement(new ScopeElement(idnName, ntype, true, false));

        //tip <- ntip
        production.getLeftState().addAttribute("type", new SimpleAttribute(ntype));
    }
}
