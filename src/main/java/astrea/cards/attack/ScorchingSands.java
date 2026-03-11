package astrea.cards.attack;

import astrea.cards.AbstractAstreaCard;
import astrea.cardsmods.SoulHeatAsDamageMod;
import astrea.character.SothisCharacter;
import astrea.util.CardStats;
import astrea.util.CustomActions.CheckedSoulHeatAction;
import astrea.util.CustomActions.ResetSoulHeatAction;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static astrea.util.managers.MechanicManager.getSoulHeat;
import static astrea.util.managers.Wiz.p;


public class ScorchingSands extends AbstractAstreaCard {
    public static final String[] EXTENDED_DESCRIPTION = CardStrings.getMockCardString().EXTENDED_DESCRIPTION;

    public static final String ID = makeID(ScorchingSands.class.getSimpleName());
    private static final CardStats info = new CardStats(
            SothisCharacter.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.RARE,
            CardTarget.ENEMY,
            1
    );

    private static final int DAMAGE = 0;
    private static final int UPG_DAMAGE = 0;
    private static final int BLOCK = 0;
    private static final int UPG_BLOCK = 0;
    private static final int MAGIC = 2;
    private static final int UPG_MAGIC = 1;
    private static final int SECOND_MAGIC = 0;
    private static final int UPG_SECOND_MAGIC = 0;

    public ScorchingSands() {
        super(ID, info);
        
        setDamage(DAMAGE, UPG_DAMAGE);
        setBlock(BLOCK, UPG_BLOCK);
        setMagic(MAGIC, UPG_MAGIC);
        setSecondMagic(SECOND_MAGIC, UPG_SECOND_MAGIC);

        CardModifierManager.addModifier(this, new SoulHeatAsDamageMod());

        verifyBackground();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(getSoulHeat() > 0){
            addToBot(new ResetSoulHeatAction());
            for(int e = magicNumber; e > 0; e -= 1){
                addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
            }
        }
        addToBot(new CheckedSoulHeatAction());
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
        return cardStrings.EXTENDED_DESCRIPTION[0];
    }


    @Override
    public AbstractCard makeCopy() { //Optional
        return new ScorchingSands();
    }
}