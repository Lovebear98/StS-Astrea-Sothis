package astrea.util.CustomActions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class ModifyCostThisTurnAction extends AbstractGameAction {
    private final AbstractCard Card;

    public ModifyCostThisTurnAction(AbstractCard card, int i) {
        this.Card= card;
        this.amount = i;
    }

    @Override
    public void update() {
        Card.setCostForTurn(amount);
        isDone = true;
    }
}
