package astrea.relics.customrelics;

import astrea.character.SothisCharacter;
import astrea.relics.AstreaRelic;

import static astrea.AstreaMod.makeID;

public class RegalKhepresh extends AstreaRelic {

    private static final String NAME = RegalKhepresh.class.getSimpleName();
    public static final String ID = makeID(NAME);
    private static final RelicTier RARITY = RelicTier.UNCOMMON;
    private static final LandingSound SOUND = LandingSound.SOLID;

    public static final int EffectAmount = 1;

    public RegalKhepresh() {
        super(ID, NAME, SothisCharacter.Meta.CARD_COLOR, RARITY, SOUND);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }


    @Override
    public void onVictory() {
        super.onVictory();
    }

    @Override
    public void atBattleStartPreDraw() {
        super.atBattleStartPreDraw();
    }
}
