package astrea.util.managers;

import astrea.powers.custompowers.DistortionHieroglyphPower;
import astrea.powers.custompowers.HourglassMiragePower;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.CardGroup;

import static astrea.util.managers.SoundManager.*;
import static astrea.util.managers.Wiz.isInCombat;
import static astrea.util.managers.Wiz.p;

public class MechanicManager {

    public static CardGroup SuspendPile = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);

    public static int SoulHeat = 0;
    public static final int BaseSoulHeatCap = 10;

    public static int SoulHeatCap(){
        int i = BaseSoulHeatCap;
        if(p() != null){

        }
        return i;
    }

    public static void gainSoulHeat(int i){
        int IncomingHeat = Math.min(i, SoulHeatCap() - SoulHeat);
        if(IncomingHeat != 0){
            boolean canStartSFX = false;
            boolean canCapSFX = false;

            if(SoulHeat == 0){
                canStartSFX = true;
            }else if(SoulHeat < SoulHeatCap()){
                canCapSFX = true;
            }

            SoulHeat += IncomingHeat;
            if(SoulHeat < 0){
                SoulHeat = 0;
            }else if (SoulHeat > SoulHeatCap()){
                SoulHeat = SoulHeatCap();
            }

            if(SoulHeat > 0 && canStartSFX){
                PlaySound(SOULHEATKEY);
            }else if(SoulHeat >= SoulHeatCap() && canCapSFX){
                PlaySound(SOULHEATCAPKEY);
                if(p().hasPower(HourglassMiragePower.POWER_ID)){
                    ((HourglassMiragePower)p().getPower(HourglassMiragePower.POWER_ID)).triggered();
                }
            }
        }
    }
    public static void resetSoulHeat(){
        SoulHeat = 0;
    }

    public static int getSoulHeat(){
        int i;
        if(isInCombat()){
            i = SoulHeat;
        }else{
            i = 0;
        }
        if(p() != null){
            if(p().hasPower(DistortionHieroglyphPower.POWER_ID)){
                return 10;
            }
        }
        return i;
    }








    public static Color soulHeatColor(){
        int i = MathUtils.random(1, 100);
        if(i <= 5){
            return new Color(0.05f, 0.05f, 0.25f, 1f);
        }else if(i <= 15){
            return new Color(0.2f, 0.05f, 0.1f, 1f);
        }else if(i <= 40){
            return new Color(0.15f, 0.025f, 0.15f, 1f);
        }else{
            return new Color(0.05f, 0.1f, 0.2f, 1f);
        }
    }
}
