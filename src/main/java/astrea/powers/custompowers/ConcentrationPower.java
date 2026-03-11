package astrea.powers.custompowers;

import astrea.powers.BasePower;
import astrea.util.CustomActions.GainSoulHeatAction;
import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static astrea.AstreaMod.makeID;

public class ConcentrationPower extends BasePower implements CloneablePowerInterface {
    public static final String POWER_ID = makeID(ConcentrationPower.class.getSimpleName());

    ///We need this here for loadout mod to make a power button
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;



    public ConcentrationPower(AbstractCreature owner, int amount) {
        this(owner, amount, false);
    }

    public ConcentrationPower(AbstractCreature owner, int amount, boolean fromCard){
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }


    public void updateDescription() {
        this.description = DESCRIPTIONS[0]+amount+DESCRIPTIONS[1];
    }

    @Override
    public void atStartOfTurn() {
        super.atStartOfTurn();
        this.flash();
        addToTop(new GainSoulHeatAction(amount));
    }

    @Override
    public AbstractPower makeCopy() {
        return new ConcentrationPower(owner, amount);
    }
}