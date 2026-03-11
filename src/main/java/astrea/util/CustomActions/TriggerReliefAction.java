package astrea.util.CustomActions;

import astrea.powers.custompowers.ReliefPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;

public class TriggerReliefAction extends AbstractGameAction {
    private final boolean keepCount;

    public TriggerReliefAction(AbstractCreature target, boolean keepCount) {
        this.target = target;
        this.keepCount = keepCount;
    }

    @Override
    public void update() {
        ReliefPower pow = ((ReliefPower)target.getPower(ReliefPower.POWER_ID));
        if(pow != null){
            pow.triggered(keepCount);
        }
        isDone = true;
    }
}
