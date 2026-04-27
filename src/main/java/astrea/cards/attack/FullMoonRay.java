package astrea.cards.attack;

import astrea.cards.AbstractAstreaCard;
import astrea.cardsmods.ChantingDamageMod;
import astrea.character.SothisCharacter;
import astrea.util.CardStats;
import astrea.util.CustomTags;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static astrea.patches.visual.AttackEffectEnum.PURIFY;


public class FullMoonRay extends AbstractAstreaCard {
    public static final String[] EXTENDED_DESCRIPTION = CardStrings.getMockCardString().EXTENDED_DESCRIPTION;

    public static final String ID = makeID(FullMoonRay.class.getSimpleName());
    private static final CardStats info = new CardStats(
            SothisCharacter.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.UNCOMMON,
            CardTarget.ALL_ENEMY,
            2
    );

    private static final int DAMAGE = 16;
    private static final int UPG_DAMAGE = 6;
    private static final int BLOCK = 0;
    private static final int UPG_BLOCK = 0;
    private static final int MAGIC = 0;
    private static final int UPG_MAGIC = 0;
    private static final int SECOND_MAGIC = 0;
    private static final int UPG_SECOND_MAGIC = 0;

    public FullMoonRay() {
        super(ID, info);

        setDamage(DAMAGE, UPG_DAMAGE);
        setBlock(BLOCK, UPG_BLOCK);
        setMagic(MAGIC, UPG_MAGIC);
        setSecondMagic(SECOND_MAGIC, UPG_SECOND_MAGIC);

        isMultiDamage = true;

        verifyBackground();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAllEnemiesAction(p, multiDamage, DamageInfo.DamageType.NORMAL, PURIFY));
    }


    @Override
    public AbstractCard makeCopy() { //Optional
        return new FullMoonRay();
    }
}