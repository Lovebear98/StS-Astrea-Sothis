package astrea.character;

import astrea.cards.starter.Defend;
import astrea.cards.starter.MinorRelief;
import astrea.cards.starter.SonataOfHealing;
import astrea.cards.starter.Strike;
import astrea.relics.customrelics.BehenianHourglass;
import basemod.BaseMod;
import basemod.abstracts.CustomPlayer;
import basemod.animations.AbstractAnimation;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.cutscenes.CutscenePanel;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

import java.util.ArrayList;
import java.util.List;

import static astrea.AstreaMod.characterPath;
import static astrea.AstreaMod.makeID;
import static astrea.util.managers.ImageManager.EndBackground;
import static astrea.util.managers.SoundManager.*;

public class SothisCharacter extends CustomPlayer {
    public static final int ENERGY_PER_TURN = 3;
    public static final int MAX_HP = 41;
    public static final int STARTING_GOLD = 99;
    public static final int CARD_DRAW = 5;
    public static final int ORB_SLOTS = 0;

    public static final Color SothisColor = new Color(63/255f, 90/255f, 165/255f, 1f);

    private static final String ID = makeID(SothisCharacter.class.getSimpleName());


    private static String[] getNames() { return CardCrawlGame.languagePack.getCharacterString(ID).NAMES; }
    private static String[] getText() { return CardCrawlGame.languagePack.getCharacterString(ID).TEXT; }

    public static class Meta {
        @SpireEnum
        public static PlayerClass SOTHIS_CHARACTER;
        @SpireEnum(name = "Behenian Blue")
        public static AbstractCard.CardColor CARD_COLOR;
        @SpireEnum(name = "Behenian Blue") @SuppressWarnings("unused")
        public static CardLibrary.LibraryType LIBRARY_COLOR;

        //Character select images
        private static final String CHAR_SELECT_BUTTON = characterPath("select/button.png");
        private static final String CHAR_SELECT_PORTRAIT = characterPath("select/portrait.png");

        //Character card images
        private static final String BG_ATTACK = characterPath("cardback/bg_attack.png");
        private static final String BG_ATTACK_P = characterPath("cardback/bg_attack_p.png");
        private static final String BG_SKILL = characterPath("cardback/bg_skill.png");
        private static final String BG_SKILL_P = characterPath("cardback/bg_skill_p.png");
        private static final String BG_POWER = characterPath("cardback/bg_power.png");
        private static final String BG_POWER_P = characterPath("cardback/bg_power_p.png");
        private static final String ENERGY_ORB = characterPath("cardback/energy_orb.png");
        private static final String ENERGY_ORB_P = characterPath("cardback/energy_orb_p.png");
        private static final String SMALL_ORB = characterPath("cardback/small_orb.png");

        private static final Color cardColor = new Color(63/255f, 90/255f, 165/255f, 1f);










        //Methods that will be used in the main mod file
        public static void registerColor() {
            BaseMod.addColor(CARD_COLOR, cardColor,
                    BG_ATTACK, BG_SKILL, BG_POWER, ENERGY_ORB,
                    BG_ATTACK_P, BG_SKILL_P, BG_POWER_P, ENERGY_ORB_P,
                    SMALL_ORB);
        }

        public static void registerCharacter() {
            BaseMod.addCharacter(new SothisCharacter(), CHAR_SELECT_BUTTON, CHAR_SELECT_PORTRAIT);
        }
    }

    @Override
    public Texture getCutsceneBg() {
        return EndBackground;
    }

   @Override
   public List<CutscenePanel> getCutscenePanels() {
       List<CutscenePanel> panels = new ArrayList<>();
       panels.add(new CutscenePanel("astrea/images/scenes/Scene1.png", "POWER_TIME_WARP"));
       panels.add(new CutscenePanel("astrea/images/scenes/Scene2.png", "HEART_BEAT"));
       panels.add(new CutscenePanel("astrea/images/scenes/Scene3.png", "HEART_BEAT"));
       panels.add(new CutscenePanel("astrea/images/scenes/Scene4.png"));
       return panels;
   }

    //In-game images
    private static final String SHOULDER_1 = characterPath("shoulder.png"); //Shoulder 1 and 2 are used at rest sites.
    private static final String SHOULDER_2 = characterPath("shoulder2.png");
    private static final String CORPSE = characterPath("corpse.png"); //Corpse is when you die.


    //Actual character class code below this point

    public SothisCharacter() {
        super(getNames()[0], Meta.SOTHIS_CHARACTER,
                new SothisOrb(), //Energy Orb
                new AbstractAnimation() {
                    @Override
                    public Type type() {
                        return Type.NONE;
                    }
                });

        initializeClass(characterPath("character.png"),
                SHOULDER_2,
                SHOULDER_1,
                CORPSE,
                getLoadout(),
                00F, -0.0F, 200.0F, 260.0F, //Character hitbox. x y position, then width and height.
                new EnergyManager(ENERGY_PER_TURN));

        //Location for text bubbles. You can adjust it as necessary later. For most characters, these values are fine.
        dialogX = (drawX + 0.0F * Settings.scale);
        dialogY = (drawY + 280.0F * Settings.scale);
    }

    @Override
    public String getSensoryStoneText() {
        return getText()[3];
    }

    @Override
    public void damage(DamageInfo info) {
        if (info.owner != null && info.type != DamageInfo.DamageType.THORNS) {
            if(info.output - this.currentBlock > 0){
                tryDamagedSound();
            }
        }
        super.damage(info);
    }

    @Override
    public void useCard(AbstractCard c, AbstractMonster monster, int energyOnUse) {
        super.useCard(c, monster, energyOnUse);
        if(c.type == AbstractCard.CardType.ATTACK){
            tryAttackSound();
        }
    }

    @Override
    public ArrayList<String> getStartingDeck() {
        ArrayList<String> retVal = new ArrayList<>();

        retVal.add(SonataOfHealing.ID);
        retVal.add(MinorRelief.ID);

        retVal.add(Strike.ID);
        retVal.add(Strike.ID);
        retVal.add(Strike.ID);
        retVal.add(Strike.ID);
        retVal.add(Strike.ID);

        retVal.add(Defend.ID);
        retVal.add(Defend.ID);
        retVal.add(Defend.ID);



        return retVal;
    }

    /// {TEMPLATE} character starting relics can be changed here.
    @Override
    public ArrayList<String> getStartingRelics() {
        ArrayList<String> retVal = new ArrayList<>();
        //IDs of starting relics. You can have multiple, but one is recommended.
        retVal.add(BehenianHourglass.ID);

        return retVal;
    }

    /// {TEMPLATE} character event card can be changed here (non Strike/Def starter card)
    @Override
    public AbstractCard getStartCardForEvent() {
        return new SonataOfHealing();
    }

    /// {TEMPLATE} character HP Loss on A14 can be changed here.
    @Override
    public int getAscensionMaxHPLoss() {
        return 4;
    }
    /// {TEMPLATE} character heart kill attacks can be changed here.
    @Override
    public AbstractGameAction.AttackEffect[] getSpireHeartSlashEffect() {
        //These attack effects will be used when you attack the heart.
        return new AbstractGameAction.AttackEffect[] {
                AbstractGameAction.AttackEffect.SLASH_HORIZONTAL,
                AbstractGameAction.AttackEffect.SLASH_DIAGONAL,
                AbstractGameAction.AttackEffect.SLASH_VERTICAL,
        };
    }
    /// {TEMPLATE} character colors can be changed here.
    private final Color cardRenderColor = SothisColor.cpy(); //Used for some vfx on moving cards (sometimes) (maybe)
    private final Color cardTrailColor = SothisColor.cpy(); //Used for card trail vfx during gameplay.
    private final Color slashAttackColor = SothisColor.cpy(); //Used for a screen tint effect when you attack the heart.
    @Override
    public Color getCardRenderColor() {
        return cardRenderColor;
    }

    @Override
    public Color getCardTrailColor() {
        return cardTrailColor;
    }

    @Override
    public Color getSlashAttackColor() {
        return slashAttackColor;
    }

    @Override
    public BitmapFont getEnergyNumFont() {
        //Font used to display your current energy.
        //energyNumFontRed, Blue, Green, and Purple are used by the basegame characters.
        //It is possible to make your own, but not convenient.
        return FontHelper.energyNumFontBlue;
    }

    @Override
    public void doCharSelectScreenSelectEffect() {
        //This occurs when you click the character's button in the character select screen.
        //See SoundMaster for a full list of existing sound effects, or look at BaseMod's wiki for adding custom audio.
        CardCrawlGame.sound.playA(SOTHISTALK1KEY, MathUtils.random(-0.1F, 0.1F));
        CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.MED, ScreenShake.ShakeDur.SHORT, false);
    }
    @Override
    public String getCustomModeCharacterButtonSoundKey() {
        //Similar to doCharSelectScreenSelectEffect, but used for the Custom mode screen. No shaking.
        return SOTHISTALK1KEY;
    }

    //Don't adjust these four directly, adjust the contents of the CharacterStrings.json file.
    @Override
    public String getLocalizedCharacterName() {
        return getNames()[0];
    }
    @Override
    public String getTitle(PlayerClass playerClass) {
        return getNames()[1];
    }
    @Override
    public String getSpireHeartText() {
        return getText()[1];
    }
    @Override
    public String getVampireText() {
        return getText()[2]; //Generally, the only difference in this text is how the vampires refer to the player.
    }

    /*- You shouldn't need to edit any of the following methods. -*/

    //This is used to display the character's information on the character selection screen.
    @Override
    public CharSelectInfo getLoadout() {
        return new CharSelectInfo(getNames()[0], getText()[0],
                MAX_HP, MAX_HP, ORB_SLOTS, STARTING_GOLD, CARD_DRAW, this,
                getStartingRelics(), getStartingDeck(), false);
    }

    @Override
    public AbstractCard.CardColor getCardColor() {
        return Meta.CARD_COLOR;
    }

    @Override
    public AbstractPlayer newInstance() {
        //Makes a new instance of your character class.
        return new SothisCharacter();
    }

    @Override
    public void gainEnergy(int e) {
        super.gainEnergy(e);
    }

    @Override
    public void applyPreCombatLogic() {
        super.applyPreCombatLogic();
    }

    @Override
    public void playDeathAnimation() {
        super.playDeathAnimation();
        PlaySound(SOTHISDIEKEY);
    }

    private void tryAttackSound(){
        int i = MathUtils.random(1, 100);
        if(i >= 71){
            if(i >= 95){
                PlaySound(SOTHISATTACK5KEY);
            }else if(i >=89){
                PlaySound(SOTHISATTACK4KEY);
            }else if(i >= 83){
                PlaySound(SOTHISATTACK3KEY);
            }else if(i >= 77){
                PlaySound(SOTHISATTACK2KEY);
            }else{
                PlaySound(SOTHISATTACK1KEY);
            }
        }
    }

    private void tryDamagedSound(){
        int i = MathUtils.random(1, 100);
        if(i >= 71){
            if(i >= 91){
                PlaySound(SOTHISHIT3KEY);
            }else if(i >= 81){
                PlaySound(SOTHISHIT2KEY);
            }else{
                PlaySound(SOTHISHIT1KEY);
            }
        }
    }


    ///Check through the list for effects, then make them if they're not present.
    @Override
    public void updateVictoryVfx(ArrayList<AbstractGameEffect> effects) {
        super.updateVictoryVfx(effects);
    }
}