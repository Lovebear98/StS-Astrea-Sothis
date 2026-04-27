package astrea.util.managers;


import astrea.character.SothisCharacter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.exordium.ApologySlime;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.cardManip.CardFlashVfx;

import java.util.ArrayList;

import static astrea.AstreaMod.makeID;
import static astrea.character.SothisOrb.SizeCorrect;
import static astrea.util.managers.ConfigManager.CorruptionEnabled;
import static astrea.util.managers.ConfigManager.EnableEyeCandy;
import static astrea.util.managers.MechanicManager.SoulHeat;
import static astrea.util.managers.MechanicManager.SuspendPile;


public class Wiz {
    /// Sets our various String files to draw from
    public static UIStrings wizStrings = CardCrawlGame.languagePack.getUIString(makeID(Wiz.class.getSimpleName()));
    public static UIStrings configStrings = CardCrawlGame.languagePack.getUIString(makeID(Wiz.class.getSimpleName()+"Config"));
    public static UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(makeID(Wiz.class.getSimpleName()+"UI"));

    ///Keys used for alternative variables, that can be used as !${modID}:M2! in card descriptions
    public static final String SecondMagicKey = makeID("M2");
    public static final String SpecialVarKey = makeID("SV");
    public static final String BaseDamageKey = makeID("BD");
    public static final String BaseBlockKey = makeID("BB");
    public static final String SoulHeatKey = makeID("SH");

    /// The path to our orbs for simplification of getting images
    public static String OrbPath(String text){
        return "astrea/images/orbs/" + text + ".png";
    }

    /// An easy way to call the player without typing AbstractDungeon.player every time.
    public static AbstractPlayer p(){
        return AbstractDungeon.player;
    }


    /// Static methods to call frequent text uses for easy translation
    public static String ReturnText(){
        return uiStrings.EXTRA_TEXT[0];
    }
    public static String DiscardText(){return uiStrings.EXTRA_TEXT[1];}
    public static String KeepText(){
        return uiStrings.EXTRA_TEXT[2];
    }
    public static String CopyText(){
        return uiStrings.EXTRA_TEXT[3];
    }
    public static String AddText(){
        return uiStrings.EXTRA_TEXT[4];
    }
    public static String ModifyText(){return uiStrings.EXTRA_TEXT[5];}
    public static String RecoverText(){
        return uiStrings.EXTRA_TEXT[6];
    }
    public static String NoKindleText(){return uiStrings.EXTRA_TEXT[7];}
    public static String SuspendText(){return uiStrings.EXTRA_TEXT[8];}
    public static String ExhaustText(){
        return uiStrings.EXTRA_TEXT[9];
    }

    /// Template for getting UI strings quickly
    public static String CONFIGTEXTTEMPLATE(){
        return configStrings.TEXT[0];
    }

    /// This helper method converts a number of energy into that many E icons.
    public static String PrintEnergy(int i){
        StringBuilder s = new StringBuilder();
        if(i > 0){
            for(int e = i; e > 0; e-= 1){
                s.append(uiStrings.TEXT[0]);
            }
            s.append(" ");
        }
        return s.toString();
    }

    /// Boolean for if we're in combat or not.
    public static boolean isInCombat() {
        return CardCrawlGame.isInARun() && AbstractDungeon.currMapNode != null && AbstractDungeon.getCurrRoom() != null && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT;
    }

    /// Easy boolean for if it's the player's turn
    public static boolean isTurn(){
        return !AbstractDungeon.actionManager.turnHasEnded && AbstractDungeon.overlayMenu.endTurnButton.enabled;
    }

    /// Easily check if there's only one monster in the room
    public static boolean monsterIsAlone(){
        int i = 0;
        for(AbstractMonster m : AbstractDungeon.getMonsters().monsters){
            if(!m.isDeadOrEscaped()){
                i += 1;
            }
            if(i >= 2){
                return false;
            }
        }
        return true;
    }

    /// Return the monster alone in the room if there is one, or a blank apology slime if not.
    public static AbstractMonster blankMonster;
    public static AbstractMonster aloneMonster(){
        if(p() != null){
            for(AbstractMonster m : AbstractDungeon.getMonsters().monsters){
                if(!m.isDeadOrEscaped()){
                    return m;
                }
            }
        }
        if(blankMonster == null){
            blankMonster = new ApologySlime();
        }
        return blankMonster;
    }

    /// Anything that happens before every combat
    public static void PreCombat(){
        PrePostCombat();
    }

    /// Anything that happens after every combat
    public static void PostCombat(){
        PrePostCombat();
    }
    ///  Anything that happens both before AND after combat
    private static void PrePostCombat() {
        SoulHeat = 0;
        SuspendPile.clear();
    }

    /// Method for generally flashing cards in hand
    public static void FlashCard(AbstractCard card){
        FlashCard(card, DefaultColor.cpy());
    }
    public static void FlashCard(AbstractCard card, Color color){
        if(EnableEyeCandy && AbstractDungeon.player != null && AbstractDungeon.player.hand.contains(card)){
            AbstractDungeon.topLevelEffectsQueue.add(new CardFlashVfx(card, color));
        }
    }
    /// Method for FORCING them to flash
    public static void ForceFlashCard(AbstractCard card, Color color){
        if(EnableEyeCandy){
            AbstractDungeon.topLevelEffectsQueue.add(new CardFlashVfx(card, color));
        }
    }
    /// The color they'll flash if you don't give them a color
    public static final Color DefaultColor = new Color(0.5f, 0.5f, 0.1f, 1f);







    public static boolean heatEnabled(){
        if(p() != null){
            if(p() instanceof SothisCharacter){
                return true;
            }
            return CorruptionEnabled;
        }
        return false;
    }

    public static ArrayList<AbstractCreature> allCharacters(){
        ArrayList<AbstractCreature> tmp = new ArrayList<>(AbstractDungeon.getMonsters().monsters);
        tmp. add(p());
        return tmp;
    }


    public static float textToFloat(String s){
        float newnum;
        try {
            newnum = Integer.parseInt(s);
        }
        catch (NumberFormatException e) {
            newnum = 0;
        }
        return newnum/100f;
    }
    public static float textToFloat(int i) {
        float f = i;
        return f/100f;
    }


    public static void renderCenteredScaled(SpriteBatch sb, Texture texture, float x, float y, float rotation) {
        sb.draw(texture, x, y, (texture.getWidth() * SizeCorrect * 0.5f), (texture.getHeight() * SizeCorrect * 0.5f), texture.getWidth()*SizeCorrect, texture.getHeight()*SizeCorrect, 1f, 1f, rotation, 0, 0, texture.getWidth(), texture.getHeight(), false, false);
    }
    public static void renderCenteredScaled(SpriteBatch sb, Texture texture, float x, float y, float scale, float rotation) {
        sb.draw(texture, x, y, (texture.getWidth() * SizeCorrect * 0.5f), (texture.getHeight() * SizeCorrect * 0.5f), texture.getWidth()*SizeCorrect, texture.getHeight()*SizeCorrect, scale, scale, rotation, 0, 0, texture.getWidth(), texture.getHeight(), false, false);
    }
    public static float timeScale(float currenttime, float maxtime){
        return ((maxtime - currenttime) / maxtime);
    }
}
