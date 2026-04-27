package astrea.cards.skill;

import astrea.cards.AbstractAstreaCard;
import astrea.character.SothisCharacter;
import astrea.util.CardStats;
import astrea.util.CustomActions.AfflictAction;
import astrea.util.CustomActions.TriggerReliefAction;
import astrea.util.CustomActions.generic.DelayAction;
import astrea.util.targeting.SothisSelfOrEnemyTargeting;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static astrea.util.managers.Wiz.p;
import static astrea.util.targeting.SothisSelfOrEnemyTargeting.RELIEF_TARGET;


public class SelflessIgnite extends AbstractAstreaCard {
    public static final String[] EXTENDED_DESCRIPTION = CardStrings.getMockCardString().EXTENDED_DESCRIPTION;

    public static final String ID = makeID(SelflessIgnite.class.getSimpleName());
    private static final CardStats info = new CardStats(
            SothisCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.RARE,
            RELIEF_TARGET,
            0
    );

    private static final int DAMAGE = 0;
    private static final int UPG_DAMAGE = 0;
    private static final int BLOCK = 0;
    private static final int UPG_BLOCK = 0;
    private static final int MAGIC = 0;
    private static final int UPG_MAGIC = 0;
    private static final int SECOND_MAGIC = 6;
    private static final int UPG_SECOND_MAGIC = 0;

    public SelflessIgnite() {
        super(ID, info);
        
        setDamage(DAMAGE, UPG_DAMAGE);
        setBlock(BLOCK, UPG_BLOCK);
        setMagic(MAGIC, UPG_MAGIC);
        setSecondMagic(SECOND_MAGIC, UPG_SECOND_MAGIC);

        verifyBackground();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractCreature target = SothisSelfOrEnemyTargeting.getTarget(this);
        if (target == null){
            target = p();
        }
        addToBot(new TriggerReliefAction(target, true));

        if(!upgraded){
            addToBot(new DelayAction(new AfflictAction(secondMagic)));
        }
    }


    @Override
    public AbstractCard makeCopy() { //Optional
        return new SelflessIgnite();
    }
}