package astrea.powers.custompowers;

import astrea.powers.BasePower;
import astrea.util.CustomActions.CheckedSoulHeatAction;
import astrea.util.CustomActions.ResetSoulHeatAction;
import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static astrea.AstreaMod.makeID;
import static astrea.util.managers.MechanicManager.SoulHeatCap;
import static astrea.util.managers.MechanicManager.getSoulHeat;

public class HourglassMiragePower extends BasePower implements CloneablePowerInterface {
    public static final String POWER_ID = makeID(HourglassMiragePower.class.getSimpleName());

    ///We need this here for loadout mod to make a power button
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;



    public HourglassMiragePower(AbstractCreature owner, int amount) {
        this(owner, amount, false);
    }

    public HourglassMiragePower(AbstractCreature owner, int amount, boolean fromCard){
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
        this.amount2 = amount;
        updateDescription();
    }


    /// This {TEMPLATE} shows how to do a conditional S in powers that need it.
    public void updateDescription() {
        String s = DESCRIPTIONS[0]+amount2+DESCRIPTIONS[1]+amount+DESCRIPTIONS[2];
        this.description = s;
    }

    @Override
    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        this.amount2 += stackAmount;
    }

    @Override
    public void reducePower(int reduceAmount) {
        super.reducePower(reduceAmount);
        this.amount2 -= reduceAmount;
    }

    @Override
    public void atStartOfTurn() {
        super.atStartOfTurn();
        flash();
        this.amount2 = this.amount;
        updateDescription();
    }

    public void triggered(){
        if(this.amount2 > 0){
            this.flash();
            this.amount2 -= 1;
            this.updateDescription();
            addToBot(new ResetSoulHeatAction());
            addToBot(new CheckedSoulHeatAction());
            addToBot(new ApplyPowerAction(owner, owner, new HourglassMasteryPower(owner, 1)));
            addToBot(new AbstractGameAction() {
                @Override
                public void update() {
                    if(getSoulHeat() >= SoulHeatCap()){
                        triggered();
                    }
                    isDone = true;
                }
            });
        }
    }

    @Override
    public void onInitialApplication() {
        super.onInitialApplication();
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                if(getSoulHeat() >= SoulHeatCap()){
                    triggered();
                }
                isDone = true;
            }
        });
    }

    @Override
    public AbstractPower makeCopy() {
        return new HourglassMiragePower(owner, amount);
    }
}