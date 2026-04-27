package astrea.cards.skill;

import astrea.cards.AbstractAstreaCard;
import astrea.character.SothisCharacter;
import astrea.powers.custompowers.BrightEnhancementPower;
import astrea.powers.custompowers.ReliefPower;
import astrea.util.CardStats;
import astrea.util.CustomActions.AfflictAction;
import astrea.util.managers.Wiz;
import astrea.util.targeting.SothisSelfOrEnemyTargeting;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static astrea.util.CustomTags.Suspend;
import static astrea.util.managers.MechanicManager.getSoulHeat;
import static astrea.util.managers.Wiz.p;
import static astrea.util.managers.Wiz.textToFloat;
import static astrea.util.targeting.SothisSelfOrEnemyTargeting.RELIEF_TARGET;


public class BrightEnhancement extends AbstractAstreaCard {
    public static final String[] EXTENDED_DESCRIPTION = CardStrings.getMockCardString().EXTENDED_DESCRIPTION;

    public static final String ID = makeID(BrightEnhancement.class.getSimpleName());
    private static final CardStats info = new CardStats(
            SothisCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.RARE,
            RELIEF_TARGET,
            2
    );

    private static final int DAMAGE = 0;
    private static final int UPG_DAMAGE = 0;
    private static final int BLOCK = 0;
    private static final int UPG_BLOCK = 0;
    private static final int MAGIC = 1;
    private static final int UPG_MAGIC = 0;
    private static final int SECOND_MAGIC = 30;
    private static final int UPG_SECOND_MAGIC = 20;

    public BrightEnhancement() {
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
        AbstractCreature target = SothisSelfOrEnemyTargeting.getTarget(this);
        if (target == null){
            target = p();
        }

        AbstractPower pow = target.getPower(ReliefPower.POWER_ID);
        if(pow != null){
            int newnum = (int) (pow.amount * Wiz.textToFloat(secondMagic));
            addToBot(new ApplyPowerAction(target, p, new ReliefPower(target, newnum)));
        }
        addToBot(new ApplyPowerAction(target, p, new BrightEnhancementPower(target, magicNumber)));

    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new BrightEnhancement();
    }
}