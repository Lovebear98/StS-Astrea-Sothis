package astrea.util.CustomActions;

import astrea.powers.custompowers.ChantingPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;

import static astrea.util.managers.Wiz.p;

public class LoseChantingAction extends AbstractGameAction {
    @Override
    public void update() {
        addToTop(new RemoveSpecificPowerAction(p(), p(), ChantingPower.POWER_ID));
        isDone = true;
    }
}
