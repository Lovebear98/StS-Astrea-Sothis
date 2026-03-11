package astrea.damagemods;

import astrea.powers.custompowers.ReliefPower;
import com.evacipated.cardcrawl.mod.stslib.damagemods.AbstractDamageModifier;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;

import static astrea.AstreaMod.makeID;

public class DamageAsReliefMod extends AbstractDamageModifier {

    public static final String ID = makeID("ReliefDamageMod");
    public final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public DamageAsReliefMod(){
        this(true);
    }
    public DamageAsReliefMod(boolean autoBind){
        this.automaticBindingForCards = autoBind;
    }


    @Override
    public boolean isInherent() {
        return true;
    }
    @Override
    public boolean ignoresBlock(AbstractCreature target) {
        return true;
    }

    @Override
    public int onAttackToChangeDamage(DamageInfo info, int damageAmount, AbstractCreature target) {
        this.addToTop(new ApplyPowerAction(target, info.owner, new ReliefPower(target, damageAmount)));
        return 0;
    }


    @Override
    public AbstractDamageModifier makeCopy() {
        return new DamageAsReliefMod(automaticBindingForCards);
    }
}
