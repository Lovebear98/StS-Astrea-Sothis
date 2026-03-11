package astrea.character;

import astrea.util.TextureLoader;
import basemod.abstracts.CustomEnergyOrb;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.core.Settings;

import static astrea.util.managers.Wiz.isInCombat;


public class SothisOrb extends CustomEnergyOrb {

    ///This corrects sizes to scale appropriately, don't ask questions.
    public static final float SizeCorrect = (1.4f* Settings.scale);

    ///Textures!
    public static final Texture Background = TextureLoader.getTexture("astrea/images/character/orb/Background.png");
    public static final Texture Stars = TextureLoader.getTexture("astrea/images/character/orb/Star.png");
    private static final Texture FX = TextureLoader.getTexture("astrea/images/character/orb/VFX.png");



    ///The size of any given UI element, since it's all large sized
    public static final float Orbw = Background.getWidth() * SizeCorrect;
    public static final float Orbh = Background.getHeight() * SizeCorrect;

    private boolean hasEnergy = true;

    public static float Orb_x = 0;
    public static float Orb_y = 0;

    private float Rotation = 0f;

    private boolean Slowing = false;
    private boolean Speedup = true;
    private float RotSpeed = 0f;


    boolean Pause = true;
    private float lurchTimer = 0.25f;
    private float pauseTimer = 0.15f;
    private float targetangle = 0f;

    public SothisOrb(String[] orbTexturePaths, String orbVfxPath, float[] layerSpeeds) {
        super(orbTexturePaths, orbVfxPath, layerSpeeds);
        orbVfx = FX;
    }
    public SothisOrb(){
        this(null, null, null);
    }

    @Override
    public void updateOrb(int energyCount) {
        hasEnergy = energyCount > 0;

        if(isInCombat()){
            if(hasEnergy){
                if(Pause){
                    pauseTimer -= Gdx.graphics.getDeltaTime();
                    if(pauseTimer <= 0f){
                        lurchTimer = 1f;
                        Pause = false;
                        targetangle = Rotation + 22.5f;
                    }
                }else{
                    Rotation = Interpolation.linear.apply(Rotation, targetangle, lurchTimer*2);
                    lurchTimer -= Gdx.graphics.getDeltaTime();
                    if(lurchTimer <= 0f){
                        pauseTimer = 0.05f;
                        Pause = true;
                    }
                }
            }

        }
    }

    @Override
    public void renderOrb(SpriteBatch sb, boolean enabled, float current_x, float current_y) {


        Orb_x = current_x - (Orbw * 0.5f);
        Orb_y = current_y - (Orbh * 0.5f);
        if(isInCombat()){
            sb.end();
            sb.begin();




            if(!hasEnergy){
                sb.setColor(Color.GRAY.cpy());
            }
            float angle = 0;
            for(int l = 8; l > 0; l -= 1){
                renderCenteredScaled(sb, Stars, Orb_x, Orb_y, Rotation + angle);
                angle += 45f;
            }

            sb.setColor(Color.WHITE.cpy());

            renderCenteredScaled(sb, Background, Orb_x, Orb_y, 0);

        }
        sb.setColor(Color.WHITE.cpy());
    }


    private static void renderCenteredScaled(SpriteBatch sb, Texture texture, float x, float y, float rotation) {
        sb.draw(texture, x, y, (texture.getWidth() * SizeCorrect * 0.5f), (texture.getHeight() * SizeCorrect * 0.5f), texture.getWidth()*SizeCorrect, texture.getHeight()*SizeCorrect, 1f, 1f, rotation, 0, 0, texture.getWidth(), texture.getHeight(), false, false);
    }

    private void AdjustSpin(float rate) {
        if(false){
            Rotation -= 0.4f * rate;
        }else{
            Rotation += 0.4f * rate;
        }
    }
}
