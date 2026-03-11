package astrea.util.CustomDynamicVariables;

import basemod.abstracts.DynamicVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;

import static astrea.util.managers.MechanicManager.getSoulHeat;
import static astrea.util.managers.Wiz.SoulHeatKey;


public class SoulHeatVar extends DynamicVariable {
    @Override
    public String key() {
        return SoulHeatKey;
    }

    @Override
    public boolean isModified(AbstractCard abstractCard) {
        return getSoulHeat() != 0;
    }

    @Override
    public int value(AbstractCard abstractCard) {
        return getSoulHeat();
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
