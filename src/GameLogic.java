import java.util.LinkedList;

public class GameLogic implements GameLogicable{
    private int currentState = 0;
    private Handable humanHand;
    private Handable cpuHand;
    private int humanWon = 0;
    private int cpuWon = 0;
    private int totalPlayed = 0;
    private final Deckable deck;
    private final String humanName = "Player 1";
    private String cpuName = "Dumb CPU";

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
    public boolean nextState(String[] messages){
        boolean nextState;
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
    private boolean state1(String[] messages){
        messages[0] = humanName+" discarded the cards";
        messages[1] = cpuName+" is \"thinking\"";
        LinkedList<Cardable> discarded = humanHand.discard();
        deck.returnToDeck(discarded);
        deck.shuffle();
        return false;
    }
    private boolean state2(String[] messages){
        selectForCPU(true);    /************ change the false to true for making it random *********/
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
    private boolean state5(String[] messages){
        deck.returnToDeck(humanHand.returnCards());
        deck.returnToDeck(cpuHand.returnCards());
        deck.shuffle();
        humanHand.draw(deck,false);
        cpuHand.draw(deck,false);

        messages[0] = "Click on proceed to play a new game";
        return true;
    }


    //
    private void selectForCPU(boolean isRandom){
        if(isRandom) randomSelect();
        else smartSelect();
    }
    //method to select at random for cpu
    private void randomSelect(){
        cpuName = "Dumb CPU";
        for(int i=0; i<5; i++){
            if(Math.random()>.5 && !cpuHand.getCard(i).isSelected())
                cpuHand.getCard(i).switchSelectedState();
        }
    }
    //method to select
    private void smartSelect(){
        cpuName = "Smart CPU";
        double royalFlushScore = 20; // these values are the log of 1/probability
        boolean[] royalFlushSelected = new boolean[]{true,true,true,true,true};
        double straightFlushScore = 16;
        boolean[] straightFlushSelected = new boolean[]{true,true,true,true,true};
        double fourOfAKindScore = 12;
        boolean[] fourOfAKindSelected = new boolean[]{true,true,true,true,true};
        double fullHouseScore = 10;
        boolean[] fullHouseSelected = new boolean[]{true,true,true,true,true};
        double flushScore = 9;
        boolean[] flushSelected = new boolean[]{true,true,true,true,true};
        double straightScore = 8;
        boolean[] straightSelected = new boolean[]{true,true,true,true,true};
        double threeOfAKindScore = 6;
        boolean[] threeOfAKindSelected = new boolean[]{true,true,true,true,true};
        double twoPairsScore = 4;
        boolean[] twoPairsSelected = new boolean[]{true,true,true,true,true};
        double pairScore = 1;
        boolean[] pairSelected = new boolean[]{true,true,true,true,true};
//        double highCardScore = 0;
//        boolean[] highCardSelected = new boolean[]{false,false,false,false,false};

        cpuHand.sortByValue();
        /****************** royal flush ***********************/
        double multiplier = 0;
        for(int i=0; i<Handable.HAND_SIZE;i++){
            if(cpuHand.getCard(i).getValue() == 1 ||
                    cpuHand.getCard(i).getValue() == 10||
                    cpuHand.getCard(i).getValue() == 11||
                    cpuHand.getCard(i).getValue() == 12||
                    cpuHand.getCard(i).getValue() == 13
            ) {
                multiplier += .2;
                royalFlushSelected[i] = false;
            }
        }
        royalFlushScore *= multiplier;
        /**************** straight flush *************************/
        multiplier = 0;
        for(int i=4; i>=1;i--){
            if(cpuHand.getCard(i).getValue() - cpuHand.getCard(i-1).getValue() == 1){
                multiplier += .2;
                straightFlushSelected[i] = false;
            }
        }
        straightFlushScore *= multiplier;
        /**************** four of a kind **********************/
        multiplier = 0;
        for(int i=0; i<4;i++){
            if(cpuHand.getCard(i).getValue() == cpuHand.getCard(i+1).getValue()){
                multiplier += .3;
                fourOfAKindSelected[i] = false;
            }
        }
        fourOfAKindScore *= multiplier;
        /*************** full house **********************/
        multiplier = 0;
        for(int i=0; i<4;i++){
            if(cpuHand.getCard(i).getValue() == cpuHand.getCard(i+1).getValue()){
                multiplier += .25;
                fullHouseSelected[i] = false;
            }
        }
        fullHouseScore *= multiplier;
        /************** flush ************************/
        multiplier = 0;
        for(int i=0; i<4;i++){
            if(cpuHand.getCard(i).getSuit() == cpuHand.getCard(i+1).getSuit()){
                multiplier += .2;
                flushSelected[i] = false;
            }
        }
        flushScore *= multiplier;
        /************ straight **********************/
        multiplier = 0;
        for(int i=0; i<4;i++){
            if(cpuHand.getCard(i).getValue() - cpuHand.getCard(i+1).getValue() == -1){
                multiplier += .2;
                straightSelected[i] = false;
            }
        }
        straightScore *= multiplier;
        /************** three of a kind ****************/
        multiplier = 0;
        for(int i=0; i<4;i++){
            if(cpuHand.getCard(i).getValue() == cpuHand.getCard(i+1).getValue()){
                multiplier += .45;
                threeOfAKindSelected[i] = false;
            }
        }
        threeOfAKindScore *= multiplier;
        /************* two pair ******************/
        multiplier = 0;
        for(int i=0; i<4;i++){
            if(cpuHand.getCard(i).getValue() == cpuHand.getCard(i+1).getValue()){
                multiplier += .5;
                twoPairsSelected[i] = false;
            }
        }
        twoPairsScore *= multiplier;
        /************ pair *********************/
        multiplier = 0;
        for (int i=0; i<4;i++){
            if(cpuHand.getCard(i).getValue() == cpuHand.getCard(i+1).getValue()){
                multiplier += 1;
                pairSelected[i] = false;
            }
        }
        pairScore *= multiplier;

        //comparing the scores
        int[] rankings = new int[]{0,1,2,3,4,5,6,7,8};
        double[] scores = new double[]{royalFlushScore,straightFlushScore,fourOfAKindScore,fullHouseScore,flushScore,straightScore,threeOfAKindScore,twoPairsScore,pairScore};
        boolean[][] selected = new boolean[][]{royalFlushSelected,straightFlushSelected,fourOfAKindSelected,fullHouseSelected,flushSelected,straightSelected,threeOfAKindSelected,twoPairsSelected,pairSelected};
        for(int i=0; i<rankings.length; i++){
            for(int j=i+1; j<rankings.length; j++){
                if(scores[rankings[i]] < scores[rankings[j]]){
                    int temp = rankings[i];
                    rankings[i] = rankings[j];
                    rankings[j] = temp;
                }
            }
        }
        for(int i = 0; i<5; i++){
            if(selected[rankings[0]][i] && !cpuHand.getCard(i).isSelected())
                cpuHand.getCard(i).switchSelectedState();
        }

    }
}
