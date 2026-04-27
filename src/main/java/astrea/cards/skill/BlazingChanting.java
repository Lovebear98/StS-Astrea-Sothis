package astrea.cards.skill;

import astrea.cards.AbstractAstreaCard;
import astrea.cardsmods.ChantingDamageMod;
import astrea.character.SothisCharacter;
import astrea.powers.custompowers.ChantingPower;
import astrea.util.CardStats;
import astrea.util.CustomTags;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static astrea.util.managers.Wiz.p;


public class BlazingChanting extends AbstractAstreaCard {
    public static final String[] EXTENDED_DESCRIPTION = CardStrings.getMockCardString().EXTENDED_DESCRIPTION;

    public static final String ID = makeID(BlazingChanting.class.getSimpleName());
    private static final CardStats info = new CardStats(
            SothisCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            1
    );

    private static final int DAMAGE = 0;
    private static final int UPG_DAMAGE = 0;
    private static final int BLOCK = 7;
    private static final int UPG_BLOCK = 3;
    private static final int MAGIC = 1;
    private static final int UPG_MAGIC = 1;
    private static final int SECOND_MAGIC = 0;
    private static final int UPG_SECOND_MAGIC = 0;

    public BlazingChanting() {
        super(ID, info);

        setDamage(DAMAGE, UPG_DAMAGE);
        setBlock(BLOCK, UPG_BLOCK);
        setMagic(MAGIC, UPG_MAGIC);
        setSecondMagic(SECOND_MAGIC, UPG_SECOND_MAGIC);

        tags.add(CustomTags.Chanting);
        CardModifierManager.addModifier(this, new ChantingDamageMod());

        verifyBackground();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, block));
        addToBot(new ApplyPowerAction(p(), p(), new ChantingPower(p(), magicNumber)));
    }


    @Override
    public AbstractCard makeCopy() { //Optional
        return new BlazingChanting();
    }
}