package astrea.util.CustomActions;

import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class AddCardModAction extends AbstractGameAction {
    private final AbstractCard card;
    private final AbstractCardModifier mod;

    public AddCardModAction(AbstractCard c, AbstractCardModifier mod) {
        this.card = c;
        this.mod = mod;

    }

    @Override
    public void update() {
        CardModifierManager.addModifier(card, mod);
        isDone = true;
    }
}
