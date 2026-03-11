package astrea.patches;

import astrea.cards.AbstractAstreaCard;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;

import static astrea.util.CustomTags.ExpandedDescription;

/// This patch just tells our cards with special descriptions to reset it when moving piles
public class ResetStuffPatch {

    @SpirePatch(
            clz = CardGroup.class,
            method = "resetCardBeforeMoving"
    )
    public static class EndTurn {
        @SpirePostfixPatch
        public static void RollbackTime(CardGroup ___instance, AbstractCard c) {
            if(c instanceof AbstractAstreaCard && c.hasTag(ExpandedDescription)){
                ((AbstractAstreaCard) c).resetDescription();
            }
        }
    }
}
