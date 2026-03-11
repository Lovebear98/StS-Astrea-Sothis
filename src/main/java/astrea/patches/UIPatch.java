package astrea.patches;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import static astrea.AstreaMod.HeatPanel;
import static astrea.util.managers.Wiz.corruptionEnabled;
import static astrea.util.managers.Wiz.isInCombat;


///The patch tht handles showing our UI
public class UIPatch {

    @SpirePatch(clz = EnergyPanel.class, method = "renderOrb", paramtypes = {"com.badlogic.gdx.graphics.g2d.SpriteBatch"})
    public static class RenderPatch{
        public static void Prefix(EnergyPanel __instance, SpriteBatch sb){
            if(isInCombat()){
                if(corruptionEnabled()){
                    HeatPanel.render(sb);
                }
            }
        }
    }
    @SpirePatch(clz = EnergyPanel.class, method = "update")
    public static class UpdatePatch{
        public static void Prefix(){
            if(isInCombat()){
                if(corruptionEnabled()){
                    HeatPanel.update();
                }
            }
        }
    }
}