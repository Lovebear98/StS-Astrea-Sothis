package astrea.cards.skill;

import astrea.cards.AbstractAstreaCard;
import astrea.character.SothisCharacter;
import astrea.util.CardStats;
import astrea.util.CustomActions.RandomReliefAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;


public class SunnyFlares extends AbstractAstreaCard {
    public static final String[] EXTENDED_DESCRIPTION = CardStrings.getMockCardString().EXTENDED_DESCRIPTION;

    public static final String ID = makeID(SunnyFlares.class.getSimpleName());
    private static final CardStats info = new CardStats(
            SothisCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.ALL,
            -1
    );

    private static final int DAMAGE = 0;
    private static final int UPG_DAMAGE = 0;
    private static final int BLOCK = 0;
    private static final int UPG_BLOCK = 0;
    private static final int MAGIC = 3;
    private static final int UPG_MAGIC = 1;
    private static final int SECOND_MAGIC = 2;
    private static final int UPG_SECOND_MAGIC = 0;

    public SunnyFlares() {
        super(ID, info);
        
        setDamage(DAMAGE, UPG_DAMAGE);
        setBlock(BLOCK, UPG_BLOCK);
        setMagic(MAGIC, UPG_MAGIC);
        setSecondMagic(SECOND_MAGIC, UPG_SECOND_MAGIC);

        verifyBackground();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int e = GetXEnergy();
        for(int i = e*secondMagic; i > 0; i -= 1){
            addToBot(new RandomReliefAction(magicNumber));
        }
    }


    @Override
    public AbstractCard makeCopy() { //Optional
        return new SunnyFlares();
    }
}