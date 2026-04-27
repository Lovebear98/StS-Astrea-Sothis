package astrea.cards.skill;

import astrea.cards.AbstractAstreaCard;
import astrea.character.SothisCharacter;
import astrea.util.CardStats;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static astrea.util.CustomTags.Suspend;
import static astrea.util.managers.MechanicManager.getSoulHeat;


public class Incorruptible extends AbstractAstreaCard {
    public static final String[] EXTENDED_DESCRIPTION = CardStrings.getMockCardString().EXTENDED_DESCRIPTION;

    public static final String ID = makeID(Incorruptible.class.getSimpleName());
    private static final CardStats info = new CardStats(
            SothisCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            3
    );

    private static final int DAMAGE = 0;
    private static final int UPG_DAMAGE = 0;
    private static final int BLOCK = 20;
    private static final int UPG_BLOCK = 6;
    private static final int MAGIC = 0;
    private static final int UPG_MAGIC = 0;
    private static final int SECOND_MAGIC = 10;
    private static final int UPG_SECOND_MAGIC = 0;

    public Incorruptible() {
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
        addToBot(new GainBlockAction(p, block));
    }

    @Override
    public boolean freeToPlay() {
        if(getSoulHeat() >= secondMagic){
            return true;
        }
        return super.freeToPlay();
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new Incorruptible();
    }

    @Override
    public void triggerOnGlowCheck() {
        glowColor = BLUE_BORDER_GLOW_COLOR;
        if(getSoulHeat() >= secondMagic){
            glowColor = GOLD_BORDER_GLOW_COLOR;
        }
    }
}