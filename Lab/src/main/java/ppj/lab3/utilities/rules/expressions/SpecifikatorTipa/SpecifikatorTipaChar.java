package ppj.lab3.utilities.rules.expressions.SpecifikatorTipa;

import ppj.lab3.utilities.SemanticProduction;
import ppj.lab3.utilities.attributes.SimpleAttribute;
import ppj.lab3.utilities.rules.Action;
import ppj.lab3.utilities.scope.Scope;

public class SpecifikatorTipaChar implements Action {


    @Override
    public void checkProduction(SemanticProduction production, Scope scope) {
        //tip <- char
        production.getLeftState().addAttribute("type",new SimpleAttribute("char"));
    }
}
