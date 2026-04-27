package astrea.cardsmods;

import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import basemod.interfaces.AlternateCardCostModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import java.util.List;

import static astrea.AstreaMod.makeID;

public class ReduceXCostMod extends AbstractCardModifier implements AlternateCardCostModifier {

    ///Its identifier is done here so we can link straight to it and avoid typing it by hand!
    public static final String CardModID = makeID(ReduceXCostMod.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(makeID("CardModText"));
    public int num;

    public ReduceXCostMod(){
        this(1);
    }
    public ReduceXCostMod(int i){
        this.num = 1;
    }
    @Override
    public String identifier(AbstractCard card) {
        return CardModID;
    }
    @Override
    public AbstractCardModifier makeCopy() {
        return new ReduceXCostMod(num);
    }

    @Override
    public boolean isInherent(AbstractCard card) {
        return false;
    }

    @Override
    public void onInitialApplication(AbstractCard card) {
        card.isCostModifiedForTurn = true;
        super.onInitialApplication(card);
    }

    @Override
    public void onRemove(AbstractCard card) {
        card.isCostModifiedForTurn = false;
        super.onRemove(card);
    }

    ///Modifications
    @Override
    public boolean shouldApply(AbstractCard targetCard) {
        ///Noelle casually saving me an embarrassing amount of times
        List<AbstractCardModifier> cardMods = CardModifierManager.getModifiers(targetCard, identifier(null));
        if (!cardMods.isEmpty()) {
            ReduceXCostMod existingMod = ((ReduceXCostMod) cardMods.get(0));
            existingMod.num += this.num;
            return false;
        }
        return true;
    }

    @Override
    public boolean removeOnCardPlayed(AbstractCard card) {
        return true;
    }
    @Override
    public boolean removeAtEndOfTurn(AbstractCard card) {
        return true;
    }

    @Override
    public int getAlternateResource(AbstractCard abstractCard) {
        return EnergyPanel.totalCount + num;
    }

    @Override
    public int spendAlternateCost(AbstractCard abstractCard, int i) {
        EnergyPanel.useEnergy(Math.max(0, i-num));
        return 0;
    }
}
