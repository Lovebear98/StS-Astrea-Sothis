package astrea.powers.custompowers;

import astrea.powers.BasePower;
import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static astrea.AstreaMod.makeID;

public class DistortionHieroglyphPower extends BasePower implements CloneablePowerInterface {
    public static final String POWER_ID = makeID(DistortionHieroglyphPower.class.getSimpleName());

    ///We need this here for loadout mod to make a power button
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;



    public DistortionHieroglyphPower(AbstractCreature owner, int amount) {
        this(owner, amount, false);
    }

    public DistortionHieroglyphPower(AbstractCreature owner, int amount, boolean fromCard){
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }


    /// This {TEMPLATE} shows how to do a conditional S in powers that need it.
    public void updateDescription() {
        String s = DESCRIPTIONS[0]+amount+DESCRIPTIONS[1];
                if(amount != 1){
                    s += DESCRIPTIONS[2];
                }
                s += DESCRIPTIONS[3];
        this.description = s;
    }


    @Override
    public void atStartOfTurn() {
        super.atStartOfTurn();
        addToTop(new RemoveSpecificPowerAction(owner, owner, this));
    }

    public void triggered(){
        this.flashWithoutSound();
        addToTop(new ReducePowerAction(owner, owner, this, 1));
    }


    @Override
    public AbstractPower makeCopy() {
        return new DistortionHieroglyphPower(owner, amount);
    }
}