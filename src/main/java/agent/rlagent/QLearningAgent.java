package main.java.agent.rlagent;

import agent.rlagent.RLAgent;
import environnement.Action;
import environnement.Environnement;
import environnement.Etat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author laetitiamatignon
 */
public class QLearningAgent extends RLAgent {
    private Map<Etat, Map<Action, Double>> qTable = new HashMap<>();
    //VOTRE CODE
    //...


    /**
     * @param alpha
     * @param gamma
     * @param _env
     */
    public QLearningAgent(double alpha, double gamma,
                          Environnement _env) {
        super(alpha, gamma, _env);

    }


    /**
     * renvoi la (les) action(s) de plus forte(s) valeur(s) dans l'etat e
     * <p>
     * renvoi liste vide si aucunes actions possibles dans l'etat
     */
    @Override
    public List<Action> getPolitique(Etat e) {
        if(e == null || !qTable.containsKey(e)) return null;

        Double maxValue = qTable.get(e)
                .entrySet().stream()
                .mapToDouble(value -> value.getValue())
                .max().getAsDouble();

        return qTable.get(e)
                .entrySet().stream()
                .filter(actionDoubleEntry -> maxValue.equals(actionDoubleEntry.getValue()))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    /**
     * @return la valeur d'un etat
     */
    @Override
    public double getValeur(Etat e) {
        if(e.estTerminal() || !qTable.containsKey(e)) return 0;

        return qTable.get(e)
                .entrySet().stream()
                .mapToDouble(value -> value.getValue())
                .max().getAsDouble();

    }

    /**
     * @param e
     * @param a
     * @return Q valeur du couple (e,a)
     */
    @Override
    public double getQValeur(Etat e, Action a) {
        if (qTable.containsKey(e) && qTable.get(e).containsKey(a)) return qTable.get(e).get(a);
        return 0;
    }

    /**
     * setter sur Q-valeur
     */
    @Override
    public void setQValeur(Etat e, Action a, double d) {
        if (!qTable.containsKey(e)) qTable.put(e, new HashMap<>());
        qTable.get(e).put(a, d);
        this.notifyObs();
    }


    /**
     * mise a jour de la Q-valeur du couple (e,a) apres chaque interaction <etat e,action a, etatsuivant esuivant, recompense reward>
     * la mise a jour s'effectue lorsque l'agent est notifie par l'environnement apres avoir realise une action.
     *
     * @param e
     * @param a
     * @param esuivant
     * @param reward
     */
    @Override
    public void endStep(Etat e, Action a, Etat esuivant, double reward) {
        Double maxB = qTable.containsKey(esuivant) ? qTable.get(esuivant)
                .entrySet().stream()
                .mapToDouble(value -> value.getValue())
                .max().getAsDouble() : 0;
        Double d = (1 - alpha) * getQValeur(e, a) + alpha * (reward + gamma * maxB);
        setQValeur(e, a, d);
    }

    @Override
    public Action getAction(Etat e) {
        this.actionChoisie = this.stratExplorationCourante.getAction(e);
        return this.actionChoisie;
    }

    /**
     * reinitialise les Q valeurs
     */
    @Override
    public void reset() {
        qTable.clear();
    }


}
