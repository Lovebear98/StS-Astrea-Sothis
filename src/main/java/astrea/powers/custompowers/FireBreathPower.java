package astrea.powers.custompowers;

import astrea.powers.BasePower;
import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static astrea.AstreaMod.makeID;
import static astrea.util.managers.MechanicManager.getSoulHeat;

public class FireBreathPower extends BasePower implements CloneablePowerInterface {
    public static final String POWER_ID = makeID(FireBreathPower.class.getSimpleName());

    ///We need this here for loadout mod to make a power button
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;



    public FireBreathPower(AbstractCreature owner, int amount) {
        this(owner, amount, false);
    }

    public FireBreathPower(AbstractCreature owner, int amount, boolean fromCard){
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }


    public void updateDescription() {
        this.description = DESCRIPTIONS[0]+amount+DESCRIPTIONS[1];
    }

    @Override
    public float atDamageGive(float damage, DamageInfo.DamageType type, AbstractCard card) {
        if(getSoulHeat() >= 10){
            return damage + amount;
        }
        return super.atDamageGive(damage, type, card);
    }

    @Override
    public AbstractPower makeCopy() {
        return new FireBreathPower(owner, amount);
    }
}