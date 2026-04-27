package astrea.patches.suspendpile;

import astrea.ui.SuspendPanel;
import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.screens.ExhaustPileViewScreen;
import javassist.CtBehavior;

import static astrea.AstreaMod.makeID;
import static astrea.util.managers.MechanicManager.SuspendPile;

/// Thanks Collector!

public class ExhaustPileViewScreenPatches {

    public static boolean showSuspendCards = false;
    private static boolean showingSUspendCards = false;
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(makeID(SuspendPanel.class.getSimpleName()));

    @SpirePatch(
            clz = ExhaustPileViewScreen.class,
            method = "open"
    )
    public static class OpenExhaustPileViewScreenPatch {
        @SpireInsertPatch(locator = OpenExhaustPileViewScreenPatchLocator.class)
        public static void Insert(ExhaustPileViewScreen _instance) {
            if (showSuspendCards) {
                CardGroup group = ReflectionHacks.getPrivate(_instance, ExhaustPileViewScreen.class, "exhaustPileCopy");
                group.clear();
                group.group.addAll(SuspendPile.group);
                showSuspendCards = false;
                showingSUspendCards = true;
            } else {
                showingSUspendCards = false;
            }
        }
    }

    private static class OpenExhaustPileViewScreenPatchLocator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctBehavior) throws Exception {
            Matcher matcher = new Matcher.MethodCallMatcher(ExhaustPileViewScreen.class, "hideCards");
            return LineFinder.findInOrder(ctBehavior, matcher);
        }
    }

    @SpirePatch(
            clz = ExhaustPileViewScreen.class,
            method = "render"
    )
    public static class RenderExhaustPileViewScreenPatch {
        @SpireInsertPatch(locator = RenderExhaustPileViewScreenPatchLocator.class)
        public static SpireReturn<Void> Insert(ExhaustPileViewScreen _instance, SpriteBatch sb) {
            if (showingSUspendCards) {
                FontHelper.renderDeckViewTip(sb, uiStrings.EXTRA_TEXT[0], 96.0F * Settings.scale, Settings.CREAM_COLOR);// 311
                return SpireReturn.Return(null);
            } else {
                return SpireReturn.Continue();
            }
        }
    }

    private static class RenderExhaustPileViewScreenPatchLocator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctBehavior) throws Exception {
            Matcher matcher = new Matcher.MethodCallMatcher(FontHelper.class, "renderDeckViewTip");
            return LineFinder.findInOrder(ctBehavior, matcher);
        }
    }
}