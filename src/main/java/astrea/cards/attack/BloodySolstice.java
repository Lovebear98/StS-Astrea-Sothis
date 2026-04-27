package astrea.cards.attack;

import astrea.cards.AbstractAstreaCard;
import astrea.character.SothisCharacter;
import astrea.patches.interfaces.KindleInterface;
import astrea.util.CardStats;
import astrea.util.CustomActions.CheckedSoulHeatAction;
import astrea.util.CustomActions.ResetSoulHeatAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static astrea.patches.visual.AttackEffectEnum.PURIFY;
import static astrea.util.CustomTags.Suspend;
import static astrea.util.managers.MechanicManager.getSoulHeat;
import static astrea.util.managers.Wiz.NoKindleText;


public class BloodySolstice extends AbstractAstreaCard {
    public static final String[] EXTENDED_DESCRIPTION = CardStrings.getMockCardString().EXTENDED_DESCRIPTION;

    public static final String ID = makeID(BloodySolstice.class.getSimpleName());
    private static final CardStats info = new CardStats(
            SothisCharacter.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.COMMON,
            CardTarget.ENEMY,
            3
    );

    private static final int DAMAGE = 70;
    private static final int UPG_DAMAGE = 30;
    private static final int BLOCK = 0;
    private static final int UPG_BLOCK = 0;
    private static final int MAGIC = 0;
    private static final int UPG_MAGIC = 0;
    private static final int SECOND_MAGIC = 10;
    private static final int UPG_SECOND_MAGIC = 0;

    public BloodySolstice() {
        super(ID, info);
        
        setDamage(DAMAGE, UPG_DAMAGE);
        setBlock(BLOCK, UPG_BLOCK);
        setMagic(MAGIC, UPG_MAGIC);
        setSecondMagic(SECOND_MAGIC, UPG_SECOND_MAGIC);

        tags.add(Suspend);

        verifyBackground();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(getSoulHeat() >= secondMagic){
            addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), PURIFY));
        }
        addToBot(new CheckedSoulHeatAction());
        addToBot(new ResetSoulHeatAction());
    }

    @Override
    public void triggerOnGlowCheck() {
        this.glowColor = BLUE_BORDER_GLOW_COLOR;
        if(getSoulHeat() >= secondMagic){
            glowColor = GOLD_BORDER_GLOW_COLOR;
        }
    }


    @Override
    public AbstractCard makeCopy() { //Optional
        return new BloodySolstice();
    }
}