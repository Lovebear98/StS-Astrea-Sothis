package astrea.util.CustomActions.generic;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;

import static astrea.util.managers.Wiz.p;

public class WeakEveryoneAction extends AbstractGameAction {
    public WeakEveryoneAction(AbstractCreature source, int num) {
        this.source = source;
        this.amount = num;
    }

    @Override
    public void update() {
        addToTop(new ApplyPowerAction(p(), source, new WeakPower(p(), amount, false)));
        for(AbstractMonster m: AbstractDungeon.getMonsters().monsters){
            if(!m.isDeadOrEscaped()){
                addToTop(new ApplyPowerAction(m, source, new WeakPower(m, amount, false)));
            }
        }
        isDone = true;
    }
}
