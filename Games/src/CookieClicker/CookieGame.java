package CookieClicker;
/** Let's play with cookies! */
public class CookieGame {
    /** Number filled with mysterious magic. */
    private final int MAGIC = 10;
    /** Default price. */
    private int cursorPrice;
    /** Value to increment. */
    private int cursorPriceIncrease;
    /** Count of the cursors available. */
    private int cursorCount = 1;
    /** Money. */
    private int cookies = 0;
    /** Clicker price. */
    private int clickerPrice;
    /**  Value to increment. */
    private int clickerPriceIncrease;
    /** Count of the clickers available. */
    private int clickerCount = 0;
    /** Interval of clickers. */
    private int clickerInterval;
    /** Decrement value. */
    private int clickerIntervalDecrease;
    /** Minimum possible interval. */
    private int clickerIntervalMin;

    /** Default constructor. */
    public CookieGame() {
        cursorPrice = 20;
        cursorPriceIncrease = 20;
        clickerPrice = 100;
        clickerPriceIncrease = 200;
        clickerInterval = 5000;
        clickerIntervalDecrease = 100;
        clickerIntervalMin = 1000;
    }

    /**
     * Alternative constructor.
     * @param cursorPrice default price
     * @param cursorPriceIncrease increment
     */
    public CookieGame(int cursorPrice, int cursorPriceIncrease) {
        this.cursorPrice = cursorPrice > 0 ? cursorPrice : 0;
        this.cursorPriceIncrease = cursorPriceIncrease > 0 ? cursorPriceIncrease : 0;
    }

    /**
     * Have you ever seen as many constructors?
     * @param cursorPrice price
     * @param cursorPriceIncrease increment
     * @param clickerPrice clicker price
     * @param clickerPriceIncrease increment
     * @param clickerInterval interval
     * @param clickerIntervalDecrease decrease interval
     * @param clickerIntervalMin minimum
     */
    public CookieGame(int cursorPrice, int cursorPriceIncrease, int clickerPrice,
                      int clickerPriceIncrease, int clickerInterval, int clickerIntervalDecrease,
                      int clickerIntervalMin) {
        this.cursorPrice = cursorPrice > 0 ? cursorPrice : 0;
        this.cursorPriceIncrease = cursorPriceIncrease > 0 ? cursorPriceIncrease : 0;
        this.clickerPrice = clickerPrice > 0 ? clickerPrice : 0;
        this.clickerPriceIncrease = clickerPriceIncrease > 0 ? clickerPriceIncrease : 0;
        this.clickerInterval = clickerInterval > 0 ? clickerInterval : 0;
        this.clickerIntervalDecrease = clickerIntervalDecrease > 0 ? clickerIntervalDecrease : 0;
        this.clickerIntervalMin = clickerIntervalMin > 0 ? clickerIntervalMin : 0;
    }

    /**
     * Do you have enough cookies?
     * @return bool
     */
    public boolean canBuyCursor() {
        return cookies >= getCursorPrice();
    }

    /**
     * Getter.
     * @return how many?
     */
    public int getCursorCount() {
        return cursorCount;
    }

    /** Buy it! */
    public void buyCursor() {
        if (canBuyCursor()) {
            cookies -= getCursorPrice();
            ++cursorCount;
        }
    }

    /**
     * Getter.
     * @return price of the next.
     */
    public int getCursorPrice() {
        return cursorPrice + cursorPriceIncrease * (cursorCount - 1);
    }

    public boolean canBuyClicker() {
        return cookies >= getClickerPrice();
    }

    /** Getter. */
    public int getClickerCount() {
        return clickerCount;
    }

    /** Buy new clicker. */
    public void buyClicker() {
        if (canBuyClicker()) {
            cookies -= getClickerPrice();
            ++clickerCount;
            changeClickerInterval();
        }

    }

    /** Set new interval. */
    private void changeClickerInterval() {
        if (clickerCount > 1) {
            int newInterval = clickerInterval - clickerIntervalDecrease;
            clickerInterval = (newInterval >= clickerIntervalMin ? newInterval : clickerIntervalMin);
        }
    }

    /**
     * Getter.
     * @return Next clicker price.
     */
    public int getClickerPrice() {
        return clickerPrice + clickerPriceIncrease * clickerCount;
    }

    /**
     * Getter.
     * @return clicker interval.
     */
    public int getClickerInterval() {
        return clickerInterval;
    }

    /**
     * Clicker action.
     */
    public void clickerAction() {
        if (clickerCount == 0) {
            return;
        }
        for (int i = 0; i < clickerCount; i++) {
            click();
        }
    }

    /**
     * Getter.
     * @return money amount
     */
    public int getCookies() {
        return cookies;
    }

    /** Action here. */
    public void click() {
        cookies += cursorCount;
    }

    /** Bonus action. */
    public void decrement() {
        cookies -= cursorCount * MAGIC;
    }
}
