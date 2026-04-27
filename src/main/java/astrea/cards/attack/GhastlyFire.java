package astrea.cards.attack;

import astrea.cards.AbstractAstreaCard;
import astrea.cardsmods.PlayerReliefDamageMod;
import astrea.character.SothisCharacter;
import astrea.util.CardStats;
import astrea.util.CustomActions.PurifyFollowupAction;
import astrea.util.CustomActions.TriggerReliefAction;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static astrea.patches.visual.AttackEffectEnum.PURIFY;
import static astrea.util.managers.MechanicManager.getSoulHeat;
import static astrea.util.managers.Wiz.p;


public class GhastlyFire extends AbstractAstreaCard {
    public static final String[] EXTENDED_DESCRIPTION = CardStrings.getMockCardString().EXTENDED_DESCRIPTION;

    public static final String ID = makeID(GhastlyFire.class.getSimpleName());
    private static final CardStats info = new CardStats(
            SothisCharacter.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.COMMON,
            CardTarget.ENEMY,
            2
    );

    private static final int DAMAGE = 0;
    private static final int UPG_DAMAGE = 0;
    private static final int BLOCK = 0;
    private static final int UPG_BLOCK = 0;
    private static final int MAGIC = 0;
    private static final int UPG_MAGIC = 0;
    private static final int SECOND_MAGIC = 0;
    private static final int UPG_SECOND_MAGIC = 0;

    public GhastlyFire() {
        super(ID, info);
        
        setDamage(DAMAGE, UPG_DAMAGE);
        setBlock(BLOCK, UPG_BLOCK);
        setMagic(MAGIC, UPG_MAGIC);
        setSecondMagic(SECOND_MAGIC, UPG_SECOND_MAGIC);

        setCostUpgrade(1);

        CardModifierManager.addModifier(this, new PlayerReliefDamageMod());

        verifyBackground();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), PURIFY));
        addToBot(new TriggerReliefAction(p, false));
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
        return new GhastlyFire();
    }
}