package astrea.patches;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import static astrea.AstreaMod.HeatPanel;
import static astrea.AstreaMod.SuspendPileButton;
import static astrea.util.managers.MechanicManager.SuspendPile;
import static astrea.util.managers.Wiz.heatEnabled;
import static astrea.util.managers.Wiz.isInCombat;


///The patch tht handles showing our UI
public class UIPatch {

    public static float Pile_Orb_x = 0f;
    public static float Pile_Orb_y = 0f;

    @SpirePatch(clz = EnergyPanel.class, method = "renderOrb", paramtypes = {"com.badlogic.gdx.graphics.g2d.SpriteBatch"})
    public static class RenderPatch{
        public static void Prefix(EnergyPanel __instance, SpriteBatch sb){
            if(isInCombat()){
                Pile_Orb_x = __instance.current_x;
                Pile_Orb_y = __instance.current_y;
                if(heatEnabled()){
                    HeatPanel.render(sb);
                }
                if(!SuspendPile.isEmpty()){
                    SuspendPileButton.render(sb);
                }
            }
        }
    }
    @SpirePatch(clz = EnergyPanel.class, method = "update")
    public static class UpdatePatch{
        public static void Prefix(){
            if(isInCombat()){
                if(heatEnabled()){
                    HeatPanel.update();
                }
                if(!SuspendPile.isEmpty()){
                    SuspendPileButton.update();
                }
            }
        }
    }
}