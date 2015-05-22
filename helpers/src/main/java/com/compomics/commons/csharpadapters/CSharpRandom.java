package com.compomics.commons.csharpadapters;


/**
 * Created by Davy Maddelein on 28/04/2015.
 */
public class CSharpRandom {

    int inext;
    int inextp;
    int[] seedArray = new int[56];


    public CSharpRandom(int seed) {

        int num1 = 161803398 - (seed == -2147483647 ? 2147483647 : Math.abs(seed));
        seedArray[55] = num1;
        int num2 = 1;
        for (int index1 = 1; index1 < 55; ++index1) {
            int index2 = 21 * index1 % 55;
            seedArray[index2] = num2;
            num2 = num1 - num2;
            if (num2 < 0)
                num2 += 2147483647;
            num1 = seedArray[index2];
        }
        for (int index1 = 1; index1 < 5; ++index1) {
            for (int index2 = 1; index2 < 56; ++index2) {
                seedArray[index2] -= seedArray[1 + (index2 + 30) % 55];
                if (seedArray[index2] < 0)
                    seedArray[index2] += 2147483647;
            }
        }
        this.inext = 0;
        this.inextp = 21;
        seed = 1;
    }

    public int next() {
        return internalSample();
    }

    protected double sample() {
        return (double) internalSample() * 4.6566128752458E-10;
    }

    private int internalSample() {
        int num1 = this.inext;
        int num2 = this.inextp;
        int index1;
        if ((index1 = num1 + 1) >= 56)
            index1 = 1;
        int index2;
        if ((index2 = num2 + 1) >= 56)
            index2 = 1;
        int num3 = seedArray[index1] - seedArray[index2];
        if (num3 == 2147483647)
            --num3;
        if (num3 < 0)
            num3 += 2147483647;
        this.seedArray[index1] = num3;
        this.inext = index1;
        this.inextp = index2;
        return num3;
    }

    private double getSampleForLargeRange() {
        int num = internalSample();
        if (internalSample() % 2 == 0)
            num = -num;
        return ((double) num + 2147483646.0) / 4294967293.0;
    }

    public int next(int minValue, int maxValue) throws IllegalArgumentException{
        if (minValue > maxValue) {
        throw new IllegalArgumentException("minimum value: " + minValue + " was bigger than max value: " + maxValue);
        }
        long num = (long) maxValue - (long) minValue;
        if (num <= (long) 2147483647)
            return (int) (sample() * (double) num) + -2147483647;
        return (int) ((long) (getSampleForLargeRange() * (double) num) + (long) minValue);
    }
}
