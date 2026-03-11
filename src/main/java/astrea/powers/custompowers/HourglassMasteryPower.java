package astrea.powers.custompowers;

import astrea.powers.BasePower;
import astrea.util.CustomActions.HourglassMasteryAction;
import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static astrea.AstreaMod.makeID;
import static astrea.util.managers.SoundManager.PlaySound;
import static astrea.util.managers.SoundManager.SANDSOFTIMETRIGGERKEY;

public class HourglassMasteryPower extends BasePower implements CloneablePowerInterface {
    public static final String POWER_ID = makeID(HourglassMasteryPower.class.getSimpleName());

    ///We need this here for loadout mod to make a power button
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    private boolean Locked = false;


    public HourglassMasteryPower(AbstractCreature owner, int amount) {
        this(owner, amount, false);
    }

    public HourglassMasteryPower(AbstractCreature owner, int amount, boolean fromCard){
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);

        this.Locked = fromCard;
    }


    /// This {TEMPLATE} shows how to do a conditional S in powers that need it.
    public void updateDescription() {
        String s =  DESCRIPTIONS[0];
        this.description = s;
    }


    @Override
    public void onAfterUseCard(AbstractCard card, UseCardAction action) {
        super.onAfterUseCard(card, action);
        if(Locked){
            Locked = false;
        }else{
            if(!(card.type == AbstractCard.CardType.POWER) && !card.purgeOnUse && !action.exhaustCard && !action.reboundCard && !action.returnToHand && !card.isInAutoplay){
                flash();
                PlaySound(SANDSOFTIMETRIGGERKEY);
                boolean originalReturnToHand = card.returnToHand;
                card.returnToHand = true;
                addToTop(new ReducePowerAction(owner, owner, this, 1));
                addToTop(new HourglassMasteryAction(card, originalReturnToHand));
            }
        }
    }

    public void onInitialApplication() {
    }

    @Override
    public AbstractPower makeCopy() {
        return new HourglassMasteryPower(owner, amount);
    }
}