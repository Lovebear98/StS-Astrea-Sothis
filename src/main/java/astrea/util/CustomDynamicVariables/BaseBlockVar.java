package astrea.util.CustomDynamicVariables;

import basemod.abstracts.DynamicVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;

import static astrea.util.managers.Wiz.BaseBlockKey;


public class BaseBlockVar extends DynamicVariable {
    @Override
    public String key() {
        return BaseBlockKey;
    }

    @Override
    public boolean isModified(AbstractCard abstractCard) {
        return false;
    }

    @Override
    public int value(AbstractCard abstractCard) {
        return abstractCard.baseBlock;
    }

    @Override
    public int baseValue(AbstractCard abstractCard) {
        return abstractCard.baseBlock;
    }

    @Override
    public boolean upgraded(AbstractCard abstractCard) {
        return abstractCard.upgradedBlock;
    }
}
