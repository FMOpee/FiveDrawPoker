import java.util.LinkedList;

public class Hand implements TestableHand{
    private Cardable[] cards = new Cardable[HAND_SIZE];

    public Hand(){
        for(int i =0; i<5;i++) {
            cards[i]=null;
        }
    }

    @Override
    public Cardable getCard(int i) {
        return cards[i];
    }

    @Override
    public void draw(Deckable d, boolean faceUp) {
        for(int i=0; i<cards.length;i++){
            if(cards[i] == null){
                Cardable drawnCard = d.drawACard(faceUp);
                cards[i]=drawnCard;
            }
        }
    }

    @Override
    public void showAllCards() {
        for (Cardable card:cards) {
            if(!card.isFaceUp()){
                card.setFaceUp(true);
            }
        }
    }

    @Override
    public LinkedList<Cardable> discard() {
        LinkedList<Cardable> cardsToBeDiscarded = new LinkedList<>();
        for (int i = 0; i<cards.length;i++){
            if(cards[i].isSelected()){
                cardsToBeDiscarded.add(cards[i]);
                cards[i]=null;
            }
        }
        return cardsToBeDiscarded;
    }

    @Override
    public LinkedList<Cardable> returnCards() {
        LinkedList<Cardable> cardsToReturn = new LinkedList<>();
        for (int i=0; i<cards.length;i++){
            cardsToReturn.add(cards[i]);
            cards[i] = null;
        }
        return cardsToReturn;
    }

    @Override
    public String evaluateHand() {
        if(!isRoyalFlush().equals("NO")) return isRoyalFlush();
        else if(!isStraightFlush().equals("NO")) return isStraightFlush();
        else if(!isFourOfAKind().equals("NO")) return isFourOfAKind();
        else if(!isFullHouse().equals("NO")) return isFullHouse();
        else if(!isFlush().equals("NO")) return isFlush();
        else if(!isStraight().equals("NO")) return isStraight();
        else if(!isThreeOfAKind().equals("NO")) return isThreeOfAKind();
        else if(!isTwoPairs().equals("NO")) return isTwoPairs();
        else if(!isPair().equals("NO")) return isPair();
        else return isHighCard();
    }

    @Override
    public int compareTo(Handable handable) {
        String selfEvaluation = evaluateHand();
        String[] selfSplit = selfEvaluation.split(",",0);
        String otherEvaluation = handable.evaluateHand();
        String[] otherSplit = otherEvaluation.split(",",0);
        if(!selfSplit[0].equals(otherSplit[0])){
            return ranking(otherSplit[0]) - ranking(selfSplit[0]);
        }
        else {
            //straight flush, flush, straight tie-breaking
            if(ranking(selfSplit[0]) == 2 || ranking(selfSplit[0]) == 6 || ranking(selfSplit[0]) == 5){
                return valueToInt(selfSplit[1]) - valueToInt(otherSplit[1]);
            }
            //Four of a kind and full house tie-breaker
            else if(ranking(selfSplit[0]) == 3 || ranking(selfSplit[0]) == 4){
                if(valueToInt(selfSplit[1]) == valueToInt(otherSplit[1]))
                    return valueToInt(selfSplit[2]) - valueToInt(otherSplit[2]);
                else return valueToInt(selfSplit[1]) - valueToInt(otherSplit[1]);
            }
            //Three of a kind and two pairs tie-breaking
            else if(ranking(selfSplit[0]) == 7 || ranking(selfSplit[0]) == 8) {
                if(valueToInt(selfSplit[1]) == valueToInt(otherSplit[1]))
                    if(valueToInt(selfSplit[2]) == valueToInt(otherSplit[2]))
                        return valueToInt(selfSplit[3]) - valueToInt(otherSplit[3]);
                    else return valueToInt(selfSplit[2]) - valueToInt(otherSplit[2]);
                else return valueToInt(selfSplit[1]) - valueToInt(otherSplit[1]);
            }
            //pair tie-breaking
            else if(ranking(selfSplit[0]) == 9) {
                if(valueToInt(selfSplit[1]) == valueToInt(otherSplit[1]))
                    if(valueToInt(selfSplit[2]) == valueToInt(otherSplit[2]))
                        if( valueToInt(selfSplit[3]) == valueToInt(otherSplit[3]) )
                            return valueToInt(selfSplit[4]) - valueToInt(otherSplit[4]);
                        else return valueToInt(selfSplit[3]) - valueToInt(otherSplit[3]);
                    else return valueToInt(selfSplit[2]) - valueToInt(otherSplit[2]);
                else return valueToInt(selfSplit[1]) - valueToInt(otherSplit[1]);
            }
            //high card, flush tie-breaking
            else if(ranking(selfSplit[0]) == 10 ) {
                if(valueToInt(selfSplit[1]) == valueToInt(otherSplit[1]))
                    if(valueToInt(selfSplit[2]) == valueToInt(otherSplit[2]))
                        if( valueToInt(selfSplit[3]) == valueToInt(otherSplit[3]) )
                            if(valueToInt(selfSplit[4]) == valueToInt(otherSplit[4]))
                                return valueToInt(selfSplit[5]) - valueToInt(otherSplit[5]);
                            else return valueToInt(selfSplit[4]) - valueToInt(otherSplit[4]);
                        else return valueToInt(selfSplit[3]) - valueToInt(otherSplit[3]);
                    else return valueToInt(selfSplit[2]) - valueToInt(otherSplit[2]);
                else return valueToInt(selfSplit[1]) - valueToInt(otherSplit[1]);
            }
            else return 0;
        }
    }

    @Override
    public void addCards(Cardable[] cards) {
        this.cards=cards;
    }

    private int valueToInt(String string){
        return switch (string) {
            case "A" -> 14;
            case "K" -> 13;
            case "Q" -> 12;
            case "J" -> 11;
            default -> Integer.parseInt(string);
        };
    }

    public void sort(){
        for (int i=0; i<cards.length;i++){
            for (int j=i+1; j<cards.length; j++){
                if( cards[i].getSuitValue() > cards[j].getSuitValue() ){
                    Cardable temp = cards[i];
                    cards[i]= cards[j];
                    cards[j]= temp;
                }
                else if(cards[i].getSuit() == cards[j].getSuit() &&
                        cards[i].getValue() > cards[j].getValue()  ){
                    Cardable temp = cards[i];
                    cards[i]= cards[j];
                    cards[j]= temp;
                }
            }
        }
    }
    public void sortByValue(){
        for (int i=0; i<cards.length;i++){
            for (int j=i+1; j<cards.length; j++){
                if( cards[i].getValue() > cards[j].getValue() ){
                    Cardable temp = cards[i];
                    cards[i]= cards[j];
                    cards[j]= temp;
                }
            }
        }
    }

    private String isRoyalFlush(){
        sort();
        boolean flag = cards[0].getValue() == 1 &&    //A
                cards[1].getValue() == 10 &&          //10
                cards[2].getValue() == 11 &&
                cards[3].getValue() == 12 &&
                cards[4].getValue() == 13;
        if(flag){
            for(int i=0; i<4;i++){
                if(cards[i].getSuit() != cards[i+1].getSuit()){
                    flag=false;
                    break;
                }
            }
        }

        if(flag){
            return "Royal Flush";
        }
        else return "NO";
    }
    private String isStraightFlush(){
        sort();
        boolean flag = cards[4].getValue() - cards[0].getValue() == 4; //as it's a sorted list so all the cards have to be in a serial if of the same suit
        if(flag){
            flag = cards[0].getSuit() == cards[4].getSuit();
        }

        if(flag){
            String ret ="Straight Flush,";

            ret+=cards[4].getValueString();
            return ret;
        }
        else return "NO";
    }
    private String isFourOfAKind(){
        sortByValue();
        String s = "Four Of A Kind,";
        if( cards[0].getValue()==cards[3].getValue() ){
            s+=cards[0].getValueString(); //value of the matching card
            s+=","+cards[4].getValueString(); //value of the kicker
            return s;
        }
        else if( cards[1].getValue()==cards[4].getValue() ){
            s+=cards[1].getValueString(); //value of the matching card
            s+=","+cards[0].getValueString(); //value of the kicker
            return s;
        }
        else {
            return "NO";
        }
    }
    private String isFullHouse(){
        sortByValue();
        String s = "Full House,";
        if( cards[0].getValue()==cards[2].getValue() &&
                cards[3].getValue()==cards[4].getValue() ){
            s+=cards[0].getValueString(); //value of the three matching card
            s+=","+cards[4].getValueString(); //value of the two matching cards
            return s;
        }
        else if( cards[0].getValue()==cards[1].getValue() &&
                cards[2].getValue()==cards[4].getValue() ){
            s+=cards[2].getValueString(); //value of the three matching card
            s+=","+cards[0].getValueString(); //value of the two matching cards
            return s;
        }
        else {
            return "NO";
        }
    }
    private String isFlush(){
        sort();
        boolean flag = cards[0].getSuit() == cards[4].getSuit();   //of the same suit
        sortByValue();
        if(flag){
            String ret ="Flush,";

            ret+=cards[4].getValueString();
            return ret;
        }
        else {
            return "NO";
        }
    }
    private String isStraight(){
        sortByValue();
        if( cards[4].getValue() - cards[3].getValue() == 1 &&
                cards[3].getValue() - cards[2].getValue() == 1 &&
                cards[2].getValue() - cards[1].getValue() == 1
        ){
            if(cards[1].getValue() - cards[0].getValue() == 1 ) {
                String ret = "Straight,";

                ret += cards[4].getValueString();
                return ret;
            }
            else if (cards[1].getValue() == 10 && cards[0].getValue() ==1){
                return "Straight,A";
            }
        }
        return "NO";
    }
    private String isThreeOfAKind(){
        sortByValue();
        String s = "Three Of A Kind,";
        if( cards[0].getValue() == cards[2].getValue()  ) {
            s+=cards[0].getValueString(); //value of the matching card
            s+=","+cards[4].getValueString();
            s+=","+cards[3].getValueString();
            return s;
        }
        else if( cards[1].getValue() == cards[3].getValue()  ) {
            s+=cards[1].getValueString(); //value of the matching card
            s+=","+cards[4].getValueString();
            s+=","+cards[0].getValueString();
            return s;
        }
        else if( cards[2].getValue() == cards[4].getValue()  ) {
            s+=cards[2].getValueString(); //value of the matching card
            s+=","+cards[1].getValueString();
            s+=","+cards[0].getValueString();
            return s;
        }
        else {
            return "NO";
        }
    }
    private String isTwoPairs(){
        sortByValue();
        boolean first = false;
        int firstPos = 0;
        boolean second = false;
        int secondPos = 0;

        for (int i=cards.length-1; i>0; i--){
            if(cards[i].getValue() == cards[i-1].getValue() ){
                if (!first) {
                    first = true;
                    firstPos = i-1;
                }
                else {
                    second = true;
                    secondPos = i-1;
                }
                i--;
            }
        }

        if(first && second){
            String ret = "Two Pairs,";
            if( cards[firstPos].getValue() < cards[secondPos].getValue() ){
                ret+=cards[secondPos].getValueString(); //value of the bigger pair
                ret+=","+cards[firstPos].getValueString(); //value of the smaller pair;
            }
            else{
                ret+=cards[firstPos].getValueString(); //value of the bigger pair
                ret+=","+cards[secondPos].getValueString(); //value of the smaller pair;
            }
            return ret;
        }
        else{
            return "NO";
        }
    }
    private String isPair(){
        sortByValue();
        boolean first = false;
        int firstPos = 0;

        for (int i=cards.length-1; i>0; i--){
            if( cards[i].getValue()== cards[i-1].getValue()){
                first = true;
                firstPos = i-1;
                break;
            }
        }

        if(first){
            StringBuilder ret = new StringBuilder("Pair,");
            ret.append(cards[firstPos].getValueString()); //value of the smaller pair;
            for (int i=4; i>=0;i--){
                if(i==firstPos+1) i--;
                else {
                    ret.append(",").append(cards[i].getValueString());
                }
            }
            return ret.toString();
        }
        else{
            return "NO";
        }
    }
    private String isHighCard(){
        sortByValue();
        StringBuilder ret = new StringBuilder("High Card");
        if( cards[0].getValue() == 1){
            ret.append(",A");
            for(int i = 4; i>=1;i--){
                ret.append(",").append(cards[i].getValueString());
            }
        }
        else {
            for(int i = 4; i>=0;i--){
                ret.append(",").append(cards[i].getValueString());
            }
        }
        return ret.toString();
    }

    private int ranking(String s){
        return switch (s) {
            case "Royal Flush" -> 1;
            case "Straight Flush" -> 2;
            case "Four Of A Kind" -> 3;
            case "Full House" -> 4;
            case "Flush" -> 5;
            case "Straight" -> 6;
            case "Three Of A Kind" -> 7;
            case "Two Pairs" -> 8;
            case "Pair" -> 9;
            default -> 10;
        };
    }
}
