package astrea.patches.visual.music;

import astrea.AstreaMod;
import com.badlogic.gdx.audio.Music;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.audio.MainMusic;

import static astrea.util.managers.ConfigManager.EnableMusic;

///Thank you The Drifter, for showing me where to patch!
@SpirePatch(
        clz = MainMusic.class,
        method = "getSong")
public class MusicPatch {

    @SpirePrefixPatch
    public static SpireReturn<Music> Prefix(MainMusic __instance, String key) {
if(EnableMusic){
    AstreaMod.logger.info("Changing Music");
    switch (key) {
        case "Exordium":
            return SpireReturn.Return((MainMusic.newMusic("astrea/audio/bgm/Act1Loop.ogg")));

        case "TheCity":
            return SpireReturn.Return((MainMusic.newMusic("astrea/audio/bgm/Act2Loop.ogg")));

        case "TheBeyond":
            return SpireReturn.Return((MainMusic.newMusic("astrea/audio/bgm/Act3Loop.ogg")));

        case "TheEnding":
            return SpireReturn.Return((MainMusic.newMusic("astrea/audio/bgm/Act4Loop.ogg")));

        case "MENU":
            return SpireReturn.Return((MainMusic.newMusic("astrea/audio/bgm/Menu.ogg")));

        default:
            AstreaMod.logger.info("BGM Dun Goofed. Defaulting: " + key);
            return SpireReturn.Continue();
    }

}else{
    return SpireReturn.Continue();
}

    }
}