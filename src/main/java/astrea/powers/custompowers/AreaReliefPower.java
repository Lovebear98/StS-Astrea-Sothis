package astrea.powers.custompowers;

import astrea.powers.BasePower;
import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.ArtifactPower;

import java.util.Objects;

import static astrea.AstreaMod.makeID;
import static astrea.util.managers.Wiz.p;

public class AreaReliefPower extends BasePower implements CloneablePowerInterface {
    public static final String POWER_ID = makeID(AreaReliefPower.class.getSimpleName());

    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;


    public AreaReliefPower(AbstractCreature owner, int amount){
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }


    public void updateDescription() {
        this.description = DESCRIPTIONS[0]+amount+DESCRIPTIONS[1];
    }

    @Override
    public AbstractPower makeCopy() {
        return new AreaReliefPower(owner, amount);
    }


    @Override
    public void onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        if(Objects.equals(power.ID, ReliefPower.POWER_ID) && target != p() &&!target.hasPower(ArtifactPower.POWER_ID)){
            addToTop(new ApplyPowerAction(p(), p(), new ReliefPower(p(), amount)));
        }
    }
}