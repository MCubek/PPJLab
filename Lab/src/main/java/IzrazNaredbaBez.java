


public class IzrazNaredbaBez implements Action {


    @Override
    public void checkProduction(SemanticProduction production, Scope scope) {
        //tip <- int
        production.getLeftState().addAttribute("type", new SimpleAttribute("int"));
    }
}
