package astrea.relics.customrelics;

import astrea.cards.generated.Mechanics.Chanting;
import astrea.character.SothisCharacter;
import astrea.relics.AstreaRelic;
import basemod.helpers.CardPowerTip;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;

import static astrea.AstreaMod.makeID;
import static astrea.util.managers.Wiz.p;

public class ResonantAnkh extends AstreaRelic {

    private static final String NAME = ResonantAnkh.class.getSimpleName();
    public static final String ID = makeID(NAME);
    private static final RelicTier RARITY = RelicTier.SPECIAL;
    private static final LandingSound SOUND = LandingSound.SOLID;

    public static final int EffectAmount = 1;

    public ResonantAnkh() {
        super(ID, NAME, SothisCharacter.Meta.CARD_COLOR, RARITY, SOUND);
        this.tips.add(new CardPowerTip(new Chanting()));
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0]+EffectAmount+DESCRIPTIONS[1];
    }

    @Override
    public void atTurnStartPostDraw() {
        super.atTurnStartPostDraw();
            this.flash();
            addToBot(new RelicAboveCreatureAction(p(), this));
             AbstractCard c = new Chanting();
             c.upgrade();
            addToBot(new MakeTempCardInHandAction(c));
    }
}
