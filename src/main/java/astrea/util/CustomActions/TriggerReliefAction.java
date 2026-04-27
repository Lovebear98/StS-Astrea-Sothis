package astrea.util.CustomActions;

import astrea.powers.custompowers.PrecisionMantraPower;
import astrea.powers.custompowers.ReliefPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;

import static astrea.util.managers.Wiz.p;

public class TriggerReliefAction extends AbstractGameAction {
    private boolean keepCount;

    public TriggerReliefAction(AbstractCreature target, boolean keepCount) {
        this.target = target;
        this.keepCount = keepCount;
    }

    @Override
    public void update() {
        ReliefPower pow = ((ReliefPower)target.getPower(ReliefPower.POWER_ID));
        if(pow != null){
            if(p() != null){
                if(p().hasPower(PrecisionMantraPower.POWER_ID)){
                    keepCount = true;
                }
            }
            pow.triggered(keepCount);
        }
        isDone = true;
    }
}
