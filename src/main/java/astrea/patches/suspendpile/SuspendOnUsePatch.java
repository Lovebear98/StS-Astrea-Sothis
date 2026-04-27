package astrea.patches.suspendpile;

import astrea.AstreaMod;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.PurgeField;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static astrea.util.CustomTags.Suspend;
import static astrea.util.managers.MechanicManager.SuspendPile;

public class SuspendOnUsePatch {
    @SpirePatch(
            clz = UseCardAction.class,
            method = "update"
    )
    public static class NopeExhaustTime {
        @SpireInsertPatch(
                rloc = 7,
                localvars = {"targetCard"}
        )
        public static void Insert(UseCardAction __instance, AbstractCard targetCard) {
            AstreaMod.logger.info(targetCard.name);
            if(targetCard.hasTag(Suspend)){
                SuspendPile.addToBottom(targetCard.makeSameInstanceOf());
                AbstractDungeon.actionManager.addToTop(new SFXAction("POWER_METALLICIZE"));
                PurgeField.purge.set(targetCard, true);
            }

        }
    }
}
