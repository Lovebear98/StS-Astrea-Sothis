package astrea.relics.customrelics;

import astrea.character.SothisCharacter;
import astrea.powers.custompowers.HourglassMasteryPower;
import astrea.relics.AstreaRelic;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;

import static astrea.AstreaMod.makeID;
import static astrea.util.managers.Wiz.p;

/// If you change this relic's name, you MUST change its matchign entry in your RelicStrings
public class BehenianHourglass extends AstreaRelic {

    private static final String NAME = BehenianHourglass.class.getSimpleName();
    public static final String ID = makeID(NAME);
    private static final RelicTier RARITY = RelicTier.STARTER;
    private static final LandingSound SOUND = LandingSound.SOLID;

    public static final int EffectAmount = 1;

    public BehenianHourglass() {
        super(ID, NAME, SothisCharacter.Meta.CARD_COLOR, RARITY, SOUND);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0]+EffectAmount+DESCRIPTIONS[1];
    }


    @Override
    public void onVictory() {
        super.onVictory();
        unusedUp();
    }

    @Override
    public void atBattleStartPreDraw() {
        super.atBattleStartPreDraw();
        unusedUp();
    }

    @Override
    public void atTurnStartPostDraw() {
        super.atTurnStartPostDraw();
        if(GameActionManager.turn == 2 && !usedUp){
            this.usedUp();
            flash();
            addToBot(new RelicAboveCreatureAction(p(), this));
            addToBot(new ApplyPowerAction(p(), p(), new HourglassMasteryPower(p(), EffectAmount)));
        }
    }
}
