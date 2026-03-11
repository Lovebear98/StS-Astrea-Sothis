package astrea.util.CustomActions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;

import static astrea.util.managers.Wiz.FlashCard;

public class HourglassMasteryAction extends AbstractGameAction {
    private final AbstractCard card;
    private final boolean Return;

    public HourglassMasteryAction(AbstractCard card, boolean rth) {
        this.card = card;
        this.Return = rth;
    }

    @Override
    public void update() {
        FlashCard(card);
        card.setCostForTurn(Math.max(0, card.costForTurn - 1));
        card.returnToHand = Return;
        isDone = true;
    }
}
