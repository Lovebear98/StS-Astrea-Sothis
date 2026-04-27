package astrea.cards.skill;

import astrea.cards.AbstractAstreaCard;
import astrea.character.SothisCharacter;
import astrea.powers.custompowers.HourglassMasteryPower;
import astrea.util.CardStats;
import astrea.util.CustomActions.CheckedSoulHeatAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static astrea.util.CustomTags.Suspend;
import static astrea.util.managers.MechanicManager.getSoulHeat;


public class BehenianHowl extends AbstractAstreaCard {
    public static final String[] EXTENDED_DESCRIPTION = CardStrings.getMockCardString().EXTENDED_DESCRIPTION;

    public static final String ID = makeID(BehenianHowl.class.getSimpleName());
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
    private static final int MAGIC = 2;
    private static final int UPG_MAGIC = 1;
    private static final int SECOND_MAGIC = 10;
    private static final int UPG_SECOND_MAGIC = 0;

    public BehenianHowl() {
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
        addToBot(new GainEnergyAction(magicNumber));
        if(getSoulHeat() >= secondMagic){
            addToBot(new ApplyPowerAction(p, p, new HourglassMasteryPower(p, 1)));
        }
        addToBot(new CheckedSoulHeatAction());
    }

    @Override
    public void triggerOnGlowCheck() {
        glowColor = BLUE_BORDER_GLOW_COLOR;
        if(getSoulHeat() >= secondMagic){
            glowColor = GOLD_BORDER_GLOW_COLOR;
        }
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new BehenianHowl();
    }
}