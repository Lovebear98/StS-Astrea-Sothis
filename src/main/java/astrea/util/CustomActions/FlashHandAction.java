package astrea.util.CustomActions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;

import static astrea.util.managers.Wiz.FlashCard;
import static astrea.util.managers.Wiz.p;

public class FlashHandAction extends AbstractGameAction {
    @Override
    public void update() {
        if(!p().hand.isEmpty()){
            for(AbstractCard c: p().hand.group){
                FlashCard(c);
            }
        }


        isDone = true;
    }
}
