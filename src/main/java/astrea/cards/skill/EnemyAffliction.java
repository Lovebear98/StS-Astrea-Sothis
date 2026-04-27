package astrea.cards.skill;

import astrea.cards.AbstractAstreaCard;
import astrea.character.SothisCharacter;
import astrea.util.CardStats;
import astrea.util.CustomActions.AfflictAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;

import static astrea.util.managers.MechanicManager.getSoulHeat;
import static astrea.util.managers.Wiz.p;


public class EnemyAffliction extends AbstractAstreaCard {
    public static final String[] EXTENDED_DESCRIPTION = CardStrings.getMockCardString().EXTENDED_DESCRIPTION;

    public static final String ID = makeID(EnemyAffliction.class.getSimpleName());
    private static final CardStats info = new CardStats(
            SothisCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            0
    );

    private static final int DAMAGE = 0;
    private static final int UPG_DAMAGE = 0;
    private static final int BLOCK = 0;
    private static final int UPG_BLOCK = 0;
    private static final int MAGIC = 3;
    private static final int UPG_MAGIC = 0;
    private static final int SECOND_MAGIC = 8;
    private static final int UPG_SECOND_MAGIC = -2;

    public EnemyAffliction() {
        super(ID, info);
        
        setDamage(DAMAGE, UPG_DAMAGE);
        setBlock(BLOCK, UPG_BLOCK);
        setMagic(MAGIC, UPG_MAGIC);
        setSecondMagic(SECOND_MAGIC, UPG_SECOND_MAGIC);

        setExhaust(true);

        verifyBackground();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(m, p(), new VulnerablePower(m, magicNumber, false)));
        addToBot(new AfflictAction(secondMagic));
    }


    @Override
    public void applyPowers() {
        super.applyPowers();
        if(p().hand.contains(this) && getSoulHeat() > 0){
            if(this.backupDescription == null){
                this.backupDescription = this.rawDescription;
            }
            this.rawDescription = GetExtraString();
            this.initializeDescription();
        }else{
            if(this.backupDescription != null){
                this.rawDescription = backupDescription;
                this.backupDescription = null;
            }
            this.initializeDescription();
        }

    }
    private String GetExtraString() {
        if(magicNumber > 0){
            return cardStrings.EXTENDED_DESCRIPTION[1];
        }
        return cardStrings.EXTENDED_DESCRIPTION[0];
    }


    @Override
    public AbstractCard makeCopy() { //Optional
        return new EnemyAffliction();
    }
}