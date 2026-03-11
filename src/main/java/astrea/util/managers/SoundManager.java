package astrea.util.managers;

import basemod.BaseMod;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.CardCrawlGame;

import static astrea.AstreaMod.makeID;
import static astrea.util.managers.ConfigManager.EnableEarCandy;

public class SoundManager {

    public static final String PURIFYKEY = makeID("PURIFYKEY");
    public static final String CORRUPTKEY = makeID("CORRUPTKEY");

    public static final String RELIEFKEY = makeID("RELIEFKEY");

    public static final String SANDSOFTIMETRIGGERKEY = makeID("SANDSOFTIMETRIGGERKEY");
    public static final String SANDSOFTIMEKEY = makeID("SANDSOFTIMEKEY");

    public static final String SOTHISATTACK1KEY = makeID("SOTHISATTACK1KEY");
    public static final String SOTHISATTACK2KEY = makeID("SOTHISATTACK2KEY");
    public static final String SOTHISATTACK3KEY = makeID("SOTHISATTACK3KEY");
    public static final String SOTHISATTACK4KEY = makeID("SOTHISATTACK4KEY");
    public static final String SOTHISATTACK5KEY = makeID("SOTHISATTACK5KEY");

    public static final String SOTHISDIEKEY = makeID("SOTHISDIEKEY");

    public static final String SOTHISHIT1KEY = makeID("SOTHISHIT1KEY");
    public static final String SOTHISHIT2KEY = makeID("SOTHISHIT2KEY");
    public static final String SOTHISHIT3KEY = makeID("SOTHISHIT3KEY");

    public static final String SOTHISTALK1KEY = makeID("SOTHISTALK1KEY");

    public static final String SOULHEATKEY = makeID("SOULHEATKEY");
    public static final String SOULHEATCAPKEY = makeID("SOULHEATCAPKEY");
    public static final String HOVERHEATUIKEY = makeID("HOVERHEATUIKEY");


    /// Forces a sound to play even if custom sounds are disabled
    public static void ForcePlaySound(String key, Float pitch){
        CardCrawlGame.sound.play(key, pitch);
    }

    /// Methods to play our custom sounds by their sound key
    public static void PlaySound(String key, Float pitch){
        if(EnableEarCandy){
            CardCrawlGame.sound.play(key, pitch);
        }
    }
    public static void PlaySound(String key, Float pitchmin, Float pitchmax){
        PlaySound(key, MathUtils.random(pitchmin, pitchmax));
    }
    public static void PlaySound(String key){
        CardCrawlGame.sound.play(key);
    }


    /// .ogg files are the best option if possible
    public static void AddSounds() {
        BaseMod.addAudio(PURIFYKEY, "astrea/audio/sfx/Purify.ogg");
        BaseMod.addAudio(CORRUPTKEY, "astrea/audio/sfx/Corruption1.ogg");

        BaseMod.addAudio(RELIEFKEY, "astrea/audio/sfx/Relief.ogg");

        BaseMod.addAudio(SANDSOFTIMETRIGGERKEY, "astrea/audio/sfx/SandsOfTimeTrigger.ogg");
        BaseMod.addAudio(SANDSOFTIMEKEY, "astrea/audio/sfx/SandsOfTime.ogg");

        BaseMod.addAudio(SOTHISATTACK1KEY, "astrea/audio/sfx/SothisAttack1.ogg");
        BaseMod.addAudio(SOTHISATTACK2KEY, "astrea/audio/sfx/SothisAttack2.ogg");
        BaseMod.addAudio(SOTHISATTACK3KEY, "astrea/audio/sfx/SothisAttack3.ogg");
        BaseMod.addAudio(SOTHISATTACK4KEY, "astrea/audio/sfx/SothisAttack4.ogg");
        BaseMod.addAudio(SOTHISATTACK5KEY, "astrea/audio/sfx/SothisAttack5.ogg");

        BaseMod.addAudio(SOTHISDIEKEY, "astrea/audio/sfx/SothisDie.ogg");

        BaseMod.addAudio(SOTHISHIT1KEY, "astrea/audio/sfx/SothisHit1.ogg");
        BaseMod.addAudio(SOTHISHIT2KEY, "astrea/audio/sfx/SothisHit2.ogg");
        BaseMod.addAudio(SOTHISHIT3KEY, "astrea/audio/sfx/SothisHit3.ogg");

        BaseMod.addAudio(SOTHISTALK1KEY, "astrea/audio/sfx/SothisTalk1.ogg");

        BaseMod.addAudio(SOULHEATKEY, "astrea/audio/sfx/SoulHeatStart.ogg");
        BaseMod.addAudio(SOULHEATCAPKEY, "astrea/audio/sfx/SoulHeatCapped.ogg");
        BaseMod.addAudio(HOVERHEATUIKEY, "astrea/audio/sfx/HeatUIHover.ogg");
    }
}
