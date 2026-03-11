package astrea.util.CustomActions;

import astrea.powers.custompowers.DistortionHieroglyphPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;

import static astrea.util.managers.Wiz.p;

public class CheckedSoulHeatAction extends AbstractGameAction {
    @Override
    public void update() {
        DistortionHieroglyphPower pow = (DistortionHieroglyphPower) p().getPower(DistortionHieroglyphPower.POWER_ID);
        if(pow != null){
            pow.triggered();
        }
        isDone = true;
    }
}
