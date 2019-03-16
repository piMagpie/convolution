import java.util.Arrays;

import convolution.Convolution1D;
import convolution.Convolution2D;
import org.junit.Test;


public class Convolution2DTest {

    private static int[][] input2D = {{1,2,3},{4,5,6},{7,8,9}};
    private static int[][] kernel2D = {{-1,-2,-1},{0,0,0},{1,2,1}};


    @Test
    public void smallConvolution2D(){

        Convolution2D convolution2D = new Convolution2D(input2D,kernel2D);
        Convolution2D.printMatrix(input2D);
        Convolution2D.printMatrix(kernel2D);

        Convolution2D.printMatrix(convolution2D.getConvolution2D());
        for (int i = 1; i <= input2D.length * input2D[0].length; i++){
            Convolution2D.printMatrix(convolution2D.getConvolution2D(i));
        }
    }

}
