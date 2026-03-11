package astrea.powers.custompowers;

import astrea.powers.BasePower;
import astrea.util.CustomActions.CustomGameEffects.vfx.ReliefFireEffect;
import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static astrea.AstreaMod.makeID;
import static astrea.patches.visual.AttackEffectEnum.PURIFY;
import static astrea.util.managers.Wiz.p;

public class ReliefPower extends BasePower implements CloneablePowerInterface {
    public static final String POWER_ID = makeID(ReliefPower.class.getSimpleName());

    ///We need this here for loadout mod to make a power button
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;


    public ReliefPower(AbstractCreature owner, int amount) {
        this(owner, p(), amount);
    }
    public ReliefPower(AbstractCreature owner, AbstractCreature source, int amount){
        super(POWER_ID, getType(owner), TURN_BASED, owner, amount);
        this.source = source;
        updateDescription();
    }

    private static PowerType getType(AbstractCreature ownerToBe) {
        if(ownerToBe instanceof AbstractPlayer){
            return PowerType.BUFF;
        }else{
            return PowerType.DEBUFF;
        }
    }

    public void updateDescription() {
        String s;
        if(this.type == PowerType.BUFF){
            s = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
        }else{
            s = DESCRIPTIONS[2] + amount + DESCRIPTIONS[3];
        }
        this.description = s;
    }


    @Override
    public void atEndOfTurn(boolean isPlayer) {
        super.atEndOfTurn(isPlayer);
        this.flash();

        if(isPlayer){
            triggered(false);
        }else{
            if(!p().hasPower(RegalKhephreshPower.POWER_ID)){
                triggered(false);
            }
        }
    }


    @Override
    public void atStartOfTurn() {
        super.atStartOfTurn();
        if(type == PowerType.DEBUFF && p().hasPower(RegalKhephreshPower.POWER_ID)){
            triggered(false);
        }
    }

    public void triggered(boolean keepCount) {
        if(this.type == PowerType.BUFF){
            addToBot(new GainBlockAction(owner, amount));
        }else{
            addToBot(new DamageAction(owner, new DamageInfo(source, amount, DamageInfo.DamageType.THORNS), PURIFY));
        }
        if(!keepCount){
            addToTop(new ReducePowerAction(owner, owner, this, Math.max(amount/2, 1)));
        }
    }

    @Override
    public void updateParticles() {
        super.updateParticles();
        AbstractDungeon.effectsQueue.add(new ReliefFireEffect(owner.hb.cX + MathUtils.random(-owner.hb.width * 0.1f, owner.hb.width * 0.1f), owner.hb.y+owner.hb.height * 0.15f));
    }

    @Override
    public AbstractPower makeCopy() {
        return new ReliefPower(owner, amount);
    }
}