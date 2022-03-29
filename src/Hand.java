import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Hand implements TestableHand{
    private final List<Cardable> cards = new ArrayList<>();

    @Override
    public Cardable getCard(int i) {
        return cards.get(i);
    }

    @Override
    public void draw(Deckable d, boolean faceUp) {
        Cardable drawnCard = d.drawACard(faceUp);
        cards.add(drawnCard);
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
        for (int i = 0; i<cards.size();i++){
            if(cards.get(i).isSelected()){
                cardsToBeDiscarded.add(cards.get(i));
                cards.set(i,null);
            }
        }
        return cardsToBeDiscarded;
    }

    @Override
    public LinkedList<Cardable> returnCards() {
        LinkedList<Cardable> cardsToReturn = new LinkedList<>(cards);
        for(int i=0; i<cards.size();i++){
            cards.set(i,null);
        }
        return cardsToReturn;
    }

    @Override
    public String evaluateHand() {
        sort();
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
            //straight flush , straight, flush tie-breaking
            if(ranking(selfSplit[0]) == 2 || ranking(selfSplit[0]) == 5 || ranking(selfSplit[0]) == 6){
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
            //high card tie-breaking
            else {
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
        }
    }

    @Override
    public void addCards(Cardable[] cards) {
        this.cards.addAll(Arrays.asList(cards));
    }

    private int valueToInt(String string){
        if(string.charAt(0) == 'A')
            return 1;
        else if (string.charAt(0) == 'K')
            return 13;
        else if (string.charAt(0) == 'Q')
            return 12;
        else if (string.charAt(0) == 'J')
            return 11;
        else {
            return Integer.parseInt(string.substring(0,string.length()-1));
        }
    }

    private void sort(){
        for (int i=0; i<cards.size();i++){
            for (int j=i+1; j<cards.size(); j++){
                if( cards.get(i).getSuitValue() > cards.get(j).getSuitValue() ){
                    Cardable temp = cards.get(i);
                    cards.set(i, cards.get(j));
                    cards.set(j, temp);
                }
                else if(cards.get(i).getSuit() == cards.get(j).getSuit() &&
                        cards.get(i).getValue() > cards.get(j).getValue()  ){
                    Cardable temp = cards.get(i);
                    cards.set(i, cards.get(j));
                    cards.set(j, temp);
                }
            }
        }
    }
    private void sortByValue(){
        for (int i=0; i<cards.size();i++){
            for (int j=i+1; j<cards.size(); j++){
                if( cards.get(i).getValue() > cards.get(j).getValue() ){
                    Cardable temp = cards.get(i);
                    cards.set(i, cards.get(j));
                    cards.set(j, temp);
                }
            }
        }
    }

    private String isRoyalFlush(){
        boolean flag = cards.get(0).getValue() == 1 &&    //A
                cards.get(1).getValue() == 10 &&          //10
                cards.get(4).getValue() == 13 &&          //K, as it's a sorted list so 2,3 has to be 11,12
                cards.get(0).getSuit() == cards.get(4).getSuit();   //of the same suit

        if(flag){
            return "Royal Flush";
        }
        else return "NO";
    }
    private String isStraightFlush(){
        boolean flag = cards.get(4).getValue() - cards.get(0).getValue() == 4 && //as it's a sorted list so all the cards have to be in a serial
                cards.get(0).getSuit() == cards.get(4).getSuit();   //of the same suit

        if(flag){
            String ret ="Straight Flush,";

            ret+=cards.get(4).getValueString();
            return ret;
        }
        else return "NO";
    }
    private String isFourOfAKind(){
        sortByValue();
        String s = "Four Of A Kind,";
        if( cards.get(0).getValue()==cards.get(3).getValue() ){
            s+=cards.get(0).getValueString(); //value of the matching card
            s+=","+cards.get(4).getValueString(); //value of the kicker
            sort();//resorting as isFourOfAKind sorts by value
            return s;
        }
        else if( cards.get(1).getValue()==cards.get(4).getValue() ){
            s+=cards.get(1).getValueString(); //value of the matching card
            s+=","+cards.get(0).getValueString(); //value of the kicker
            sort();//resorting as isFourOfAKind sorts by value
            return s;
        }
        else {
            sort();//resorting as isFourOfAKind sorts by value
            return "NO";
        }
    }
    private String isFullHouse(){
        sortByValue();
        String s = "Full House,";
        if( cards.get(0).getValue()==cards.get(2).getValue() &&
                cards.get(3).getValue()==cards.get(4).getValue() ){
            s+=cards.get(0).getValueString(); //value of the three matching card
            s+=","+cards.get(4).getValueString(); //value of the two matching cards
            sort();//resorting as isFourOfAKind sorts by value
            return s;
        }
        else if( cards.get(0).getValue()==cards.get(1).getValue() &&
                cards.get(2).getValue()==cards.get(4).getValue() ){
            s+=cards.get(2).getValueString(); //value of the three matching card
            s+=","+cards.get(0).getValueString(); //value of the two matching cards
            sort();//resorting as isFourOfAKind sorts by value
            return s;
        }
        else {
            sort();//resorting as it sorts by value
            return "NO";
        }
    }
    private String isFlush(){
        boolean flag = cards.get(0).getSuit() == cards.get(4).getSuit();   //of the same suit
        sortByValue();
        if(flag){
            String ret ="Flush,";

            ret+=cards.get(4).getValueString();
            sort();
            return ret;
        }
        else {
            sort();
            return "NO";
        }
    }
    private String isStraight(){
        sortByValue();
        if( cards.get(4).getValue() - cards.get(0).getValue() == 4 ){
            String ret ="Straight,";

            ret+=cards.get(4).getValueString();
            sort();
            return ret;
        }
        else if( cards.get(4).getValue() - cards.get(1).getValue() == 3 &&
                cards.get(4).getValue() == 13 && cards.get(0).getValue() == 1){
            sort();
            return "Straight,A";
        }
        else {
            sort();
            return "NO";
        }
    }
    private String isThreeOfAKind(){
        sortByValue();
        String s = "Three Of A Kind,";
        if( cards.get(0).getValue() == cards.get(2).getValue()  ) {
            s+=cards.get(0).getValueString(); //value of the matching card
            s+=","+cards.get(4).getValueString();
            s+=","+cards.get(3).getValueString();
            sort();//resorting as isFourOfAKind sorts by value
            return s;
        }
        else if( cards.get(1).getValue() == cards.get(3).getValue()  ) {
            s+=cards.get(1).getValueString(); //value of the matching card
            s+=","+cards.get(4).getValueString();
            s+=","+cards.get(0).getValueString();
            sort();//resorting as isFourOfAKind sorts by value
            return s;
        }
        else if( cards.get(2).getValue() == cards.get(4).getValue()  ) {
            s+=cards.get(2).getValueString(); //value of the matching card
            s+=","+cards.get(1).getValueString();
            s+=","+cards.get(0).getValueString();
            sort();//resorting as isFourOfAKind sorts by value
            return s;
        }
        else {
            sort();//resorting as it sorts by value
            return "NO";
        }
    }
    private String isTwoPairs(){
        sortByValue();
        boolean first = false;
        int firstPos = 0;
        boolean second = false;
        int secondPos = 0;

        for (int i=cards.size()-1; i>=0; i--){
            if(cards.get(i).getValue() == cards.get(i-1).getValue() ){
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
            if( cards.get(firstPos).getValue() < cards.get(secondPos).getValue() ){
                ret+=cards.get(secondPos).getValueString(); //value of the bigger pair
                ret+=","+cards.get(firstPos).getValueString(); //value of the smaller pair;
            }
            else{
                ret+=cards.get(firstPos).getValueString(); //value of the bigger pair
                ret+=","+cards.get(secondPos).getValueString(); //value of the smaller pair;
            }
            sort();
            return ret;
        }
        else{
            sort();
            return "NO";
        }
    }
    private String isPair(){
        sortByValue();
        boolean first = false;
        int firstPos = 0;

        for (int i=cards.size()-1; i>=0; i--){
            if( cards.get(i).getValue()== cards.get(i-1).getValue()){
                first = true;
                firstPos = i-1;
                break;
            }
        }

        if(first){
            StringBuilder ret = new StringBuilder("Pairs,");
            ret.append(cards.get(firstPos).getValueString()).append(" pair"); //value of the smaller pair;
            for (int i=4; i>=0;i--){
                if(i==firstPos+1) i--;
                else {
                    ret.append(",").append(cards.get(i).getValueString());
                }
            }
            sort();
            return ret.toString();
        }
        else{
            sort();
            return "NO";
        }
    }
    private String isHighCard(){
        sortByValue();
        StringBuilder ret = new StringBuilder("High Card");
        if( cards.get(0).getValue() == 0){
            ret.append(",A");
            for(int i = 4; i>=1;i--){
                ret.append(",").append(cards.get(i).getValueString());
            }
        }
        else {
            for(int i = 4; i>=0;i--){
                ret.append(",").append(cards.get(i).getValueString());
            }
        }
        sort();
        return ret.toString();
    }

    private int ranking(String s){
        switch (s) {
            case "Royal Flush": return 1;
            case "Straight Flush": return 2;
            case "Four Of A Kind": return 3;
            case "Full House": return 4;
            case "Flush": return 5;
            case "Straight": return 6;
            case "Three Of A Kind": return 7;
            case "Two Pairs": return 8;
            case "Pairs": return 9;
            default: return 10;
        }
    }
}
