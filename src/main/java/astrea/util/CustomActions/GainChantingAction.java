package astrea.util.CustomActions;

import astrea.powers.custompowers.ChantingPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;

import static astrea.util.managers.Wiz.p;

public class GainChantingAction extends AbstractGameAction {

    public GainChantingAction(){
        this(1);
    }
    public GainChantingAction(int i){
        this.amount = i;
    }

    @Override
    public void update() {
        addToTop(new ApplyPowerAction(p(), p(), new ChantingPower(p(), amount)));
        isDone = true;
    }
}
