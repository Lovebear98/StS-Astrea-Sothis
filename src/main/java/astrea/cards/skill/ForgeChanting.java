package astrea.cards.skill;

import astrea.cards.AbstractAstreaCard;
import astrea.cardsmods.AppliedChantingDamageMod;
import astrea.character.SothisCharacter;
import astrea.util.CardStats;
import basemod.helpers.CardModifierManager;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static astrea.util.CustomTags.Chanting;
import static astrea.util.managers.Wiz.ModifyText;


public class ForgeChanting extends AbstractAstreaCard {
    public static final String[] EXTENDED_DESCRIPTION = CardStrings.getMockCardString().EXTENDED_DESCRIPTION;

    public static final String ID = makeID(ForgeChanting.class.getSimpleName());
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

    public ForgeChanting() {
        super(ID, info);
        
        setDamage(DAMAGE, UPG_DAMAGE);
        setBlock(BLOCK, UPG_BLOCK);
        setMagic(MAGIC, UPG_MAGIC);
        setSecondMagic(SECOND_MAGIC, UPG_SECOND_MAGIC);
        setCostUpgrade(1);
        setExhaust(true);

        verifyBackground();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SelectCardsInHandAction(magicNumber, ModifyText(), false, false, card -> card != this && card.type == CardType.ATTACK && !card.hasTag(Chanting), selectedcards -> {
            if(!selectedcards.isEmpty()){
                for(AbstractCard c: selectedcards){
                    addToBot(new AbstractGameAction() {
                        @Override
                        public void update() {
                            c.tags.add(Chanting);
                            CardModifierManager.addModifier(c, new AppliedChantingDamageMod());
                            isDone = true;
                        }
                    });
                }
            }

        }));
    }

    @Override
    public void triggerOnGlowCheck() {
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new ForgeChanting();
    }
}