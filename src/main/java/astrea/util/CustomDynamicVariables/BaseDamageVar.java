package astrea.util.CustomDynamicVariables;

import basemod.abstracts.DynamicVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;

import static astrea.util.managers.Wiz.BaseDamageKey;


public class BaseDamageVar extends DynamicVariable {
    @Override
    public String key() {
        return BaseDamageKey;
    }

    @Override
    public boolean isModified(AbstractCard abstractCard) {
        return false;
    }

    @Override
    public int value(AbstractCard abstractCard) {
        return abstractCard.baseDamage;
    }

    @Override
    public int baseValue(AbstractCard abstractCard) {
        return abstractCard.baseDamage;
    }

    @Override
    public boolean upgraded(AbstractCard abstractCard) {
        return abstractCard.upgradedDamage;
    }
}
