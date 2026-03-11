package astrea.util.CustomActions;

import astrea.powers.custompowers.InnerWarmthPower;
import astrea.util.managers.MechanicManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;

import static astrea.util.managers.Wiz.p;

public class ResetSoulHeatAction extends AbstractGameAction {
    @Override
    public void update() {
        InnerWarmthPower pow = (InnerWarmthPower) p().getPower(InnerWarmthPower.POWER_ID);
        if(pow != null){
            pow.triggered();
        }else{
            MechanicManager.resetSoulHeat();
        }
        isDone = true;
    }
}
