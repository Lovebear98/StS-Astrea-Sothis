package astrea.util.CustomDynamicVariables;

import astrea.cards.AbstractAstreaCard;
import basemod.abstracts.DynamicVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;

import static astrea.util.managers.Wiz.SecondMagicKey;

public class secondMagicVar  extends DynamicVariable {
    @Override
    public String key() {
        return SecondMagicKey;
    }

    @Override
    public boolean isModified(AbstractCard abstractCard) {
        if(abstractCard instanceof AbstractAstreaCard){
            return ((AbstractAstreaCard) abstractCard).isSecondMagicModified;
        }
        return false;
    }

    @Override
    public int value(AbstractCard abstractCard) {
        if(abstractCard instanceof AbstractAstreaCard){
            return ((AbstractAstreaCard) abstractCard).secondMagic;
        }
        return 0;
    }

    @Override
    public int baseValue(AbstractCard abstractCard) {
        if(abstractCard instanceof AbstractAstreaCard){
            return ((AbstractAstreaCard) abstractCard).baseSecondMagic;
        }
        return 0;
    }

    @Override
    public boolean upgraded(AbstractCard abstractCard) {
        if(abstractCard instanceof AbstractAstreaCard){
            return ((AbstractAstreaCard) abstractCard).secondMagicUpgraded;
        }
        return false;
    }
}
