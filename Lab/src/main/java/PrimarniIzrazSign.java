


public class PrimarniIzrazSign implements Action {

    @Override
    public void checkProduction(SemanticProduction production, Scope scope) {
        //1. znak je ispravan po 4.3.2
        TerminalSymbol sign = (TerminalSymbol) production.getRightStates().get(0);

        if (! Scope.charConstValid(sign.getLexicalUnits()[0]))
            throw new SemanticException(production.toString());

        //tip <- char
        //l-izraz <- 0
        production.getLeftState().addAttribute("type", new SimpleAttribute("char"));
        production.getLeftState().addAttribute("lExpression", new SimpleAttribute("false"));

        int znak = sign.getLexicalUnits()[0].replace("'", "").charAt(0);

        GeneratorKoda.codeBuilder.addCommand("MOVE %D " + znak + ", R0");
        GeneratorKoda.codeBuilder.addCommand("PUSH R0");
    }
}
