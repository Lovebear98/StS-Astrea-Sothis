package astrea.util.CustomDynamicVariables;

import astrea.cards.AbstractAstreaCard;
import basemod.abstracts.DynamicVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;

import static astrea.util.managers.Wiz.SpecialVarKey;

public class SpecialVar extends DynamicVariable {
    @Override
    public String key() {
        return SpecialVarKey;
    }

    @Override
    public boolean isModified(AbstractCard abstractCard) {
        if(abstractCard instanceof AbstractAstreaCard){
            return ((AbstractAstreaCard) abstractCard).SpecialVar() != 0;
        }
        return false;
    }

    @Override
    public int value(AbstractCard abstractCard) {
        if(abstractCard instanceof AbstractAstreaCard){
            return ((AbstractAstreaCard) abstractCard).SpecialVar();
        }
        return 0;
    }

    @Override
    public int baseValue(AbstractCard abstractCard) {
        return 0;
    }

    @Override
    public boolean upgraded(AbstractCard abstractCard) {
        return false;
    }
}
