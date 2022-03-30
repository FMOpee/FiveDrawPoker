import java.util.LinkedList;

public class GameLogic implements GameLogicable{
    private int currentState = 0;
    private Handable humanHand;
    private Handable cpuHand;
    private int humanWon = 0;
    private int cpuWon = 0;
    private int totalPlayed = 0;
    private final Deckable deck;
    private final String humanName = "Very Human Sounding Name";
    private final String cpuName = "Super Smart Ninja AI";

    public GameLogic(){
        deck = Deck.getInstance();
        humanHand = new Hand();
        cpuHand = new Hand();
        deck.shuffle();
        humanHand.draw(deck,false);
        deck.shuffle();
        cpuHand.draw(deck,false);
    }

    @Override
    public Handable getCPUHand() {
        return humanHand;
    }

    @Override
    public Handable getHumanHand() {
        return cpuHand;
    }

    @Override
    public boolean nextState(String[] messages) throws Exception {
        boolean nextState = true;
        if(currentState==0) nextState = state0(messages);
        else if(currentState==1) nextState = state1(messages);
        else if(currentState==2) nextState = state2(messages);
        else if(currentState==3) nextState = state3(messages);
        else if(currentState==4) nextState = state4(messages);
        else nextState = state5(messages);

        currentState++;
        currentState %= MAX_GAME_STATES;
        return nextState;
    }

    private boolean state0(String[] messages){
        for (int i=0; i< 5; i++){
            humanHand.getCard(i).setFaceUp(true);
            humanHand.getCard(i).resetSelected();
            cpuHand.getCard(i).resetSelected();
        }
        messages[0] = "Beginning of Game "+(++totalPlayed);
        messages[1] = humanName+", Choose which cards to discard";
        messages[2] = "and Click on Proceed Button";
        return true;
    }
    private boolean state1(String[] messages) throws Exception {
        messages[0] = humanName+" discarded the cards";
        messages[1] = cpuName+" is \"thinking\"";
        LinkedList<Cardable> discarded = humanHand.discard();
        deck.returnToDeck(discarded);
        deck.shuffle();
        return false;
    }
    private boolean state2(String[] messages) throws Exception {
        randomSelect();
        LinkedList<Cardable> discarded = cpuHand.discard();
        deck.returnToDeck(discarded);
        deck.shuffle();
        messages[0] = cpuName+" discarded the cards";
        messages[1] = "Each Player will get the same number of cards they discarded";
        return false;
    }
    private boolean state3(String[] messages) {
        deck.shuffle();
        cpuHand.draw(deck,false);
        deck.shuffle();
        humanHand.draw(deck,true);
        for (int i=0; i<5;i++){
            humanHand.getCard(i).resetSelected();
            cpuHand.getCard(i).resetSelected();
        }
        messages[0] = "Each Player has been dealt new cards";
        messages[1] = "Click on proceed to see winner";

        return true;
    }
    private boolean state4(String[] messages){
        messages[0] = cpuName+" has: "+cpuHand.evaluateHand();
        messages[1] = humanName+" has: "+humanHand.evaluateHand();
        for (int i=0;i<5;i++){
            cpuHand.getCard(i).setFaceUp(true);
            humanHand.getCard(i).setFaceUp(true);
        }

        if(cpuHand.compareTo(humanHand) < 0) {
            messages[2] = humanName + " wins";
            humanWon++;
        } else {
            messages[2] = cpuName + " wins";
            cpuWon++;
        }

        messages[3] = humanName+" won: "+humanWon+". "+cpuName+" won: "+cpuWon;
        return true;
    }
    private boolean state5(String[] messages) throws Exception {
        deck.returnToDeck(humanHand.returnCards());
        deck.returnToDeck(cpuHand.returnCards());
        deck.shuffle();
        humanHand.draw(deck,false);
        cpuHand.draw(deck,false);

        messages[0] = "Click on proceed to play a new game";
        return true;
    }



    //method to select at random for cpu
    private void randomSelect(){
        for(int i=0; i<5; i++){
            if(Math.random()>.5 && !cpuHand.getCard(i).isSelected())
                cpuHand.getCard(i).switchSelectedState();
        }
    }
}
