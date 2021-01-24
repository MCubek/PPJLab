


public class IzravniDeklaratorFuncVoid implements Action {


    @Override
    public void checkProduction(SemanticProduction production, Scope scope) {
        //1. ako je IDN.ime deklarirano u lokalnom djelokrugu, tip prethodne deklaracije
        //je jednak funkcija(void -> ntip)
        String ntype = production.getLeftState().getAttributeMap().get("ntype").getAttribute().toString();
        TerminalSymbol idn = (TerminalSymbol) production.getRightStates().get(0);
        String idnName = idn.getLexicalUnits()[0];
        ScopeElement declaredIdn = scope.isDeclaredLocally(idnName);
        String checkType = "funkcija(void -> " + ntype + ")";
        if(declaredIdn != null) {
            if(!declaredIdn.getName().equals(checkType))
                throw new SemanticException(production.toString());
        } else {
            //2. zabiljezi deklaraciju IDN.ime s odgovarajucim tipom ako ista funkcija vec nije
            //deklarirana u lokalnom djelokrugu
            scope.addScopeElement(new ScopeElement(idnName, checkType, false, false));
        }

        //tip <- funkcija(void -> ntip)
        production.getLeftState().addAttribute("type", new SimpleAttribute(checkType));
    }
}
