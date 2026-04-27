//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package astrea.util.targeting;

import basemod.ReflectionHacks;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.mod.stslib.cards.targeting.TargetingHandler;
import com.evacipated.cardcrawl.mod.stslib.patches.CustomTargeting;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputActionSet;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

import static astrea.AstreaMod.multiCompat;

public class SothisSelfOrEnemyTargeting extends TargetingHandler<AbstractCreature> {
    @SpireEnum
    public static AbstractCard.CardTarget RELIEF_TARGET;
    private AbstractCreature hovered = null;

    public static AbstractCreature getTarget(AbstractCard card) {
        return (AbstractCreature)CustomTargeting.getCardTarget(card);// 25
    }

    public boolean hasTarget() {
        return this.hovered != null;// 32
    }

    public void updateHovered() {
        this.hovered = null;// 37
        AbstractDungeon.player.hb.update();// 39
        if (AbstractDungeon.player.hb.hovered) {// 40
            this.hovered = AbstractDungeon.player;// 41
        } else {
            ArrayList<AbstractMonster> monList = AbstractDungeon.getMonsters().monsters;
            if(multiCompat){
                MultiHelper.updateHovered(this, monList);
            }else{
                for(AbstractMonster m : monList) {// 44
                    if (!m.isDeadOrEscaped()) {// 45
                        m.hb.update();// 46
                        if (m.hb.hovered) {// 47
                            this.hovered = m;// 48
                            break;// 49
                        }
                    }
                }
            }
        }

    }// 54

    public AbstractCreature getHovered() {
        return this.hovered;// 58
    }

    public void clearHovered() {
        this.hovered = null;// 63
    }// 64

    public void renderReticle(SpriteBatch sb) {
        if (this.hovered != null) {// 68
            this.hovered.renderReticle(sb);// 69
        }

    }// 71

    public void setDefaultTarget() {
        this.hovered = AbstractDungeon.player;// 76
    }// 77

    public int getDefaultTargetX() {
        return (int)AbstractDungeon.player.hb.cX;// 80
    }

    public int getDefaultTargetY() {
        return (int)AbstractDungeon.player.hb.cY;// 84
    }

    public void updateKeyboardTarget() {
        int directionIndex = 0;// 89
        if (InputActionSet.left.isJustPressed() || CInputActionSet.left.isJustPressed() || CInputActionSet.altLeft.isJustPressed()) {// 91
            --directionIndex;// 92
        }

        if (InputActionSet.right.isJustPressed() || CInputActionSet.right.isJustPressed() || CInputActionSet.altRight.isJustPressed()) {// 95
            ++directionIndex;// 96
        }

        if (directionIndex != 0) {// 99
            ArrayList<AbstractMonster> sortedMonsters = new ArrayList(AbstractDungeon.getCurrRoom().monsters.monsters);// 100
            sortedMonsters.removeIf(AbstractCreature::isDeadOrEscaped);// 102
            AbstractCreature newTarget = null;// 104
            if (sortedMonsters.isEmpty()) {// 106
                if (this.hovered != null) {// 107
                    return;// 111
                }

                newTarget = AbstractDungeon.player;// 108
            } else {
                sortedMonsters.sort(AbstractMonster.sortByHitbox);// 115
                if (this.hovered == null) {// 117
                    if (directionIndex == 1) {// 118
                        newTarget = (AbstractCreature)sortedMonsters.get(0);// 119
                    } else {
                        newTarget = AbstractDungeon.player;// 121
                    }
                } else if (this.hovered == AbstractDungeon.player) {// 124
                    if (directionIndex == 1) {// 125
                        newTarget = (AbstractCreature)sortedMonsters.get(0);// 126
                    } else {
                        newTarget = (AbstractCreature)sortedMonsters.get(sortedMonsters.size() - 1);// 128
                    }
                } else if (this.hovered instanceof AbstractMonster) {// 131
                    int currentTargetIndex = sortedMonsters.indexOf(this.hovered);// 132
                    int newTargetIndex = currentTargetIndex + directionIndex;// 133
                    if (newTargetIndex == -1) {// 135
                        newTarget = AbstractDungeon.player;// 136
                    } else {
                        newTargetIndex = (newTargetIndex + sortedMonsters.size()) % sortedMonsters.size();// 139
                        newTarget = (AbstractCreature)sortedMonsters.get(newTargetIndex);// 140
                    }
                }
            }

            if (newTarget != null) {// 147
                Hitbox target = newTarget.hb;// 148
                Gdx.input.setCursorPosition((int)target.cX, Settings.HEIGHT - (int)target.cY);// 149
                this.hovered = newTarget;// 150
                ReflectionHacks.setPrivate(AbstractDungeon.player, AbstractPlayer.class, "isUsingClickDragControl", true);// 151
                AbstractDungeon.player.isDraggingCard = true;// 152
            }

            if (this.hovered instanceof AbstractMonster && this.hovered.halfDead) {// 155
                this.hovered = null;// 156
            }
        }

    }// 159

    public void setHovered(AbstractMonster m) {
        this.hovered = m;
    }
}
