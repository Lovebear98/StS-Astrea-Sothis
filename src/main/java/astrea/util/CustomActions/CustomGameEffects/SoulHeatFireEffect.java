//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package astrea.util.CustomActions.CustomGameEffects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

import static astrea.util.managers.MechanicManager.soulHeatColor;

public class SoulHeatFireEffect extends AbstractGameEffect {
    private float x;
    private float y;
    private float vY;
    private TextureAtlas.AtlasRegion img;

    public SoulHeatFireEffect(float x, float y) {
        this.duration = MathUtils.random(1F, 2.0F);// 26
        this.startingDuration = this.duration;// 27
        this.img = this.getImg();// 28
        this.x = x - (float)(this.img.packedWidth / 2) + MathUtils.random(-3.0F, 3.0F) * Settings.scale;// 29
        this.y = y - (float)(this.img.packedHeight / 2);// 30
        this.scale = Settings.scale * MathUtils.random(2.0F, 3.0F);// 31
        this.vY = MathUtils.random(1.0F, 10.0F);// 32
        this.vY *= this.vY * Settings.scale;// 33
        this.rotation = MathUtils.random(-20.0F, 20.0F);// 34

        this.color = soulHeatColor();
        this.color.a = 0.01f;

       /// this.renderBehind = true;
    }// 50

    private TextureAtlas.AtlasRegion getImg() {
        switch (MathUtils.random(0, 2)) {// 53
            case 0:
                return ImageMaster.TORCH_FIRE_1;// 55
            case 1:
                return ImageMaster.TORCH_FIRE_2;// 57
            default:
                return ImageMaster.TORCH_FIRE_3;// 59
        }
    }

    public void update() {
        this.duration -= Gdx.graphics.getDeltaTime();// 64
        if (this.duration < 0.0F) {// 65
            this.isDone = true;// 66
        }

        this.color.a = Interpolation.fade.apply(0.0F, 0.75F, this.duration / this.startingDuration);// 68
        this.y += this.vY * Gdx.graphics.getDeltaTime();// 69
    }// 70

    public void render(SpriteBatch sb) {
        sb.setBlendFunction(770, 1);// 74
        sb.setColor(this.color);// 75
        sb.draw(this.img, this.x, this.y, (float)this.img.packedWidth / 2.0F, (float)this.img.packedHeight / 2.0F, (float)this.img.packedWidth, (float)this.img.packedHeight, this.scale, this.scale, this.rotation);// 76
        sb.setBlendFunction(770, 771);// 87
    }// 88

    public void dispose() {
    }// 93
}
