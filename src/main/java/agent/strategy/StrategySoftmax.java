package main.java.agent.strategy;

import agent.strategy.StrategyExploration;
import environnement.Action;
import environnement.Etat;
import main.java.agent.rlagent.RLAgent;

import java.util.Random;

/**
 * @author lmatignon
 */
public class StrategySoftmax extends StrategyExploration {
    private Random rand = new Random();


    public StrategySoftmax(RLAgent agent, double tau) {
        super(agent);
    }

    @Override
    public Action getAction(Etat _e) {
        return null;

    }

    public void setTau(double tau) {
        // TODO Auto-generated method stub

    }


}
