package com.compomics;

import com.compomics.commons.generalutilities.ArrayUtilities;
import org.apache.commons.math3.analysis.polynomials.PolynomialFunction;
import org.apache.commons.math3.fitting.PolynomialCurveFitter;
import org.apache.commons.math3.fitting.WeightedObservedPoint;
import org.apache.commons.math3.fitting.WeightedObservedPoints;

import java.util.List;

/**
 * Created by Davy Maddelein on 29/06/2015.
 */
public class TippingPoint {


    static WeightedObservedPoints points = new WeightedObservedPoints();


    public static void main(String[] args) {

        for (int anInt : ArrayUtilities.consecutiveInts(50))

        {
            points.add(anInt, anInt);
        }

        for (int anInt : ArrayUtilities.consecutiveInts(50)) {
            points.add(50 + anInt, anInt+5);
        }


        PolynomialCurveFitter fitter = PolynomialCurveFitter.create(2);

        PolynomialFunction fittedFunction = new PolynomialFunction(fitter.fit(points.toList()));

        List<WeightedObservedPoint> pointsList = points.toList();

        for (WeightedObservedPoint aPointsList : pointsList) {

            System.out.println(aPointsList.getY());
            System.out.println(fittedFunction.derivative().value(aPointsList.getY()));


        }

    }

}
