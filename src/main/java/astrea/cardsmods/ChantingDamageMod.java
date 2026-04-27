package astrea.cardsmods;

import astrea.powers.custompowers.ChantingPower;
import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static astrea.AstreaMod.makeID;
import static astrea.util.CustomTags.Chanting;
import static astrea.util.managers.Wiz.isInCombat;
import static astrea.util.managers.Wiz.p;

public class ChantingDamageMod extends AbstractCardModifier{

    ///Its identifier is done here so we can link straight to it and avoid typing it by hand!
    public static final String CardModID = makeID(ChantingDamageMod.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(makeID(ChantingDamageMod.class.getSimpleName()));

    public ChantingDamageMod(){}
    @Override
    public String identifier(AbstractCard card) {
        return CardModID;
    }
    @Override
    public AbstractCardModifier makeCopy() {
        return new ChantingDamageMod();
    }

    @Override
    public boolean isInherent(AbstractCard card) {
        return true;
    }

    @Override
    public float modifyDamage(float damage, DamageInfo.DamageType type, AbstractCard card, AbstractMonster target) {
        if(card.hasTag(Chanting)){
            if(p() != null && isInCombat()){
                AbstractPower pow = p().getPower(ChantingPower.POWER_ID);
                if(pow != null){
                    return damage + pow.amount;
                }
            }
        }
        return super.modifyDamage(damage, type, card, target);
    }
}
