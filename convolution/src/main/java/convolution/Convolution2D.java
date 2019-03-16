package convolution;

import java.util.ArrayList;
import java.util.List;

public class Convolution2D {

    private int[][] image;
    private int[][] kernel;
    private int height = 0;
    private int width = 0;

    public Convolution2D(int[][] image, int[][] kernel) {
        this.image = image;
        this.kernel = flip(kernel);
        this.height = image.length;
        this.width = image[0].length;
    }

    public int[][] getConvolution2D(){

        int[][] result = new int[height][width];
        for (int i = 0; i < height; i++){
            for (int j = 0; j < image[i].length; j++){
                result[i][j] = calculateSingleValue(i,j);
            }
        }

        return result;
    }

    public int[][] getConvolution2D(int numberThreads){


        int n = height * width;
        int[][] result = new int[height][width];

        numberThreads = (n < numberThreads) ? n : numberThreads;

        int blocksPerThread = n / numberThreads;
        int restBlocks = n % numberThreads;

        List<Thread> threadList = new ArrayList<>();


        for ( int threadIndex = 0; threadIndex < numberThreads; threadIndex++){
            final int to = threadIndex * blocksPerThread;
            final int from = to + blocksPerThread;
            //System.out.println("from: " + from + " to: " + to);
            Thread thread = null;
            if ( restBlocks > 0){
                final int extraIndex = n - restBlocks;
                restBlocks--;
                thread = new Thread(() -> {
                    for ( int index = to; index < from; index++){
                        result[getX(index)][getY(index)] = calculateSingleValue(getX(index),getY(index));
                    }
                    result[getX(extraIndex)][getY(extraIndex)] = calculateSingleValue(getX(extraIndex),getY(extraIndex));
                });
            }else{
                thread = new Thread(() -> {
                    for ( int index = to; index < from; index++){
                        result[getX(index)][getY(index)] = calculateSingleValue(getX(index),getY(index));
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

    private int getX(int index){
        return index / width;
    }

    private int getY(int index){
        return  index % width;
    }

    private int calculateSingleValue(int i,int j){
        int result = 0;

        for ( int kernelIndexX = 0, inputIndexX = i - kernel.length / 2; kernelIndexX < kernel.length; kernelIndexX++, inputIndexX++){
            if (inputIndexX >= 0 && inputIndexX < image.length){
                for ( int kernelIndexY = 0, inputIndexY = j - kernel[kernelIndexX].length / 2; kernelIndexY< kernel[kernelIndexX].length; kernelIndexY++, inputIndexY++) {
                    if (inputIndexY >= 0 && inputIndexY < image[inputIndexX].length){
                        result += image[inputIndexX][inputIndexY] * kernel[kernelIndexX][kernelIndexY];
                    }
                }
            }
        }

        return result;
    }

    private int[][] flip(final int[][] matrix){

        int[][] flippedMatrix = new int[matrix.length][];

        for ( int i = 0; i < matrix.length; i++){
            int[] vector = new int[matrix[i].length];
            for ( int j = 0; j < matrix[i].length; j++){
                vector[j] = matrix[matrix.length-i-1][matrix[i].length-j-1];
            }
            flippedMatrix[i] = vector;
        }
        return flippedMatrix;
    }

    public static void printMatrix(int[][] matrix){
        System.out.println();
        for (int i = 0; i < matrix.length; i++){
            for (int j = 0; j < matrix[i].length; j++){
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
}
