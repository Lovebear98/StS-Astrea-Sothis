package astrea.powers.custompowers;

import astrea.powers.BasePower;
import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static astrea.AstreaMod.makeID;
import static astrea.util.managers.Wiz.p;

public class QuicksandDancePower extends BasePower implements CloneablePowerInterface {
    public static final String POWER_ID = makeID(QuicksandDancePower.class.getSimpleName());

    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public QuicksandDancePower(AbstractCreature owner, int amount){
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }


    @Override
    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0]+amount+DESCRIPTIONS[1];
    }

    @Override
    public void atStartOfTurnPostDraw() {
        super.atStartOfTurnPostDraw();
        addToBot(new ApplyPowerAction(p(), p(), new QuicksandDancePower(p(), amount)));
    }

    @Override
    public AbstractPower makeCopy() {
        return new QuicksandDancePower(owner, amount);
    }
}