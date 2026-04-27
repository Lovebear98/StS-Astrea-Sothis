package astrea.ui;

import astrea.patches.suspendpile.ExhaustPileViewScreenPatches;
import astrea.util.TextureLoader;
import astrea.util.managers.Wiz;
import basemod.ClickableUIElement;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.localization.UIStrings;

import static astrea.AstreaMod.makeID;
import static astrea.character.SothisOrb.SizeCorrect;
import static astrea.patches.UIPatch.Pile_Orb_x;
import static astrea.patches.UIPatch.Pile_Orb_y;
import static astrea.util.managers.MechanicManager.SuspendPile;
import static astrea.util.managers.SoundManager.PlaySound;
import static astrea.util.managers.Wiz.timeScale;


@SuppressWarnings("unused")
public class SuspendPanel extends ClickableUIElement{
    UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(makeID(SuspendPanel.class.getSimpleName()));

    public static final Texture SuspendPileTex = TextureLoader.getTexture("astrea/images/ui/SuspendPile.png");

    private float SuspendScale = 1f;
    private float BigSuspendScale = 1.25f;
    private float LerpTimer = 1f;
    private float GrowTime = 0.05f;
    private float ShrinkTime = 0.1f;

    public static float Suspend_x = 0;
    public static float Suspend_y = 0;

    public SuspendPanel(){
        super(SuspendPileTex,0, 0,w,h);
        setClickable(true);

        this.hitbox.resize(w, h);
    }

    @Override
    public void render(SpriteBatch sb,Color color){
        if(!SuspendPile.isEmpty()){
            ///Start rendering
            sb.end();
            Gdx.gl.glColorMask(true,true,true,true);
            sb.begin();
            sb.setColor(Color.WHITE.cpy());
            Wiz.renderCenteredScaled(sb, SuspendPileTex, x, y, SuspendScale, 0);

            FontHelper.renderFontCentered(sb, FontHelper.blockInfoFont, String.valueOf(SuspendPile.size()), x + (w * 0.5f), y + (h * 0.4f), Color.WHITE.cpy(), 1.5f);

            sb.setColor(Color.WHITE.cpy());
            this.hitbox.render(sb);
        }
    }


    @Override
    protected void onHover(){
        if(!AbstractDungeon.isScreenUp && !SuspendPile.isEmpty()){
            RenderTooltip(GetTitle(), GetDesc());
            if(this.hitbox.justHovered){
                PlaySound("UI_HOVER");
                LerpTimer = GrowTime;
            }
        }
    }
    @Override
    protected void onUnhover(){
        LerpTimer = ShrinkTime;
    }



    @Override
    public void update() {
        super.update();

        this.x = Pile_Orb_x - (130f * Settings.scale) - w * 0.5f;
        this.y = Pile_Orb_y + (140f * Settings.scale) - w * 0.5f;
        Suspend_x = x + (w * 0.5f);
        Suspend_y = y + (h * 0.5f);
        this.hitbox.move(x+(w * 0.5f), y+(h * 0.5f));


        if(LerpTimer > 0){
            LerpTimer -= Gdx.graphics.getDeltaTime();
            if(LerpTimer <= 0){
                LerpTimer = 0;
            }
            if(this.hitbox.hovered){
                SuspendScale = MathUtils.lerp(SuspendScale, BigSuspendScale, timeScale(LerpTimer, GrowTime));
            }else{
                SuspendScale = MathUtils.lerp(SuspendScale, 1f, timeScale(LerpTimer, ShrinkTime));
            }
        }
    }


    private void RenderTooltip(String Name, String Desc){
        TipHelper.renderGenericTip(this.hitbox.cX + this.hitbox.width * 0.30f, this.hitbox.cY + this.hitbox.height * 0.55f, Name, Desc);
    }

    @Override
    protected void updateHitbox() {
        super.updateHitbox();
    }

    @Override
    protected void onClick(){
        if(!AbstractDungeon.isScreenUp && !SuspendPile.isEmpty()){
            PlaySound("UI_CLICK");
            ExhaustPileViewScreenPatches.showSuspendCards = true;
            AbstractDungeon.exhaustPileViewScreen.open();
        }
    }



    ///The size of the UI
    private static final float w = SuspendPileTex.getWidth() * SizeCorrect;
    private static final float h = SuspendPileTex.getHeight() * SizeCorrect;

    /// Stuff to make the tooltip text!
    private String GetTitle(){
        return uiStrings.TEXT[0];
    }
    private String GetDesc(){
        return uiStrings.TEXT[1];
    }


}