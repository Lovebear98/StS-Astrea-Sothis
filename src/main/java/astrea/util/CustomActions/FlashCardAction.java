package astrea.util.CustomActions;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;

import static astrea.util.managers.Wiz.FlashCard;

public class FlashCardAction extends AbstractGameAction {
    private final AbstractCard card;
    private Color Color = null;

    public FlashCardAction(AbstractCard c) {
        this.card = c;
    }
    public FlashCardAction(AbstractCard c, Color col) {
        this.card = c;
        this.Color = col;
    }

    @Override
    public void update() {
        if(Color == null){
            FlashCard(card);
        }else{
            FlashCard(card, Color);
        }
        isDone = true;
    }
}