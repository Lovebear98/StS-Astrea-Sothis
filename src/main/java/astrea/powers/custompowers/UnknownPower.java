package astrea.powers.custompowers;

import astrea.powers.BasePower;
import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static astrea.AstreaMod.makeID;

public class UnknownPower extends BasePower implements CloneablePowerInterface {
    public static final String POWER_ID = makeID(UnknownPower.class.getSimpleName());

    ///We need this here for loadout mod to make a power button
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;



    public UnknownPower(AbstractCreature owner) {
        this(owner, -1);
    }

    public UnknownPower(AbstractCreature owner, int amount){
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }


    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }


    @Override
    public AbstractPower makeCopy() {
        return new UnknownPower(owner, amount);
    }
}