package org.deeplearning4j.arbiter.scoring.impl;

import org.deeplearning4j.arbiter.scoring.RegressionValue;
import org.deeplearning4j.arbiter.scoring.util.ScoreUtil;
import org.deeplearning4j.eval.RegressionEvaluation;
import org.deeplearning4j.nn.graph.ComputationGraph;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.nd4j.linalg.dataset.api.iterator.MultiDataSetIterator;

/**
 * Score function for regression (including multi-label regression) for a MultiLayerNetwork on a test set
 *
 * @author Alex Black
 */
public class TestSetRegressionScoreFunction extends BaseNetScoreFunction {
    private final RegressionValue regressionValue;

    /**
     * @param regressionValue The type of evaluation to do: MSE, MAE, RMSE, etc
     */
    public TestSetRegressionScoreFunction(RegressionValue regressionValue) {
        this.regressionValue = regressionValue;
    }


    @Override
    public boolean minimize() {
        return regressionValue != RegressionValue.CorrCoeff; //Maximize correlation coefficient, minimize the remaining ones
    }

    @Override
    public String toString() {
        return "TestSetRegressionScoreFunction(type=" + regressionValue + ")";
    }

    @Override
    public double score(MultiLayerNetwork net, DataSetIterator iterator) {
        RegressionEvaluation e = net.evaluateRegression(iterator);
        return ScoreUtil.getScoreFromRegressionEval(e, regressionValue);
    }

    @Override
    public double score(MultiLayerNetwork net, MultiDataSetIterator iterator) {
        throw new UnsupportedOperationException("Cannot evaluate MultiLayerNetwork on MultiDataSetIterator");
    }

    @Override
    public double score(ComputationGraph graph, DataSetIterator iterator) {
        RegressionEvaluation e = graph.evaluateRegression(iterator);
        return ScoreUtil.getScoreFromRegressionEval(e, regressionValue);
    }

    @Override
    public double score(ComputationGraph graph, MultiDataSetIterator iterator) {
        RegressionEvaluation e = graph.evaluateRegression(iterator);
        return ScoreUtil.getScoreFromRegressionEval(e, regressionValue);
    }
}
