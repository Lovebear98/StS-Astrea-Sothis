package astrea.util.CustomActions.CustomGameEffects;

import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class AddCardModEffect extends AbstractGameEffect {
    private final AbstractCardModifier mod;
    private final AbstractCard c;

    public AddCardModEffect(AbstractCard card, AbstractCardModifier modifier) {
        this.c = card;
        this.mod = modifier;
    }

    @Override
    public void update() {
        CardModifierManager.addModifier(c, mod);
        isDone = true;
    }

    @Override
    public void render(SpriteBatch spriteBatch) {

    }

    @Override
    public void dispose() {

    }
}
