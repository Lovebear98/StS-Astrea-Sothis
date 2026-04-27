package astrea.util.CustomActions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class DrawRiskyFollowupAction extends AbstractGameAction {

    public DrawRiskyFollowupAction(int num){
        this.duration = 0.001F;
        this.amount = num;
    }

    @Override
    public void update() {
        AbstractDungeon.actionManager.addToTop(new WaitAction(0.4F));
        this.tickDuration();
        if (this.isDone) {
            boolean gainEnergy = false;
            for(AbstractCard c : DrawCardAction.drawnCards) {
                if (c.type == AbstractCard.CardType.ATTACK) {
                    gainEnergy = true;
                    break;
                }
            }
            if(gainEnergy){
                addToBot(new GainEnergyAction(amount));
            }
        }
    }
}

