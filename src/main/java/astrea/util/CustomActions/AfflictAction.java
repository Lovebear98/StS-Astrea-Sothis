package astrea.util.CustomActions;

import astrea.powers.custompowers.ReliefPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static astrea.util.managers.Wiz.p;

public class AfflictAction extends AbstractGameAction {
    public AfflictAction(int num) {
        this.amount = num;
    }

    @Override
    public void update() {
        AbstractPower pow = p().getPower(ReliefPower.POWER_ID);
        if(pow != null){
            int pownum = pow.amount;
            if(pownum >= amount){
                addToTop(new ReducePowerAction(p(), p(), pow, amount));
                pow.flashWithoutSound();
                isDone = true;
                return;
            }
        }
        addToTop(new DamageAction(p(), new DamageInfo(p(), amount, DamageInfo.DamageType.THORNS), AttackEffect.FIRE));
        isDone = true;
    }
}
