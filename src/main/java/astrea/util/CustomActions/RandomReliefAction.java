package astrea.util.CustomActions;

import astrea.powers.custompowers.ReliefPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

import static astrea.util.managers.Wiz.allCharacters;
import static astrea.util.managers.Wiz.p;

public class RandomReliefAction extends AbstractGameAction {
    public RandomReliefAction(int magicNumber) {
        this.amount = magicNumber;
    }

    @Override
    public void update() {
        ArrayList<AbstractCreature> targetList = allCharacters();
        int i = AbstractDungeon.cardRng.random(0, targetList.size()-1);
        AbstractCreature c = targetList.get(i);
        addToTop(new ApplyPowerAction(c, p(), new ReliefPower(c, amount)));
        isDone = true;
    }
}
