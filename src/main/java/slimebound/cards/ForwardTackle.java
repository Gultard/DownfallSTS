package slimebound.cards;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import slimebound.SlimeboundMod;
import slimebound.actions.CommandAction;
import slimebound.actions.CommandCidAction;
import slimebound.actions.TackleSelfDamageAction;
import slimebound.patches.AbstractCardEnum;
import slimebound.powers.PreventTackleDamagePower;
import slimebound.powers.TackleBuffPower;
import slimebound.powers.TackleDebuffPower;


public class ForwardTackle extends AbstractSlimeboundCard {
    public static final String ID = "Slimebound:ForwardTackle";
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String IMG_PATH = "cards/forwardtackle.png";
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardStrings cardStrings;
    private static final int COST = 2;
    public static String UPGRADED_DESCRIPTION;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    }


    public ForwardTackle() {

        super(ID, NAME, SlimeboundMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, AbstractCardEnum.SLIMEBOUND, RARITY, TARGET);
        tags.add(SlimeboundMod.TACKLE);


        this.baseDamage = 18;
        baseSelfDamage = this.selfDamage = 3;
        SlimeboundMod.loadJokeCardImage(this, "ForwardTackle.png");

        //this.exhaust = true;

    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        if (!AbstractDungeon.player.hasPower(PreventTackleDamagePower.POWER_ID))
            addToBot(new TackleSelfDamageAction(new DamageInfo(p, selfDamage, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.SMASH));
        addToBot(new CommandCidAction());
        addToBot(new CommandCidAction());
        if (upgraded) addToBot(new CommandCidAction());


        checkMinionMaster();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(4);
            rawDescription = UPGRADED_DESCRIPTION;
            initializeDescription();
        }
    }
}


