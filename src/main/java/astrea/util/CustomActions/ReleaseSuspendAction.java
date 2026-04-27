package astrea.util.CustomActions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static astrea.util.managers.MechanicManager.SuspendPile;

public class ReleaseSuspendAction extends AbstractGameAction {
    @Override
    public void update() {
        if(!SuspendPile.isEmpty()){
            addToBot(new SFXAction("POWER_METALLICIZE"));

            for(AbstractCard c: SuspendPile.group){
                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(c.makeSameInstanceOf(), 1));
            }
            SuspendPile.clear();
        }
        isDone = true;
    }
}
