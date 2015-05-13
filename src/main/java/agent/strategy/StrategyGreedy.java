package main.java.agent.strategy;

import agent.strategy.StrategyExploration;
import environnement.Action;
import environnement.Etat;
import main.java.agent.rlagent.RLAgent;

import java.util.List;
import java.util.Random;

/**
 * Strategie qui renvoit une action aleatoire avec probabilite epsilon, une action gloutonne (qui suit la politique de l'agent) sinon
 * Cette classe a acces a un RLAgent par l'intermediaire de sa classe mere.
 *
 * @author lmatignon
 */
public class StrategyGreedy extends StrategyExploration {
    Double epsilon;

    private Random rand = new Random();


    public StrategyGreedy(RLAgent agent, double epsilon) {
        super(agent);
        setEpsilon(epsilon);
    }

    /**
     * @return action selectionnee par la strategie d'exploration
     */
    @Override
    public Action getAction(Etat _e) {
        List<Action> actions = agent.getActionsLegales(_e);
        if (rand.nextDouble() <= epsilon) return actions.get(rand.nextInt(actions.size()));

        List<Action> bestActions = agent.getPolitique(_e);
        return bestActions.get(rand.nextInt(bestActions.size()));
    }


    public void setEpsilon(double epsilon) {
        if (epsilon > 1 || epsilon < 0) throw new RuntimeException("Epsilon doit être compris entre 0 et 1 !");
        this.epsilon = epsilon;
    }


}
