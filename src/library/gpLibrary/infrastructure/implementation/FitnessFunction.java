package library.gpLibrary.infrastructure.implementation;

import library.gpLibrary.infrastructure.interfaces.IFitnessFunction;
import library.gpLibrary.models.highOrder.implementation.PopulationMember;

import java.util.List;

public abstract class FitnessFunction<T> implements IFitnessFunction<T> {

    public PopulationMember<T> getFittest(List<PopulationMember<T>> list) {
        double bestFitness = getWorstPossibleValue();
        int bestIndex = 0;

        for (int i = 0; i < list.size(); i++) {
            PopulationMember<T> member = list.get(i);

            if(firstFitterThanSecond(member.getFitness(), bestFitness)){
                bestFitness = (int) member.getFitness();
                bestIndex = i;
            }
        }
        return list.get(bestIndex);
    }
}
