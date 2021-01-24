


public class SpecifikatorTipaChar implements Action {


    @Override
    public void checkProduction(SemanticProduction production, Scope scope) {
        //tip <- char
        production.getLeftState().addAttribute("type",new SimpleAttribute("char"));
    }
}
