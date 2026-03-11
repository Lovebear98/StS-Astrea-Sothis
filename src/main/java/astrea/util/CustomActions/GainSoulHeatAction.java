package astrea.util.CustomActions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;

import static astrea.util.managers.MechanicManager.gainSoulHeat;

public class GainSoulHeatAction extends AbstractGameAction {

    public GainSoulHeatAction(){
        this(1);
    }

    public GainSoulHeatAction(int i){
        this.amount = i;
    }

    @Override
    public void update() {
        gainSoulHeat(amount);
        isDone = true;
    }
}
