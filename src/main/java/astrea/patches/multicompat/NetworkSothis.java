//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package astrea.patches.multicompat;

import astrea.character.SothisCharacter;
import astrea.character.SothisOrb;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import skindex.entities.player.SkindexPlayerAtlasEntity;
import skindex.registering.SkindexRegistry;
import skindex.skins.player.PlayerSkin;
import spireTogether.SpireTogetherMod;
import spireTogether.modcompat.generic.energyorbs.CustomizableEnergyOrbCustom;
import spireTogether.modcompat.marisa.skins.MarisaGhostSkin.SkinData;
import spireTogether.monsters.CharacterEntity;
import spireTogether.monsters.playerChars.NetworkCharPreset;
import spireTogether.ui.elements.presets.Nameplate;
import spireTogether.util.Reflection;
import spireTogether.util.TextureManager;
import spireTogether.util.UIElements.Nameplates;

import static astrea.character.SothisCharacter.Meta.SOTHIS_CHARACTER;

public class NetworkSothis extends NetworkCharPreset implements SkindexPlayerAtlasEntity {
    public static final String NAME = "SOTHIS";
    public static final AbstractPlayer.PlayerClass PLAYER_CLASS = SOTHIS_CHARACTER;


    public NetworkSothis() {
        super(new SothisCharacter());
        this.energyOrb = new SothisOrb();
        this.lobbyScale = 1.3F;
    }

    public PlayerSkin GetGhostSkin() {
        return SkindexRegistry.getPlayerSkinByClassAndId(PLAYER_CLASS, SkinData.ID);// 48
    }

    public CharacterEntity CreateNew() {
        return new NetworkSothis();// 54
    }

    public Texture GetNameplateIcon(String nameplate) {
        return TextureManager.getTexture("astrea/images/character/multicompat/defaulticon.png");// 60
    }

    public Texture GetDefaultIcon() {
        return TextureManager.getTexture("astrea/images/character/multicompat/defaulticon.png");// 64
    }

    public Color GetCharColor() {
        return SothisCharacter.SothisColor.cpy();// 68
    }

    public Texture GetWhiteSpecialIcon() {
        return TextureManager.getTexture("astrea/images/character/multicompat/whiteSpecial.png");// 72
    }

    public Nameplate GetNameplateUnlock() {
        return Nameplates.basic;// 76
    }

    public String GetThreeLetterID() {
        return "ASO";// 81
    }

    public static class Patches {
        @SpirePatch(
            clz = SpireTogetherMod.class,
            method = "RegisterModdedChars",
            requiredModId = "spireTogether",
            optional = true
        )
        public static class Insert {
            public static void Postfix() {
                SpireTogetherMod.allCharacterEntities.put(NetworkSothis.PLAYER_CLASS, new NetworkSothis());// 89
            }// 90
        }
    }
}
