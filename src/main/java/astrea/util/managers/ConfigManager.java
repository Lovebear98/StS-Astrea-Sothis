package astrea.util.managers;

import basemod.ModLabeledToggleButton;
import basemod.ModPanel;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;

import java.util.Properties;

import static astrea.util.managers.Wiz.CONFIGTEXTTEMPLATE;

public class ConfigManager {
    /// Keep track of our SpireConfig so we can call back to it and save it whenever
    public static SpireConfig AstreaConfig;
    public static Properties AstreaDefaults = new Properties();

    public static final String ENABLE_EAR_CANDY = "EnableEarCandy";
    public static boolean EnableEarCandy = true;

    public static final String ENABLE_MUSIC = "EnableEarCandy";
    public static boolean EnableMusic = true;

    public static boolean CorruptionEnabled = false;


    public static final String ENABLE_EYE_CANDY = "EnableEarCandy";
    public static boolean EnableEyeCandy = true;


    ///Set all our defaults, then load them
    public static void SetConfigDefaults(){
        AstreaDefaults.setProperty(ENABLE_EAR_CANDY, Boolean.toString(EnableEarCandy));
        try {
            /// Load the config
            AstreaConfig.load();
            /// And set our matching boolean to the contents of the config
            EnableEarCandy = AstreaConfig.getBool(ENABLE_EAR_CANDY);
            EnableMusic = AstreaConfig.getBool(ENABLE_MUSIC);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    ///Add all our config buttons
    public static void AddConfigButtons(ModPanel settingsPanel){

        /// This example buttons turns on and off custom mod sounds
        ModLabeledToggleButton templateConfig = new ModLabeledToggleButton(CONFIGTEXTTEMPLATE(),
                350.0f, yPos(1), Settings.CREAM_COLOR, FontHelper.charDescFont,
                EnableEarCandy, settingsPanel, (label) -> {
        }, (button) -> {
            /// After clicking the button, modify the matching setting then save
            EnableEarCandy = button.enabled;
            saveConfig();
        });
        settingsPanel.addUIElement(templateConfig);
    }


    /// Save the config on the fly
    public static void saveConfig() {
        try {
            AstreaConfig.save();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /// This method lets us automatically space entries apart easily
    public static float yPos(int i){
        return 750.0f - (50.0f * (i - 1));
    }
}
