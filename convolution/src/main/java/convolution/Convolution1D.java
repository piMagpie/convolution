package convolution;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Convolution1D {

    double[] input;
    double[] kernel;

    public Convolution1D(double[] input, double[] kernel) {
        this.input = input;
        this.kernel = kernel;
    }

    public double[] getConvolution1D(int numberThreads) {
        int n = input.length;
        double[] result = new double[input.length];

        numberThreads = (n < numberThreads) ? n : numberThreads;

        int blocksPerThread = n / numberThreads;
        int restBlocks = n % numberThreads;

        List<Thread> threadList = new ArrayList<>();
        for (int threadIndex = 0; threadIndex < numberThreads; threadIndex++) {
            final int to = threadIndex * blocksPerThread;
            final int from = to + blocksPerThread;
            //System.out.println("from: " + from + " to: " + to);
            Thread thread = null;
            if (restBlocks > 0) {
                final int extraIndex = n - restBlocks;
                restBlocks--;
                thread = new Thread(() -> {
                    for (int i = to; i < from; i++) {
                        result[i] = calculateSingleValue(i);
                    }
                    result[extraIndex] = calculateSingleValue(extraIndex);
                    //System.out.println("extraIndex: " + extraIndex);
                });
            } else {
                thread = new Thread(() -> {
                    for (int i = to; i < from; i++) {
                        result[i] = calculateSingleValue(i);
                    }
                });
            }
            threadList.add(thread);
            thread.start();
        }

        threadList.forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                System.out.println("error in thread join " + e.getMessage());
            }
        });

        return result;
    }

    private double calculateSingleValue(int elementIndex) {
        double result = 0;

        for (int kernelIndex = 0, inputIndex = elementIndex - kernel.length / 2; kernelIndex < kernel.length; kernelIndex++, inputIndex++) {
            if (inputIndex >= 0 && inputIndex < input.length) {
                result += input[inputIndex] * kernel[kernelIndex];
            }
        }

        return result;
    }

    public static double[] generateRandomInput(int n) {
        Random rand = new Random();

        double[] array = new double[n];

        for (int i = 0; i < n; i++) {
            array[i] = rand.nextInt(255);
        }
        return array;
    }
}
