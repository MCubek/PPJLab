


public class IzravniDeklaratorNiz implements Action {


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

        //3. BROJ.vrijednost je pozitivan broj (> 0) ne veci od 1024
        TerminalSymbol number = (TerminalSymbol) production.getRightStates().get(2);
        String numberValue = number.getLexicalUnits()[0];
        if (Integer.parseInt(numberValue) <= 0 || Integer.parseInt(numberValue) > 1024)
            throw new SemanticException(production.toString());

        //4. zabiljezi deklaraciju IDN.ime s odgovarajucim tipom
        String newType = "niz(" + ntype + ")";
        int offsetValue = scope.lastStackOffset() -4*Integer.parseInt(numberValue);
        scope.addScopeElement(new ScopeElement(idnName, newType, true, false, offsetValue));

        //tip <- niz (ntip)
        //br-elem <- BROJ.vrijednost
        production.getLeftState().addAttribute("type", new SimpleAttribute(newType));
        production.getLeftState().addAttribute("numElem", new SimpleAttribute(numberValue));
    }
}
