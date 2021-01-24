


public class SpecifikatorTipaVoid implements Action {


    @Override
    public void checkProduction(SemanticProduction production, Scope scope) {
        //tip <- void
        production.getLeftState().addAttribute("type", new SimpleAttribute("void"));
    }
}
