package astrea.patches.visual;

import astrea.AstreaMod;
import astrea.util.TextureLoader;
import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.DamageHeartEffect;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

import static astrea.patches.visual.AttackEffectEnum.CORRUPT;
import static astrea.patches.visual.AttackEffectEnum.PURIFY;
import static astrea.util.managers.SoundManager.*;

///Creating his custom "Glitch Screen" attack effect
public class AttackEffectPatch {

    private static final Texture corruptTex = TextureLoader.getTexture("astrea/images/attackeffects/CorruptFire.png");
    private static final Texture pureTex = TextureLoader.getTexture("astrea/images/attackeffects/PureFire.png");


    @SpirePatch(
            clz = FlashAtkImgEffect.class,
            method = "loadImage"
    )
    @SpirePatch(
            clz = DamageHeartEffect.class,
            method = "loadImage"
    )
    public static class loadImagePatch
    {
        @SpirePrefixPatch
        public static SpireReturn<TextureAtlas.AtlasRegion> tryLoadImage(AbstractGameEffect __instance, AbstractGameAction.AttackEffect ___effect)
        {
            /// START of an effect
            try {
                if (___effect == CORRUPT) {
                    ///Set rotation
                    ReflectionHacks.setPrivate(__instance, AbstractGameEffect.class, "rotation", 0);
                    ///Tell it what to show
                    return SpireReturn.Return(new TextureAtlas.AtlasRegion(corruptTex, 0, 0, corruptTex.getWidth(), corruptTex.getHeight()));
                }
            }
            ///Log an error if it's missing
            catch (Exception e)
            {
                AstreaMod.logger.error("Failed to get texture in " + __instance.getClass().getName());
                e.printStackTrace();
            }
            /// END of an Effect

            /// START of an effect
            try {
                if (___effect == PURIFY) {
                    ///Set rotation
                    ReflectionHacks.setPrivate(__instance, AbstractGameEffect.class, "rotation", 0);
                    ///Tell it what to show
                    return SpireReturn.Return(new TextureAtlas.AtlasRegion(pureTex, 0, 0, corruptTex.getWidth(), corruptTex.getHeight()));
                }
            }
            ///Log an error if it's missing
            catch (Exception e)
            {
                AstreaMod.logger.error("Failed to get texture in " + __instance.getClass().getName());
                e.printStackTrace();
            }
            /// END of an Effect

            return SpireReturn.Continue();
        }
        ///
    }

    @SpirePatch(
            clz = FlashAtkImgEffect.class,
            method = "playSound"
    )
    @SpirePatch(
            clz = DamageHeartEffect.class,
            method = "playSound"
    )
    public static class playSoundPatch
    {
        @SpirePrefixPatch
        public static SpireReturn tryPlaySound(AbstractGameEffect __instance, AbstractGameAction.AttackEffect effect)
        {

            if(effect == PURIFY){
                PlaySound(PURIFYKEY);
                return SpireReturn.Return();
            }

            if(effect == CORRUPT){
                PlaySound(CORRUPTKEY);
                return SpireReturn.Return();
            }

            return SpireReturn.Continue();

        }
    }
}