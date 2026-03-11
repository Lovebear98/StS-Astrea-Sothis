package astrea.relics;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.helpers.PowerTip;

public abstract class AstreaRelic extends BaseRelic{
    public AstreaRelic(String id, String imageName, AbstractCard.CardColor pool, RelicTier tier, LandingSound sfx) {
        super(id, imageName, pool, tier, sfx);
    }

    public void fixDescription(){
        description = this.getUpdatedDescription();
        tips.clear();
        tips.add(new PowerTip(this.name, this.description));
        initializeTips();
    }

    @Override
    public void usedUp() {
        this.grayscale = true;
        this.usedUp = true;
    }


    public void unusedUp() {
        this.grayscale = false;
        this.usedUp = false;
    }

    public void trueUsedUp(){
        super.usedUp();
    }
}
