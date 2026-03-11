package astrea.cardsmods;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;

import static astrea.AstreaMod.makeID;
import static astrea.util.managers.MechanicManager.getSoulHeat;
import static astrea.util.managers.Wiz.isInCombat;
import static astrea.util.managers.Wiz.p;

public class SoulHeatAsBlockMod extends AbstractCardModifier{

    ///Its identifier is done here so we can link straight to it and avoid typing it by hand!
    public static final String CardModID = makeID(SoulHeatAsBlockMod.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(makeID(SoulHeatAsBlockMod.class.getSimpleName()));

    public SoulHeatAsBlockMod(){}
    @Override
    public String identifier(AbstractCard card) {
        return CardModID;
    }
    @Override
    public AbstractCardModifier makeCopy() {
        return new SoulHeatAsBlockMod();
    }

    @Override
    public boolean isInherent(AbstractCard card) {
        return true;
    }

    @Override
    public float modifyBlock(float block, AbstractCard card) {
        if(p() != null && isInCombat()){
            return block + getSoulHeat();
        }
        return super.modifyBlock(block, card);
    }
}
