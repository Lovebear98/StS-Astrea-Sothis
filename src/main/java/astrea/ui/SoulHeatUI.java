package astrea.ui;

import astrea.util.CustomActions.CustomGameEffects.SoulHeatFireEffect;
import astrea.util.TextureLoader;
import basemod.ClickableUIElement;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.localization.UIStrings;

import static astrea.AstreaMod.makeID;
import static astrea.character.SothisOrb.*;
import static astrea.util.managers.MechanicManager.SoulHeat;
import static astrea.util.managers.MechanicManager.SoulHeatCap;
import static astrea.util.managers.SoundManager.HOVERHEATUIKEY;
import static astrea.util.managers.SoundManager.PlaySound;


@SuppressWarnings("unused")
public class SoulHeatUI extends ClickableUIElement{
    UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(makeID(SoulHeatUI.class.getSimpleName()));

    public static boolean enabled = true;
    public static float heatTimer = 0.05f;
    public static float heatTimerReset = 0.025f;

    public static final Texture HitboxSizeTex = TextureLoader.getTexture("astrea/images/ui/HBSize.png");


    public SoulHeatUI(){
        super(HitboxSizeTex,0, 0,w,h);
        setClickable(false);

        this.hitbox.resize(w, h);
        UpdateHitbox();
    }

    private void UpdateHitbox() {
        this.hitbox.move(Orb_x+(Orbw * 1.1f), Orb_y+(Orbh * 1.1f));
    }

    @Override
    public void render(SpriteBatch sb,Color color){

        ///Start rendering
        sb.end();
        Gdx.gl.glColorMask(true,true,true,true);
        sb.begin();
        sb.setColor(Color.WHITE.cpy());

        if(SoulHeat > 0){
            FontHelper.renderFontCentered(sb, FontHelper.blockInfoFont, String.valueOf(SoulHeat), this.hitbox.cX, this.hitbox.cY, Color.CYAN.cpy(), 1.25f);
        }

        sb.setColor(Color.WHITE.cpy());
        UpdateHitbox();
        this.hitbox.render(sb);
    }


    @Override
    protected void onHover(){
            if(!AbstractDungeon.isScreenUp && SoulHeat > 0){
                RenderTooltip(GetTitle(), GetDesc());
                if(this.hitbox.justHovered){
                    PlaySound(HOVERHEATUIKEY);
                }
            }
    }
    @Override
    protected void onUnhover(){

    }



    @Override
    public void update() {
        super.update();

        UpdateHitbox();
        if(SoulHeat > 0){
                heatTimer = heatTimerReset;
                AbstractDungeon.effectsQueue.add(new SoulHeatFireEffect(this.hitbox.cX, this.hitbox.cY));

        }
    }


    private void RenderTooltip(String Name, String Desc){
        TipHelper.renderGenericTip(this.hitbox.cX, this.hitbox.cY + ( this.hitbox.height * 1.8f), Name, Desc);
    }

    @Override
    protected void updateHitbox() {
        super.updateHitbox();
    }

    @Override
    protected void onClick(){
    }


    public static void drawSpinning(SpriteBatch sb, Texture texture, float x, float y, float rotation) {
        sb.draw(texture, x, y, (texture.getWidth() * SizeCorrect * 0.5f), (texture.getHeight() * SizeCorrect * 0.5f), HitboxSizeTex.getWidth()*SizeCorrect, HitboxSizeTex.getHeight()*SizeCorrect, 1f, 1f, rotation, 0, 0, texture.getWidth(), texture.getHeight(), false, false);
    }


    ///Fixes sizes
    public static final float SizeCorrect = (1.4f*Settings.scale);
    ///The size of the UI
    private static final float w = HitboxSizeTex.getWidth() * SizeCorrect;
    private static final float h = HitboxSizeTex.getHeight() * SizeCorrect;

    /// Stuff to make the tooltip text!
    private String GetTitle(){
        return uiStrings.TEXT[0];
    }
    private String GetDesc(){
        return uiStrings.TEXT[1]+SoulHeatCap()+uiStrings.TEXT[2];
    }
}