package astrea.util.managers;

import astrea.util.CustomDynamicVariables.*;
import basemod.BaseMod;
import basemod.abstracts.CustomSavable;
import basemod.patches.com.megacrit.cardcrawl.cards.AbstractCard.DynamicTextBlocks;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static astrea.AstreaMod.makeID;
import static astrea.util.managers.MechanicManager.getSoulHeat;
import static astrea.util.managers.Wiz.isInCombat;

public class SetupManager {
    /// Set up audio
    public static void AddSounds(){
        /// We want setup all in one place to be easy to find, but we defer this
        /// to the Sound Manager so audio methods are in one place
        SoundManager.AddSounds();
    }

    /// Set up events
    public static void AddEvents(){

    }
    /// Set up potions
    public static void AddPotions(){
        ///BaseMod.addPotion({TemplatePotion}.class,
        ///        Color.WHITE.cpy(), null, null,
        ///                {TemplatePotion}.POTION_ID, TemplateCharacter.Meta.TEMPLATE_CHARACTER);

    }
    /// Set
    ///  up Enemies
    public static void AddMonsters(){

    }


    /// Set up dynamic text block variables {@@}
    public static void AddDynamicTextVariables(){
        DynamicTextBlocks.registerCustomCheck(makeID("EXG"), card -> {
            int i = getSoulHeat();
            if (AbstractDungeon.player != null) {
                if(isInCombat()){
                    return getSoulHeat();
                }else{
                    return 0;
                }
            }
            return 0;
        });
    }

    ///Set up dynamic variables for cards like second damage, base damage, etc.
    public static void DynamicCardVariables() {
        BaseMod.addDynamicVariable(new secondMagicVar());
        BaseMod.addDynamicVariable(new SpecialVar());
        BaseMod.addDynamicVariable(new BaseDamageVar());
        BaseMod.addDynamicVariable(new BaseBlockVar());
        BaseMod.addDynamicVariable(new SoulHeatVar());
    }


    public static void RegisterScreens(){

    }

    /// The key we'll set for our example
    public static final String TemplateSaveKey = makeID("TemplateSaveKey");
    /// This is the boolean we'll save our field to on the player
    public static boolean exampleSavedBoolen = false;


    public static void SetupSaveFields() {
        ///Add a save field with a certain text key, and the type <Boolean>
        BaseMod.addSaveField(TemplateSaveKey, new CustomSavable<Boolean>() {
            /// When we're trying to save, how do we figure out what to store?
            @Override
            public Boolean onSave() {
                return false;
            }

            /// When we're trying to load, how do we use what we saved?
            @Override
            public void onLoad(Boolean savedBoolean) {
                if (savedBoolean != null) {
                    exampleSavedBoolen = savedBoolean;
                }
            }
        });
    }
}
