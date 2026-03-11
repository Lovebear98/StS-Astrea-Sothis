package astrea.patches;

import astrea.AstreaMod;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;

public class GenericEndTurnPatch {

    @SpirePatch(
            clz = AbstractCreature.class,
            method = "applyEndOfTurnTriggers"
    )
    public static class EndTurn {
        @SpirePostfixPatch
        public static void RollbackTime(AbstractCreature ___instance) {
            if(___instance instanceof AbstractPlayer){
                AstreaMod.receiveOnPlayerTurnEnd();
            }
        }
    }
}
