package astrea.patches;

import astrea.AstreaMod;
import astrea.cards.AbstractAstreaCard;
import astrea.cardsmods.ReduceXCostMod;
import astrea.powers.custompowers.AscendPower;
import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import java.util.List;

import static astrea.util.managers.Wiz.isInCombat;
import static astrea.util.managers.Wiz.p;

public class AscendPatch {

    @SpirePatch(
            clz = AbstractCard.class,
            method = "freeToPlay"
    )
    public static class EndTurn {
        @SpirePrefixPatch
        public static SpireReturn RollbackTime(AbstractCard ___instance) {
            if(isInCombat() && p() != null) {
                if(p().hasPower(AscendPower.POWER_ID) && p().hand.contains(___instance)){
                    if(___instance.costForTurn >= 2 || (___instance.cost == -1 && ___instance.energyOnUse >= 2)){
                        return SpireReturn.Return(true);
                    }
                }
            }
            return SpireReturn.Continue();
        }
    }


    @SpirePatch(
            clz = AbstractCard.class,
            method = "triggerOnGlowCheck"
    )
    public static class Glowie {
        @SpirePostfixPatch
        public static SpireReturn RollbackTime(AbstractCard ___instance) {
            if(isInCombat() && p() != null) {
                if(p().hasPower(AscendPower.POWER_ID) && p().hand.contains(___instance)){
                    ///For X Costs, start with our current energy
                    int energy = EnergyPanel.totalCount;
                    /// If it exists, add in our X-Cost discount from Hourglass Mastery, etc.
                    List<AbstractCardModifier> cardMods = CardModifierManager.getModifiers(___instance, ReduceXCostMod.CardModID);
                    if (!cardMods.isEmpty()) {
                        ReduceXCostMod existingMod = ((ReduceXCostMod) cardMods.get(0));
                        energy += existingMod.num;
                    }

                    if(___instance.costForTurn >= 2 || (___instance.cost == -1 && energy >= 2)){
                        ___instance.glowColor = AbstractAstreaCard.exposeGlowCOlor();
                        return SpireReturn.Return();
                    }
                }
            }
            return SpireReturn.Continue();
        }
    }
}
