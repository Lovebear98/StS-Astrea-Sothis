package astrea.cards.skill;

import astrea.cards.AbstractAstreaCard;
import astrea.cardsmods.ReduceXCostMod;
import astrea.character.SothisCharacter;
import astrea.util.CardStats;
import astrea.util.CustomActions.AddCardModAction;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static astrea.util.CustomTags.Suspend;
import static astrea.util.managers.Wiz.ModifyText;


public class Inventive extends AbstractAstreaCard {
    public static final String[] EXTENDED_DESCRIPTION = CardStrings.getMockCardString().EXTENDED_DESCRIPTION;

    public static final String ID = makeID(Inventive.class.getSimpleName());
    private static final CardStats info = new CardStats(
            SothisCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.RARE,
            CardTarget.SELF,
            1
    );

    private static final int DAMAGE = 0;
    private static final int UPG_DAMAGE = 0;
    private static final int BLOCK = 0;
    private static final int UPG_BLOCK = 0;
    private static final int MAGIC = 1;
    private static final int UPG_MAGIC = 1;
    private static final int SECOND_MAGIC = 1;
    private static final int UPG_SECOND_MAGIC = 0;

    public Inventive() {
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
        addToBot(new SelectCardsInHandAction(magicNumber, ModifyText(), false, false, card -> card != this && card.cost != 0, selectedcards -> {
            if(!selectedcards.isEmpty()){

                for(AbstractCard c: selectedcards){
                    if(c.costForTurn == -1){
                        addToBot(new AddCardModAction(c, new ReduceXCostMod()));
                    }else if(c.costForTurn > 0){
                        addToBot(new AbstractGameAction() {
                            @Override
                            public void update() {
                                c.setCostForTurn(c.costForTurn-secondMagic);
                                isDone = true;
                            }
                        });
                    }
                }
            }

        }));
    }

    @Override
    public void upgrade() {
        super.upgrade();
        tags.add(Suspend);
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new Inventive();
    }
}