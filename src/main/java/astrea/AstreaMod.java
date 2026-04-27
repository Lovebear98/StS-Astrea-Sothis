package astrea;

import astrea.cards.BaseCard;
import astrea.cards.skill.ShieldBoomerang;
import astrea.character.SothisCharacter;
import astrea.powers.custompowers.ChantingPower;
import astrea.powers.custompowers.HourglassMasteryPower;
import astrea.relics.BaseRelic;
import astrea.ui.SoulHeatUI;
import astrea.ui.SuspendPanel;
import astrea.util.CustomActions.*;
import astrea.util.CustomTags;
import astrea.util.GeneralUtils;
import astrea.util.KeywordInfo;
import astrea.util.TextureLoader;
import astrea.util.managers.SetupManager;
import astrea.util.targeting.SothisSelfOrEnemyTargeting;
import basemod.AutoAdd;
import basemod.BaseMod;
import basemod.ModPanel;
import basemod.interfaces.*;
import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglFileHandle;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.evacipated.cardcrawl.mod.stslib.patches.CustomTargeting;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.evacipated.cardcrawl.modthespire.ModInfo;
import com.evacipated.cardcrawl.modthespire.Patcher;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.actions.utility.DiscardToHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.scannotation.AnnotationDB;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static astrea.util.managers.ConfigManager.*;
import static astrea.util.managers.SetupManager.*;
import static astrea.util.managers.Wiz.*;

@SpireInitializer
public class AstreaMod implements
        EditStringsSubscriber,
        EditKeywordsSubscriber,
        PostInitializeSubscriber,
        EditCharactersSubscriber,
        EditCardsSubscriber,
        EditRelicsSubscriber,
        AddAudioSubscriber,
        OnStartBattleSubscriber,
        PostBattleSubscriber,
        OnPlayerTurnStartPostDrawSubscriber,
        OnCardUseSubscriber,
        PostPowerApplySubscriber
{

    public static ModInfo info;
    public static String modID; //Edit your pom.xml to change this
    static { loadModInfo(); }
    private static final String resourcesFolder = checkResourcesPath();
    public static final Logger logger = LogManager.getLogger(modID);
    public static String makeID(String id) {
        return modID + ":" + id;
    }



    /// Variables to detect for any cross-mod content ///
    public static final boolean multiCompat;
    static{
        multiCompat = Loader.isModLoaded("spireTogether");

        logger.info("Checking for [Together in Spire] to enable ally targeting fixes...");
        if(multiCompat){
            logger.info("Found [Together in Spire] - Ally targeting is enabled!");
        }else{
            logger.info("[Together in Spire] not found - Ally targeting disabled.");
        }
    }


    public static SoulHeatUI HeatPanel;
    public static SuspendPanel SuspendPileButton;

    public static void initialize() {
        new AstreaMod();
        SothisCharacter.Meta.registerColor();
    }
    public AstreaMod() {
        BaseMod.subscribe(this); //This will make BaseMod trigger all the subscribers at their appropriate times.

        logger.info(modID + " subscribed to BaseMod.");

        try
        {
            AstreaConfig = new SpireConfig("Astrea Mod", "AstreaConfig", AstreaDefaults);
        }
        catch (IOException e)
        {
            logger.error("Astrea Mod config initialisation failed:");
            e.printStackTrace();
        }
        SetConfigDefaults();
    }

    @Override
    public void receivePostInitialize() {
        HeatPanel = new SoulHeatUI();
        SuspendPileButton = new SuspendPanel();
        CustomTargeting.registerCustomTargeting(SothisSelfOrEnemyTargeting.RELIEF_TARGET, new SothisSelfOrEnemyTargeting());

        /// Add any events we want to add
        AddEvents();
        /// Set up our mod panel config
        Texture badgeTexture = TextureLoader.getTexture(imagePath("badge.png"));
        ModPanel settingsPanel = new ModPanel();
        AddConfigButtons(settingsPanel);
        /// Set up our save fields
        SetupSaveFields();
        ///This step is last because it's what registers everything else in the settings panel
        BaseMod.registerModBadge(badgeTexture, info.Name, GeneralUtils.arrToString(info.Authors), info.Description, settingsPanel);
        /// Register any screens we've made
        RegisterScreens();
        /// Add any potions we've made
        AddPotions();
        /// Add any dynamic text variables we've made
        AddDynamicTextVariables();
    }


    /*----------Localization----------*/

    //This is used to load the appropriate localization files based on language.
    private static String getLangString()
    {
        return Settings.language.name().toLowerCase();
    }
    private static final String defaultLanguage = "eng";

    public static final Map<String, KeywordInfo> keywords = new HashMap<>();

    @Override
    public void receiveEditStrings() {
        /*
            First, load the default localization.
            Then, if the current language is different, attempt to load localization for that language.
            This results in the default localization being used for anything that might be missing.
            The same process is used to load keywords slightly below.
        */
        loadLocalization(defaultLanguage); //no exception catching for default localization; you better have at least one that works.
        if (!defaultLanguage.equals(getLangString())) {
            try {
                loadLocalization(getLangString());
            }
            catch (GdxRuntimeException e) {
                e.printStackTrace();
            }
        }
    }

    private void loadLocalization(String lang) {
        //While this does load every type of localization, most of these files are just outlines so that you can see how they're formatted.
        //Feel free to comment out/delete any that you don't end up using.
        BaseMod.loadCustomStringsFile(CardStrings.class,
                localizationPath(lang, "CardStrings.json"));
        BaseMod.loadCustomStringsFile(CharacterStrings.class,
                localizationPath(lang, "CharacterStrings.json"));
        BaseMod.loadCustomStringsFile(EventStrings.class,
                localizationPath(lang, "EventStrings.json"));
        BaseMod.loadCustomStringsFile(OrbStrings.class,
                localizationPath(lang, "OrbStrings.json"));
        BaseMod.loadCustomStringsFile(PotionStrings.class,
                localizationPath(lang, "PotionStrings.json"));
        BaseMod.loadCustomStringsFile(PowerStrings.class,
                localizationPath(lang, "PowerStrings.json"));
        BaseMod.loadCustomStringsFile(RelicStrings.class,
                localizationPath(lang, "RelicStrings.json"));
        BaseMod.loadCustomStringsFile(UIStrings.class,
                localizationPath(lang, "UIStrings.json"));
        BaseMod.loadCustomStringsFile(TutorialStrings.class,
                localizationPath(lang, "TutorialStrings.json"));
        BaseMod.loadCustomStringsFile(MonsterStrings.class,
                localizationPath(lang, "MonsterStrings.json"));
        BaseMod.loadCustomStringsFile(StanceStrings.class,
                localizationPath(lang, "StanceStrings.json"));
    }

    @Override
    public void receiveEditKeywords()
    {
        Gson gson = new Gson();
        String json = Gdx.files.internal(localizationPath(defaultLanguage, "Keywords.json")).readString(String.valueOf(StandardCharsets.UTF_8));
        KeywordInfo[] keywords = gson.fromJson(json, KeywordInfo[].class);
        for (KeywordInfo keyword : keywords) {
            keyword.prep();
            registerKeyword(keyword);
        }

        if (!defaultLanguage.equals(getLangString())) {
            try
            {
                json = Gdx.files.internal(localizationPath(getLangString(), "Keywords.json")).readString(String.valueOf(StandardCharsets.UTF_8));
                keywords = gson.fromJson(json, KeywordInfo[].class);
                for (KeywordInfo keyword : keywords) {
                    keyword.prep();
                    registerKeyword(keyword);
                }
            }
            catch (Exception e)
            {
                logger.warn(modID + " does not support " + getLangString() + " keywords.");
            }
        }
    }

    private void registerKeyword(KeywordInfo info) {
        BaseMod.addKeyword(modID.toLowerCase(), info.PROPER_NAME, info.NAMES, info.DESCRIPTION);
        if (!info.ID.isEmpty())
        {
            keywords.put(info.ID, info);
        }
    }

    //These methods are used to generate the correct filepaths to various parts of the resources folder.
    public static String localizationPath(String lang, String file) {
        return resourcesFolder + "/localization/" + lang + "/" + file;
    }

    public static String imagePath(String file) {
        return resourcesFolder + "/images/" + file;
    }
    public static String characterPath(String file) {
        return resourcesFolder + "/images/character/" + file;
    }
    public static String powerPath(String file) {
        return resourcesFolder + "/images/powers/" + file;
    }
    public static String relicPath(String file) {
        return resourcesFolder + "/images/relics/" + file;
    }

    /**
     * Checks the expected resources path based on the package name.
     */
    private static String checkResourcesPath() {
        String name = AstreaMod.class.getName(); //getPackage can be iffy with patching, so class name is used instead.
        int separator = name.indexOf('.');
        if (separator > 0)
            name = name.substring(0, separator);

        FileHandle resources = new LwjglFileHandle(name, Files.FileType.Internal);

        if (!resources.exists()) {
            throw new RuntimeException("\n\tFailed to find resources folder; expected it to be named \"" + name + "\"." +
                    " Either make sure the folder under resources has the same name as your mod's package, or change the line\n" +
                    "\t\"private static final String resourcesFolder = checkResourcesPath();\"\n" +
                    "\tat the top of the " + AstreaMod.class.getSimpleName() + " java file.");
        }
        if (!resources.child("images").exists()) {
            throw new RuntimeException("\n\tFailed to find the 'images' folder in the mod's 'resources/" + name + "' folder; Make sure the " +
                    "images folder is in the correct location.");
        }
        if (!resources.child("localization").exists()) {
            throw new RuntimeException("\n\tFailed to find the 'localization' folder in the mod's 'resources/" + name + "' folder; Make sure the " +
                    "localization folder is in the correct location.");
        }

        return name;
    }

    /**
     * This determines the mod's ID based on information stored by ModTheSpire.
     */
    private static void loadModInfo() {
        Optional<ModInfo> infos = Arrays.stream(Loader.MODINFOS).filter((modInfo)->{
            AnnotationDB annotationDB = Patcher.annotationDBMap.get(modInfo.jarURL);
            if (annotationDB == null)
                return false;
            Set<String> initializers = annotationDB.getAnnotationIndex().getOrDefault(SpireInitializer.class.getName(), Collections.emptySet());
            return initializers.contains(AstreaMod.class.getName());
        }).findFirst();
        if (infos.isPresent()) {
            info = infos.get();
            modID = info.ID;
        }
        else {
            throw new RuntimeException("Failed to determine mod info/ID based on initializer.");
        }
    }

    @Override
    public void receiveEditCharacters() {
        SothisCharacter.Meta.registerCharacter();
    }

    @Override
    public void receiveEditCards() {
        new AutoAdd(modID) //Loads files from this mod
                .packageFilter(BaseCard.class) //In the same package as this class
                .setDefaultSeen(true) //And marks them as seen in the compendium
                .cards(); //Adds the cards
        /// Add our dynamic variables for cards
        SetupManager.DynamicCardVariables();
    }

    @Override
    public void receiveEditRelics() { //somewhere in the class
        new AutoAdd(modID) //Loads files from this mod
                .packageFilter(BaseRelic.class) //In the same package as this class
                .any(BaseRelic.class, (info, relic) -> { //Run this code for any classes that extend this class
                    if (relic.pool != null)
                        BaseMod.addRelicToCustomPool(relic, relic.pool); //Register a custom character specific relic
                    else
                        BaseMod.addRelic(relic, relic.relicType); //Register a shared or base game character specific relic

                    //If the class is annotated with @AutoAdd.Seen, it will be marked as seen, making it visible in the relic library.
                    //If you want all your relics to be visible by default, just remove this if statement.
                    if (info.seen)
                        UnlockTracker.markRelicAsSeen(relic.relicId);
                });
    }

    @Override
    public void receiveAddAudio() {AddSounds();}



















    @Override
    public void receiveOnBattleStart(AbstractRoom abstractRoom) {
        PreCombat();
    }
    @Override
    public void receivePostBattle(AbstractRoom abstractRoom) {
        PostCombat();
    }


    /// We made this method for a patch to allow generic end-of-turn effects
    public static void receiveOnPlayerTurnEnd(){
        AbstractDungeon.actionManager.addToBottom(new ReleaseSuspendAction());
    }


    @Override
    public void receiveCardUsed(AbstractCard abstractCard) {
        AbstractDungeon.actionManager.addToTop(new GainSoulHeatAction());
        if(abstractCard.hasTag(CustomTags.Chanting)){
            AbstractDungeon.actionManager.addToBottom(new GainChantingAction());
        }else if(abstractCard.type == AbstractCard.CardType.ATTACK){
            if(p().hasPower(ChantingPower.POWER_ID)){
                AbstractDungeon.actionManager.addToBottom(new LoseChantingAction());
            }
        }
    }

    @Override
    public void receiveOnPlayerTurnStartPostDraw() {
        AbstractDungeon.actionManager.addToTop(new ResetSoulHeatAction());
    }

    @Override
    public void receivePostPowerApplySubscriber(AbstractPower power, AbstractCreature abstractCreature, AbstractCreature abstractCreature1) {
        if(power.ID.equals(HourglassMasteryPower.POWER_ID)){
            if(abstractCreature1 == p() && !p().discardPile.isEmpty()){
                for(AbstractCard c: p().discardPile.group){
                    if(c.cardID.equals(ShieldBoomerang.ID)){
                        AbstractDungeon.actionManager.addToBottom(new DiscardToHandAction(c));
                    }
                }

            }
        }
    }
}
