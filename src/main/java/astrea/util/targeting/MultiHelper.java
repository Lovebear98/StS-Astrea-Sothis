package astrea.util.targeting;

import com.megacrit.cardcrawl.monsters.AbstractMonster;
import spireTogether.monsters.CharacterEntity;
import spireTogether.network.P2P.P2PManager;
import spireTogether.network.P2P.P2PPlayer;

import java.util.ArrayList;

import static spireTogether.SpireTogetherMod.isConnected;
import static spireTogether.network.P2P.P2PManager.GetPlayerCount;
import static spireTogether.util.SpireHelp.Multiplayer.Players.IsAloneInRoom;

public class MultiHelper {
    public static boolean MultiCheck(){
        if(isConnected){
            if(GetPlayerCount() > 1){
                return !IsAloneInRoom(true, null);
            }
        }
        return false;
    }
    public static boolean DeadMultiCheck(){
        if(isConnected){
            if(GetPlayerCount() > 1){
                return !IsAloneInRoom(false, null);
            }
        }
        return false;
    }


    public static ArrayList<AbstractMonster> addplayers(ArrayList<AbstractMonster> monList) {
        if (isConnected) {// 25

            for(P2PPlayer p : P2PManager.GetAllPlayersAsList()){
                if(p.location.IsSameAsCurrentRoomAndAction() && p.IsTechnicallyAlive()){
                    CharacterEntity entity = p.GetEntity();
                    monList.add(entity);
                }
            }
        }
        return monList;
    }

    public static void updateHovered(SothisSelfOrEnemyTargeting inst, ArrayList<AbstractMonster> list) {
        for(AbstractMonster m: list){
            if (!m.isDeadOrEscaped()) {// 45
                m.hb.update();// 46
                if (m.hb.hovered) {// 47
                    inst.setHovered(m);// 48
                    break;// 49
                }
            }
        }
        if(isConnected){
            for(P2PPlayer p : P2PManager.GetAllPlayersAsList()){
                if(p.location.IsSameAsCurrentRoomAndAction() && p.IsTechnicallyAlive()){
                    CharacterEntity entity = p.GetEntity();
                    if (entity != null && (entity.hb.hovered || p.GetInfobox().IsHovered())) {
                        entity.hb.update();
                        if (entity.hb.hovered) {// 47
                            inst.setHovered(entity);// 48
                            break;// 49
                        }
                    }
                }
            }
        }
    }
}
