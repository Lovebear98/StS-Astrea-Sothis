package astrea.util.CustomActions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;

import static astrea.character.SothisCharacter.SothisColor;
import static astrea.util.managers.MechanicManager.SuspendPile;
import static astrea.util.managers.Wiz.FlashCard;
import static astrea.util.managers.Wiz.p;

public class SuspendCardAction extends AbstractGameAction {
    private final AbstractCard card;
    private final CardGroup Pile;

    public SuspendCardAction(AbstractCard abstractCard) {
        this(abstractCard, null);
    }
    public SuspendCardAction(AbstractCard abstractCard, CardGroup group){
        this.card = abstractCard;
        this.Pile = group;
    }

    @Override
    public void update() {
        boolean hasdupe = false;
        for(AbstractCard c: SuspendPile.group){
            if(c.uuid == card.uuid){
                hasdupe = true;
                break;
            }
        }

        if(!hasdupe){
            if(Pile != null){
               addToTop(new AbstractGameAction() {
                   @Override
                   public void update() {
                       if(Pile == p().hand){
                           FlashCard(card, SothisColor.cpy());
                           p().hand.removeCard(card);
                       }
                       if(Pile == p().drawPile){
                           FlashCard(card, SothisColor.cpy());
                           p().drawPile.removeCard(card);
                       }
                       if(Pile == p().discardPile){
                           FlashCard(card, SothisColor.cpy());
                           p().discardPile.removeCard(card);
                       }
                       isDone = true;
                   }
               });
            }
            this.addToBot(new SFXAction("POWER_METALLICIZE"));
            card.targetAngle = 0;
            card.stopGlowing();
            SuspendPile.addToBottom(card);
        }
        isDone = true;
    }
}
