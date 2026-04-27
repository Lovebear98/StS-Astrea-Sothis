package astrea.cards.skill;

import astrea.cards.AbstractAstreaCard;
import astrea.character.SothisCharacter;
import astrea.util.CardStats;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.PurgeField;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.Objects;

import static astrea.util.managers.Wiz.CopyText;


public class Copycat extends AbstractAstreaCard {
    public static final String[] EXTENDED_DESCRIPTION = CardStrings.getMockCardString().EXTENDED_DESCRIPTION;

    public static final String ID = makeID(Copycat.class.getSimpleName());
    private static final CardStats info = new CardStats(
            SothisCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.RARE,
            CardTarget.SELF,
            2
    );

    private static final int DAMAGE = 0;
    private static final int UPG_DAMAGE = 0;
    private static final int BLOCK = 0;
    private static final int UPG_BLOCK = 0;
    private static final int MAGIC = 1;
    private static final int UPG_MAGIC = 0;
    private static final int SECOND_MAGIC = 0;
    private static final int UPG_SECOND_MAGIC = 0;

    public Copycat() {
        super(ID, info);
        
        setDamage(DAMAGE, UPG_DAMAGE);
        setBlock(BLOCK, UPG_BLOCK);
        setMagic(MAGIC, UPG_MAGIC);
        setSecondMagic(SECOND_MAGIC, UPG_SECOND_MAGIC);

        setCostUpgrade(1);
        PurgeField.purge.set(this, true);

        verifyBackground();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SelectCardsInHandAction(1, CopyText(), false, false, card -> card != this && !Objects.equals(card.cardID, Copycat.ID), selectedcards -> {
            if(!selectedcards.isEmpty()){
                for(AbstractCard c: selectedcards){
                    addToTop(new MakeTempCardInHandAction(c.makeStatEquivalentCopy()));
                }
            }
        }));
    }

    @Override
    public void upgrade() {
        super.upgrade();
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new Copycat();
    }
}