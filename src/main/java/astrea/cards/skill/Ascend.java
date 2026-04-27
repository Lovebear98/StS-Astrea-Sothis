package astrea.cards.skill;

import astrea.cards.AbstractAstreaCard;
import astrea.character.SothisCharacter;
import astrea.powers.custompowers.AscendPower;
import astrea.util.CardStats;
import astrea.util.CustomActions.RandomReliefAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static astrea.util.CustomTags.Suspend;
import static astrea.util.managers.MechanicManager.getSoulHeat;
import static astrea.util.managers.Wiz.p;


public class Ascend extends AbstractAstreaCard {
    public static final String[] EXTENDED_DESCRIPTION = CardStrings.getMockCardString().EXTENDED_DESCRIPTION;

    public static final String ID = makeID(Ascend.class.getSimpleName());
    private static final CardStats info = new CardStats(
            SothisCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.ALL,
            -1
    );

    private static final int DAMAGE = 0;
    private static final int UPG_DAMAGE = 0;
    private static final int BLOCK = 0;
    private static final int UPG_BLOCK = 0;
    private static final int MAGIC = 0;
    private static final int UPG_MAGIC = 1;
    private static final int SECOND_MAGIC = 0;
    private static final int UPG_SECOND_MAGIC = 0;

    public Ascend() {
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
        int e = GetXEnergy();
        addToBot(new ApplyPowerAction(p, p, new AscendPower(p, e+magicNumber)));
    }


    @Override
    public void applyPowers() {
        super.applyPowers();
        if(p().hand.contains(this) && magicNumber > 0){
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
            return cardStrings.EXTENDED_DESCRIPTION[0];
        }else{
            if(upgraded){
                return cardStrings.UPGRADE_DESCRIPTION;
            }else{
                return cardStrings.DESCRIPTION;
            }
        }
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new Ascend();
    }
}