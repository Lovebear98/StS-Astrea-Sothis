package astrea.util;

import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class CustomTags {
    @SpireEnum
    public static AbstractCard.CardTags TemplateTag1;
    @SpireEnum
    public static AbstractCard.CardTags TemplateTag2;
    /// This tag tells our fixit patch to fix its description because it may have changed.
    @SpireEnum
    public static AbstractCard.CardTags ExpandedDescription;

}