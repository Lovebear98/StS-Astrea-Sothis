package astrea.cards.skill;

import astrea.cards.AbstractAstreaCard;
import astrea.character.SothisCharacter;
import astrea.util.CardStats;
import astrea.util.CustomActions.SuspendCardAction;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import static astrea.util.CustomTags.Suspend;
import static astrea.util.managers.Wiz.ModifyText;


public class AstralRebound extends AbstractAstreaCard {
    public static final String[] EXTENDED_DESCRIPTION = CardStrings.getMockCardString().EXTENDED_DESCRIPTION;

    public static final String ID = makeID(AstralRebound.class.getSimpleName());
    private static final CardStats info = new CardStats(
            SothisCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.SELF,
            0
    );

    private static final int DAMAGE = 0;
    private static final int UPG_DAMAGE = 0;
    private static final int BLOCK = 0;
    private static final int UPG_BLOCK = 0;
    private static final int MAGIC = 1;
    private static final int UPG_MAGIC = 0;
    private static final int SECOND_MAGIC = 0;
    private static final int UPG_SECOND_MAGIC = 0;

    public AstralRebound() {
        super(ID, info);
        
        setDamage(DAMAGE, UPG_DAMAGE);
        setBlock(BLOCK, UPG_BLOCK);
        setMagic(MAGIC, UPG_MAGIC);
        setSecondMagic(SECOND_MAGIC, UPG_SECOND_MAGIC);
        setExhaust(true, false);
        if(upgraded){
            tags.add(Suspend);
        }
        verifyBackground();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SelectCardsInHandAction(magicNumber, ModifyText(), false, false, card -> card != this, selectedcards -> {
            if(!selectedcards.isEmpty()){
                int energy = 0;

                for(AbstractCard c: selectedcards){
                    addToBot(new SuspendCardAction(c, p.hand));

                    if(c.costForTurn == -1){
                        energy += EnergyPanel.totalCount;
                    }else if(c.costForTurn > 0){
                        energy += c.costForTurn;
                    }

                    if(energy > 0){
                        addToBot(new GainEnergyAction(energy));
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
        return new AstralRebound();
    }
}