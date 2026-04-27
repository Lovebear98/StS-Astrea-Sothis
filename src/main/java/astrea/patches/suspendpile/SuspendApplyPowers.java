package astrea.patches.suspendpile;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.cards.CardGroup;

import static astrea.util.managers.MechanicManager.SuspendPile;
import static astrea.util.managers.Wiz.p;

public class SuspendApplyPowers {

    @SpirePatch(
            clz = CardGroup.class,
            method = "applyPowers"
    )
    public static class EndTurn {
        @SpirePostfixPatch
        public static void RollbackTime(CardGroup ___instance) {
            if(___instance == p().hand){
                if(!SuspendPile.isEmpty()){
                    SuspendPile.applyPowers();
                }
            }
        }
    }
}
