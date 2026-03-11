package astrea.patches.visual.music;

import astrea.AstreaMod;
import com.badlogic.gdx.audio.Music;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.audio.MainMusic;
import com.megacrit.cardcrawl.audio.TempMusic;

import static astrea.util.managers.ConfigManager.EnableMusic;

@SpirePatch(
        clz = TempMusic.class,
        method = "getSong")
public class TempMusicPatch {

    @SpirePrefixPatch
    public static SpireReturn<Music> Prefix(TempMusic __instance, String key) {
if(EnableMusic){
    AstreaMod.logger.info("Changing Music");
    switch (key) {
        case "SHOP":
        case "CREDITS":
            return SpireReturn.Return((MainMusic.newMusic("astrea/audio/bgm/Shop.ogg")));

        case "SHRINE":
            return SpireReturn.Return((MainMusic.newMusic("astrea/audio/bgm/Shrine.ogg")));

        case "MINDBLOOM":
        case "ELITE":
            return SpireReturn.Return((MainMusic.newMusic("astrea/audio/bgm/Elite.ogg")));

        case "BOSS_BOTTOM":
            return SpireReturn.Return((MainMusic.newMusic("astrea/audio/bgm/Act1Boss.ogg")));

        case "BOSS_CITY":
            return SpireReturn.Return((MainMusic.newMusic("astrea/audio/bgm/Act2Boss.ogg")));

        case "BOSS_BEYOND":
            return SpireReturn.Return((MainMusic.newMusic("astrea/audio/bgm/Act3Boss.ogg")));

        case "BOSS_ENDING":
            return SpireReturn.Return((MainMusic.newMusic("astrea/audio/bgm/Act4Boss.ogg")));

        default:
            return SpireReturn.Continue();

    }
    }else{
    return SpireReturn.Continue();

}

}
    }
