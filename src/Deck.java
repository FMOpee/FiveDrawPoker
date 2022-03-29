import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Deck implements Deckable{
    private static Deck instance; //singleton
    private final List<Cardable> deck = new ArrayList<>();

    private Deck() {
        instance = this;
        for (int i=0; i<4; i++){
            for(int j=1;j<14;j++){
                if(i==0) deck.add(new Card(j,Cardable.Suit.DIAMOND));
                else if(i==1) deck.add(new Card(j, Cardable.Suit.HEART));
                else if(i==2) deck.add(new Card(j, Cardable.Suit.CLUB));
                else deck.add(new Card(j, Cardable.Suit.SPADE));
            }
        }
    }

    @Override
    public void shuffle() {
        Collections.shuffle(deck);
    }

    @Override
    public void returnToDeck(LinkedList<Cardable> discarded) throws Exception {
        if(deck.size()+discarded.size()!=NUM_CARDS) {
            throw new Exception("something clearly gone wrong in the deck size");
        }
        else {
            deck.addAll(discarded);
            shuffle();
        }

    }

    @Override
    public Cardable drawACard(boolean faceUp) {
        Cardable card = deck.remove(0);
        card.setFaceUp(faceUp);
        return card;
    }

    public static Deck getInstance(){
        if(instance == null)
            instance = new Deck();
        return instance;
    }
}
