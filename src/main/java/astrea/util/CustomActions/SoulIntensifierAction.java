package astrea.util.CustomActions;

import astrea.powers.custompowers.ReliefPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static astrea.util.managers.Wiz.p;

public class SoulIntensifierAction extends AbstractGameAction {

    public SoulIntensifierAction(AbstractCreature c) {
        this.target = c;
        this.source = p();
    }

    @Override
    public void update() {
        AbstractPower p = target.getPower(ReliefPower.POWER_ID);
        if(p != null){
            addToTop(new ApplyPowerAction(target, source, new ReliefPower(target, p.amount)));
        }
        isDone = true;
    }
}
