package astrea.cardsmods;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static astrea.AstreaMod.makeID;
import static astrea.util.managers.MechanicManager.getSoulHeat;
import static astrea.util.managers.Wiz.isInCombat;
import static astrea.util.managers.Wiz.p;

public class SoulHeatAsDamageMod extends AbstractCardModifier{

    ///Its identifier is done here so we can link straight to it and avoid typing it by hand!
    public static final String CardModID = makeID(SoulHeatAsDamageMod.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(makeID(SoulHeatAsDamageMod.class.getSimpleName()));

    public SoulHeatAsDamageMod(){}
    @Override
    public String identifier(AbstractCard card) {
        return CardModID;
    }
    @Override
    public AbstractCardModifier makeCopy() {
        return new SoulHeatAsDamageMod();
    }

    @Override
    public boolean isInherent(AbstractCard card) {
        return true;
    }

    @Override
    public float modifyDamage(float damage, DamageInfo.DamageType type, AbstractCard card, AbstractMonster target) {
        if(p() != null && isInCombat()){
            return damage + getSoulHeat();
        }
        return super.modifyDamage(damage, type, card, target);
    }
}
