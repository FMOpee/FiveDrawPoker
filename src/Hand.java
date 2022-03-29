import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Hand implements Handable , TestableHand{
    private final List<Cardable> cards = new ArrayList<>();
    public Hand(){

    }

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
                cardsToBeDiscarded.add(cards.remove(i));
                i--;
            }
        }
        return cardsToBeDiscarded;
    }

    @Override
    public LinkedList<Cardable> returnCards() {
        LinkedList<Cardable> cardsToReturn = new LinkedList<>(cards);
        cards.removeAll(cardsToReturn);
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
                return getValue(String.valueOf( selfSplit[1].charAt(0) )) - getValue(String.valueOf( otherSplit[1].charAt(0) ));
            }
            //Four of a kind and full house tie-breaker
            else if(ranking(selfSplit[0]) == 3 || ranking(selfSplit[0]) == 4){
                if(getValue(selfSplit[1]) == getValue(otherSplit[1]))
                    return getValue(selfSplit[2]) - getValue(otherSplit[2]);
                else return getValue(selfSplit[1]) - getValue(otherSplit[1]);
            }
            //Three of a kind and two pairs tie-breaking
            else if(ranking(selfSplit[0]) == 7 || ranking(selfSplit[0]) == 8) {
                if(getValue(selfSplit[1]) == getValue(otherSplit[1]))
                    if(getValue(selfSplit[2]) == getValue(otherSplit[2]))
                        return getValue(selfSplit[3]) - getValue(otherSplit[3]);
                    else return getValue(selfSplit[2]) - getValue(otherSplit[2]);
                else return getValue(selfSplit[1]) - getValue(otherSplit[1]);
            }
            //pair tie-breaking
            else if(ranking(selfSplit[0]) == 9) {
                if(getValue(selfSplit[1]) == getValue(otherSplit[1]))
                    if(getValue(selfSplit[2]) == getValue(otherSplit[2]))
                        if( getValue(selfSplit[3]) == getValue(otherSplit[3]) )
                            return getValue(selfSplit[4]) - getValue(otherSplit[4]);
                        else return getValue(selfSplit[3]) - getValue(otherSplit[3]);
                    else return getValue(selfSplit[2]) - getValue(otherSplit[2]);
                else return getValue(selfSplit[1]) - getValue(otherSplit[1]);
            }
            //high card tie-breaking
            else {
                if(getValue(selfSplit[1]) == getValue(otherSplit[1]))
                    if(getValue(selfSplit[2]) == getValue(otherSplit[2]))
                        if( getValue(selfSplit[3]) == getValue(otherSplit[3]) )
                            if(getValue(selfSplit[4]) == getValue(otherSplit[4]))
                                return getValue(selfSplit[5]) - getValue(otherSplit[5]);
                            else return getValue(selfSplit[4]) - getValue(otherSplit[4]);
                        else return getValue(selfSplit[3]) - getValue(otherSplit[3]);
                    else return getValue(selfSplit[2]) - getValue(otherSplit[2]);
                else return getValue(selfSplit[1]) - getValue(otherSplit[1]);
            }
        }
    }

    @Override
    public void addCards(Cardable[] cards) {
        this.cards.addAll(Arrays.asList(cards));
    }

    private int getValue(String string){
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
    private int getSuit(String string){
        if(string.charAt(string.length()-1) == '♣')
            return 0;
        else if(string.charAt(string.length()-1) == '♦')
            return 1;
        else if(string.charAt(string.length()-1) == '♥')
            return 2;
        else return 3;
    }

    private void sort(){
        for (int i=0; i<cards.size();i++){
            for (int j=i+1; j<cards.size(); j++){
                if( getSuit(cards.get(i).toString()) > getSuit(cards.get(j).toString()) ){
                    Cardable temp = cards.get(i);
                    cards.set(i, cards.get(j));
                    cards.set(j, temp);
                }
                else if(getSuit(cards.get(i).toString()) == getSuit(cards.get(j).toString()) &&
                        getValue(cards.get(i).toString()) > getValue(cards.get(j).toString())  ){
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
                if( getValue(cards.get(i).toString()) > getValue(cards.get(j).toString()) ){
                    Cardable temp = cards.get(i);
                    cards.set(i, cards.get(j));
                    cards.set(j, temp);
                }
            }
        }
    }

    private String isRoyalFlush(){
        boolean flag = getValue(cards.get(0).toString()) == 1 &&    //A
                getValue(cards.get(1).toString()) == 10 &&          //10
                getValue(cards.get(4).toString()) == 13 &&          //K, as it's a sorted list so 2,3 has to be 11,12
                getSuit (cards.get(0).toString()) == getSuit(cards.get(4).toString());   //of the same suit

        if(flag){
            return "Royal Flush";
        }
        else return "NO";
    }
    private String isStraightFlush(){
        boolean flag = getValue(cards.get(4).toString()) - getValue(cards.get(0).toString()) == 4 && //as it's a sorted list so all the cards have to be in a serial
                getSuit (cards.get(0).toString()) == getSuit(cards.get(4).toString());   //of the same suit

        if(flag){
            String ret ="Straight Flush,";

            ret+=cards.get(4).toString().substring(0,cards.get(4).toString().length()-1);
            ret+=" high";

            return ret;
        }
        else return "NO";
    }
    private String isFourOfAKind(){
        sortByValue();
        String s = "Four Of A Kind,";
        if( getValue(cards.get(0).toString())==getValue(cards.get(3).toString()) ){
            s+=cards.get(0).toString().substring(0,cards.get(0).toString().length()-1); //value of the matching card
            s+=","+cards.get(4).toString().substring(0,cards.get(4).toString().length()-1); //value of the kicker
            sort();//resorting as isFourOfAKind sorts by value
            return s;
        }
        else if( getValue(cards.get(1).toString())==getValue(cards.get(4).toString()) ){
            s+=cards.get(1).toString().substring(0,cards.get(1).toString().length()-1); //value of the matching card
            s+=","+cards.get(0).toString().substring(0,cards.get(0).toString().length()-1); //value of the kicker
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
        if( getValue(cards.get(0).toString())==getValue(cards.get(2).toString()) &&
                getValue(cards.get(3).toString())==getValue(cards.get(4).toString()) ){
            s+=cards.get(0).toString().substring(0,cards.get(0).toString().length()-1); //value of the three matching card
            s+=","+cards.get(4).toString().substring(0,cards.get(4).toString().length()-1); //value of the two matching cards
            sort();//resorting as isFourOfAKind sorts by value
            return s;
        }
        else if( getValue(cards.get(0).toString())==getValue(cards.get(1).toString()) &&
                getValue(cards.get(2).toString())==getValue(cards.get(4).toString()) ){
            s+=cards.get(2).toString().substring(0,cards.get(2).toString().length()-1); //value of the three matching card
            s+=","+cards.get(0).toString().substring(0,cards.get(0).toString().length()-1); //value of the two matching cards
            sort();//resorting as isFourOfAKind sorts by value
            return s;
        }
        else {
            sort();//resorting as it sorts by value
            return "NO";
        }
    }
    private String isFlush(){
        boolean flag = getSuit (cards.get(0).toString()) == getSuit(cards.get(4).toString());   //of the same suit
        sortByValue();
        if(flag){
            String ret ="Flush,";

            ret+=cards.get(4).toString().substring(0,cards.get(4).toString().length()-1);
            ret+=" high";
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
        if( getValue(cards.get(4).toString()) - getValue(cards.get(0).toString()) == 4 ){
            String ret ="Straight,";

            ret+=cards.get(4).toString().substring(0,cards.get(4).toString().length()-1);
            ret+=" high";
            sort();
            return ret;
        }
        else if( getValue(cards.get(4).toString()) - getValue(cards.get(1).toString()) == 3 &&
                getValue(cards.get(4).toString()) == 13 && getValue(cards.get(0).toString()) == 1){
            sort();
            return "Straight,A high";
        }
        else {
            sort();
            return "NO";
        }
    }
    private String isThreeOfAKind(){
        sortByValue();
        String s = "Three Of A Kind,";
        if( getValue(cards.get(0).toString())==getValue(cards.get(2).toString()) ){
            s+=cards.get(0).toString().substring(0,cards.get(0).toString().length()-1); //value of the matching card
            s+=","+cards.get(4).toString().substring(0,cards.get(4).toString().length()-1);
            s+=","+cards.get(3).toString().substring(0,cards.get(3).toString().length()-1);
            sort();//resorting as isFourOfAKind sorts by value
            return s;
        }
        else if( getValue(cards.get(1).toString())==getValue(cards.get(3).toString()) ){
            s+=cards.get(1).toString().substring(0,cards.get(1).toString().length()-1); //value of the matching card
            s+=","+cards.get(4).toString().substring(0,cards.get(4).toString().length()-1);
            s+=","+cards.get(0).toString().substring(0,cards.get(0).toString().length()-1);
            sort();//resorting as isFourOfAKind sorts by value
            return s;
        }
        else if( getValue(cards.get(2).toString())==getValue(cards.get(4).toString()) ){
            s+=cards.get(2).toString().substring(0,cards.get(2).toString().length()-1); //value of the matching card
            s+=","+cards.get(1).toString().substring(0,cards.get(1).toString().length()-1);
            s+=","+cards.get(0).toString().substring(0,cards.get(0).toString().length()-1);
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
            if( getValue(cards.get(i).toString()) == getValue(cards.get(i-1).toString()) ){
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
            if( getValue(cards.get(firstPos).toString()) < getValue(cards.get(secondPos).toString()) ){
                ret+=cards.get(secondPos).toString().substring(0,cards.get(secondPos).toString().length()-1); //value of the bigger pair
                ret+=","+cards.get(firstPos).toString().substring(0,cards.get(firstPos).toString().length()-1); //value of the smaller pair;
            }
            else{
                ret+=cards.get(firstPos).toString().substring(0,cards.get(firstPos).toString().length()-1); //value of the bigger pair
                ret+=","+cards.get(secondPos).toString().substring(0,cards.get(secondPos).toString().length()-1); //value of the smaller pair;
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
            if( getValue(cards.get(i).toString()) == getValue(cards.get(i-1).toString()) ){
                first = true;
                firstPos = i-1;
                break;
            }
        }

        if(first){
            StringBuilder ret = new StringBuilder("Pairs,");
            ret.append(cards.get(firstPos).toString(), 0, cards.get(firstPos).toString().length() - 1).append(" pair"); //value of the smaller pair;
            for (int i=4; i>=0;i--){
                if(i==firstPos+1) i--;
                else {
                    ret.append(",").append(cards.get(i).toString(), 0, cards.get(i).toString().length() - 1);
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
        if( getValue(cards.get(0).toString()) == 0){
            ret.append(",A");
            for(int i = 4; i>=1;i--){
                ret.append(",").append(cards.get(i).toString(), 0, cards.get(i).toString().length() - 1);
            }
        }
        else {
            for(int i = 4; i>=0;i--){
                ret.append(",").append(cards.get(i).toString(), 0, cards.get(i).toString().length() - 1);
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
