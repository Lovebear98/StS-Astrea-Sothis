package astrea.cards.skill;

import astrea.cards.AbstractAstreaCard;
import astrea.character.SothisCharacter;
import astrea.util.CardStats;
import astrea.util.CustomActions.CheckedSoulHeatAction;
import com.megacrit.cardcrawl.actions.common.BetterDrawPileToHandAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.EmptyDeckShuffleAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static astrea.util.CustomTags.Suspend;
import static astrea.util.managers.MechanicManager.getSoulHeat;


public class AstralMantra extends AbstractAstreaCard {
    public static final String[] EXTENDED_DESCRIPTION = CardStrings.getMockCardString().EXTENDED_DESCRIPTION;

    public static final String ID = makeID(AstralMantra.class.getSimpleName());
    private static final CardStats info = new CardStats(
            SothisCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            0
    );

    private static final int DAMAGE = 0;
    private static final int UPG_DAMAGE = 0;
    private static final int BLOCK = 0;
    private static final int UPG_BLOCK = 0;
    private static final int MAGIC = 1;
    private static final int UPG_MAGIC = 0;
    private static final int SECOND_MAGIC = 7;
    private static final int UPG_SECOND_MAGIC = -2;

    public AstralMantra() {
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
            if(p.drawPile.isEmpty() && !p.discardPile.isEmpty()){
                addToBot(new EmptyDeckShuffleAction());
            }
            addToBot(new BetterDrawPileToHandAction(magicNumber));
        }else{
            addToBot(new DrawCardAction(magicNumber));
        }

        addToBot(new CheckedSoulHeatAction());
    }

    @Override
    public void triggerOnGlowCheck() {
        glowColor = BLUE_BORDER_GLOW_COLOR.cpy();
        if(getSoulHeat() >= secondMagic){
            glowColor = GOLD_BORDER_GLOW_COLOR.cpy();
        }
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new AstralMantra();
    }
}