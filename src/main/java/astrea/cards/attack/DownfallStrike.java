package astrea.cards.attack;

import astrea.cards.AbstractAstreaCard;
import astrea.character.SothisCharacter;
import astrea.powers.custompowers.ReliefPower;
import astrea.util.CardStats;
import astrea.util.CustomActions.AfflictAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static astrea.patches.visual.AttackEffectEnum.PURIFY;


public class DownfallStrike extends AbstractAstreaCard {
    public static final String[] EXTENDED_DESCRIPTION = CardStrings.getMockCardString().EXTENDED_DESCRIPTION;

    public static final String ID = makeID(DownfallStrike.class.getSimpleName());
    private static final CardStats info = new CardStats(
            SothisCharacter.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            0
    );

    private static final int DAMAGE = 10;
    private static final int UPG_DAMAGE = 4;
    private static final int BLOCK = 0;
    private static final int UPG_BLOCK = 0;
    private static final int MAGIC = 6;
    private static final int UPG_MAGIC = 3;
    private static final int SECOND_MAGIC = 0;
    private static final int UPG_SECOND_MAGIC = 0;

    public DownfallStrike() {
        super(ID, info);
        
        setDamage(DAMAGE, UPG_DAMAGE);
        setBlock(BLOCK, UPG_BLOCK);
        setMagic(MAGIC, UPG_MAGIC);
        setSecondMagic(SECOND_MAGIC, UPG_SECOND_MAGIC);

        tags.add(CardTags.STRIKE);

        verifyBackground();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), PURIFY));
        addToBot(new ApplyPowerAction(m, p, new ReliefPower(m, magicNumber)));
        addToBot(new AfflictAction(magicNumber));
    }


    @Override
    public AbstractCard makeCopy() { //Optional
        return new DownfallStrike();
    }
}