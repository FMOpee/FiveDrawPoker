public class Card implements Cardable{
    private final int value;
    private final Suit suit;
    private boolean selected;
    private boolean faceUp;

    public Card(int value, Suit suit) {
        this.value = value;
        this.suit = suit;
        this.selected = false;
        this.faceUp = false;
    }

    @Override
    public boolean isSelected() {
        return selected;
    }

    @Override
    public boolean isFaceUp() {
        return faceUp;
    }

    @Override
    public Suit getSuit() {
        return suit;
    }

    @Override
    public void switchSelectedState() {
        selected = !selected;
    }

    @Override
    public void resetSelected() {
        selected = false;
    }

    @Override
    public void setFaceUp(boolean faceUp) {
        this.faceUp = faceUp;
    }

    @Override
    public String toString(){
        String returnString = "";

        if(value<=10 && value>=2)
            returnString+= String.valueOf(value);
        else if(value == 11)
            returnString+="J";
        else if(value == 12)
            returnString+="Q";
        else if(value == 13)
            returnString+="K";
        else returnString+="A";

        if(suit.equals(Suit.CLUB))
            returnString+="♣";
        else if(suit.equals(Suit.DIAMOND))
            returnString+="♦";
        else if(suit.equals(Suit.HEART))
            returnString+="♥";
        else returnString+="♠";

        return returnString;
    }
}
