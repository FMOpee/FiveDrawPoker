public class GameLogic implements GameLogicable{
    @Override
    public Handable getCPUHand() {
        return null;
    }

    @Override
    public Handable getHumanHand() {
        return null;
    }

    @Override
    public boolean nextState(String[] messages) {
        return false;
    }
}
