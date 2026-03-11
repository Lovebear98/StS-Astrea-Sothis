package astrea.cardsmods;

import astrea.patches.interfaces.KindleInterface;
import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;

import static astrea.AstreaMod.makeID;

public class KindleMod extends AbstractCardModifier{

    ///Its identifier is done here so we can link straight to it and avoid typing it by hand!
    public static final String CardModID = makeID(KindleMod.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(makeID(KindleMod.class.getSimpleName()));

    public KindleMod(){}
    @Override
    public String identifier(AbstractCard card) {
        return CardModID;
    }
    @Override
    public AbstractCardModifier makeCopy() {
        return new KindleMod();
    }

    @Override
    public boolean isInherent(AbstractCard card) {
        return true;
    }

    @Override
    public boolean canPlayCard(AbstractCard card) {
        if(card instanceof KindleInterface){
            return ((KindleInterface) card).canKindle();
        }
        return super.canPlayCard(card);
    }
}
