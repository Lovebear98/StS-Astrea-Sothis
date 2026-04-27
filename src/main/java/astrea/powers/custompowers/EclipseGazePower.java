package astrea.powers.custompowers;

import astrea.powers.BasePower;
import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.RagePower;

import static astrea.AstreaMod.makeID;
import static astrea.util.managers.Wiz.p;

public class EclipseGazePower extends BasePower implements CloneablePowerInterface {
    public static final String POWER_ID = makeID(EclipseGazePower.class.getSimpleName());

    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;


    public EclipseGazePower(AbstractCreature owner, int amount){
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }


    public void updateDescription() {
        this.description = DESCRIPTIONS[0]+amount+DESCRIPTIONS[1];
    }

    public void onUseCard(AbstractCard card, UseCardAction action) {
        if(card.costForTurn >= 2 || card.costForTurn == -1 && card.energyOnUse >= 2){

            addToBot(new ApplyPowerAction(p(), p(), new ReliefPower(p(), amount)));
            for(AbstractMonster m2: AbstractDungeon.getMonsters().monsters){
                if(!m2.isDeadOrEscaped()){
                    addToBot(new ApplyPowerAction(m2, p(), new ReliefPower(m2, p(), amount)));
                }
            }

            this.flash();
        }
    }

    @Override
    public AbstractPower makeCopy() {
        return new EclipseGazePower(owner, amount);
    }


}