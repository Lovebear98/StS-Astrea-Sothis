package astrea.cards;

import astrea.util.CardStats;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.PurgeField;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.ChemicalX;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import static astrea.util.managers.Wiz.p;

public abstract class AbstractAstreaCard extends BaseCard{

    protected static final Color RED_BORDER_GLOW_COLOR = Color.RED.cpy();


    ///The base magic number
    public int baseSecondMagic;
    ///The "Upgrade" number of the second magic
    protected int secondMagicUpgrade;
    ///The current second magicr
    public int secondMagic;

    ///Whether the Magic Number CAN upgrade
    private boolean secondMagicCanUpgrade;
    ///If the Second Magic is upgraded
    public boolean secondMagicUpgraded;

    ///Whether the second magic is modified
    public boolean isSecondMagicModified;


    /// Whether we should upgrade the card preview when this upgrades. Defaults to true.
    protected boolean UpgradePreviewCard = true;


    /// Back up the card's description when we're adding Blizzard-like text to it
    public String backupDescription = null;


    public AbstractAstreaCard(String ID, CardStats info) {
        super(ID, info);
        ///It's not upgraded by default
        this.secondMagicUpgraded = false;
        ///It can't upgrade by default
        this.secondMagicCanUpgrade = false;
        ///It's -1 by default
        this.baseSecondMagic = -1;
        ///It doesn't upgrade by default
        this.secondMagicUpgrade = 0;
        ///It's not modified by default
        this.isSecondMagicModified = false;
    }

    /// Beginning a card's use method with e = GetXEnergy(); will do all of the work of making X-cost things happen,
    /// and provide an easy number to use for the energy used
    protected int GetXEnergy(){
        int e = this.energyOnUse;
        if(!freeToPlay()){
            p().energy.use(EnergyPanel.totalCount);
        }
        if(AbstractDungeon.player.hasRelic(ChemicalX.ID)){
            e += 2;
            AbstractDungeon.player.getRelic(ChemicalX.ID).flash();
        }
        return e;
    }

    /// Easily set if the card should purge or not
    protected void setPurge(boolean b){
        PurgeField.purge.set(this, b);
    }

    /// Reset the card's description when it moves piles
    public void resetDescription(){
        addToBot(
                new AbstractGameAction() {
                    @Override
                    public void update() {
                        if(backupDescription != null){
                            rawDescription = backupDescription;
                            backupDescription = null;
                        }
                        initializeDescription();
                        isDone = true;
                    }
                }
        );
    }



    ///Set the second magic stats on the card
    protected final void setSecondMagic(int magic)
    {
        this.setSecondMagic(magic, 0);
    }
    public final void setSecondMagic(int base, int upg) {
        this.baseSecondMagic = this.secondMagic = base;
        if (upg != 0) {
            this.secondMagicCanUpgrade = true;
            this.secondMagicUpgrade = upg;
        }
    }

    /// Provides an easy second damage variable where needed.
    protected void setSecondDamage(int base, int upg){
        setCustomVar("D2", VariableType.DAMAGE, base, upg,
                (card, m, val)->{ //Do things before calculating damage
                    return val;
                },
                (card, m, val)->{ //Do things after calculating damage
                    return val;
                });
    }
    protected int getSecondDamage(){
        return customVar("D2");
    }

    /// Provides an easy second block variable where needed.
    protected void setSecondBlock(int base, int upg){
        setCustomVar("B2", VariableType.BLOCK, base, upg,
                (card, m, val)->{ //Do things before calculating damage
                    return val;
                },
                (card, m, val)->{ //Do things after calculating damage
                    return val;
                });
    }
    protected int getSecondBlock(){
        return customVar("B2");
    }


    /// This can be used in dynamic text blocks for card-specific changes.
    public int SpecialVar(){
        return 0;
    }












    ///When we upgrade the card, if it isn't upgraded, if the second magic upgrades, upgrade the second magic.
    @Override
    public void upgrade() {
        if(!upgraded){
            if (secondMagicCanUpgrade) {
                this.upgradeSecondMagic(secondMagicUpgrade);
            }
        }
        if(UpgradePreviewCard && this.cardsToPreview != null){
            cardsToPreview.upgrade();
        }
        super.upgrade();
    }

    ///Upgrade our second magic
    protected void upgradeSecondMagic(int amount) {
        this.baseSecondMagic += amount;
        this.secondMagic = this.baseSecondMagic;
        this.secondMagicUpgraded = true;
    }

    ///When we're supposed to show upgrades, upgrade our second magic and color it
    @Override
    public void displayUpgrades() {
        super.displayUpgrades();
        ///If it's upgraded
        if (this.secondMagicUpgraded) {
            ///Show the un-modified base
            this.secondMagic = this.baseSecondMagic;
            ///And note that it's modified
            this.isSecondMagicModified = true;
        }
    }

    ///When we copy this card, pass along its second magic
    @Override
    public AbstractCard makeStatEquivalentCopy() {
        ///Get the default first
        AbstractCard card = super.makeStatEquivalentCopy();

        //Modify it if it fits
        if (card instanceof AbstractAstreaCard) {
            ///Pass along whether it CAN upgrade
            ((AbstractAstreaCard) card).secondMagicCanUpgrade = this.secondMagicCanUpgrade;
            ///Pass along whether it's upgraded
            ((AbstractAstreaCard) card).secondMagicUpgraded = this.secondMagicUpgraded;
            ///How much it upgrades by
            ((AbstractAstreaCard) card).secondMagicUpgrade = this.secondMagicUpgrade;
            ///Its base amount
            ((AbstractAstreaCard) card).baseSecondMagic = this.baseSecondMagic;
            ///And its current amount
            ((AbstractAstreaCard) card).secondMagic = this.secondMagic;
        }

        ///Then return the result
        return card;
    }


    /// Putting this in a card's constructor will allow it to use a custom status/upgrade background, if you want it to.
    protected void verifyBackground(){
        if(this.type == CardType.CURSE){
            setBackgroundTexture("astrea/images/character/cardback/bg_curse.png", "astrea/images/character/cardback/bg_curse_p.png");
        } else if(this.type == CardType.STATUS){
            setBackgroundTexture("astrea/images/character/cardback/bg_status.png", "astrea/images/character/cardback/bg_status_p.png");
        }
    }


    public static Color exposeGlowCOlor(){
        return GOLD_BORDER_GLOW_COLOR.cpy();
    }
}
