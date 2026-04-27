//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package astrea.util.CustomActions;

import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.PurgeField;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.EmptyDeckShuffleAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.actions.utility.UnlimboAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class FieryProjectionAction extends AbstractGameAction {
    private boolean suspendCards;

    public FieryProjectionAction(AbstractCreature target, boolean suspends) {
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = ActionType.WAIT;
        this.source = AbstractDungeon.player;
        this.target = target;
        this.suspendCards = suspends;
    }// 21

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {// 25
            if (AbstractDungeon.player.drawPile.size() + AbstractDungeon.player.discardPile.size() == 0) {
                this.isDone = true;
                return;
            }

            if (AbstractDungeon.player.drawPile.isEmpty()) {
                this.addToTop(new FieryProjectionAction(this.target, this.suspendCards));
                this.addToTop(new EmptyDeckShuffleAction());
                this.isDone = true;
                return;
            }

            if (!AbstractDungeon.player.drawPile.isEmpty()) {
                AbstractCard card = AbstractDungeon.player.drawPile.getTopCard();
                AbstractDungeon.player.drawPile.group.remove(card);
                AbstractDungeon.getCurrRoom().souls.remove(card);
                /// This check prevents single-use cards from being cloned via suspension
                if(card.exhaustOnUseOnce || card.exhaust || PurgeField.purge.get(card) || card.purgeOnUse || card.type == AbstractCard.CardType.POWER){
                    suspendCards = false;
                }

                if(suspendCards){
                    PurgeField.purge.set(card, true);
                }

                AbstractDungeon.player.limbo.group.add(card);
                card.current_y = -200.0F * Settings.scale;
                card.target_x = (float)Settings.WIDTH / 2.0F + 200.0F * Settings.xScale;
                card.target_y = (float)Settings.HEIGHT / 2.0F;
                card.targetAngle = 0.0F;
                card.lighten(false);
                card.drawScale = 0.12F;
                card.targetDrawScale = 0.75F;
                card.applyPowers();// 52
                this.addToTop(new NewQueueCardAction(card, this.target, false, true));
                this.addToTop(new UnlimboAction(card));
                if(suspendCards){
                    this.addToTop(new SuspendCardAction(card.makeSameInstanceOf()));
                }
                if (!Settings.FAST_MODE) {
                    this.addToTop(new WaitAction(Settings.ACTION_DUR_MED));
                } else {
                    this.addToTop(new WaitAction(Settings.ACTION_DUR_FASTER));
                }
            }

            this.isDone = true;// 61
        }

    }// 63
}
