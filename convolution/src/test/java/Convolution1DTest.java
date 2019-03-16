import java.util.Arrays;

import convolution.Convolution1D;
import convolution.Convolution2D;
import org.junit.Assert;
import org.junit.Test;


public class Convolution1DTest {

    private static double[] input1D = {1.0,0.0,2.0,1.0};
    private static double[] kernel1D = {1,0,2};
    private static double[] resultOK = {0.0, 5.0, 2.0, 2.0};
    @Test
    public void smallConvolution1D(){

        Convolution1D convolution1D = new Convolution1D(input1D,kernel1D);
        double[] result;

        for (int i = 1; i < input1D.length; i++){
            result = convolution1D.getConvolution1D(i);
            System.out.println(Arrays.toString(result));
        }
    }

    @Test
    public void bigConvolution1D(){
        double[] customInput1D = Convolution1D.generateRandomInput(100);
        double[] customKernel1D = Convolution1D.generateRandomInput(5);

        Convolution1D convolution1DCustom = new Convolution1D(customInput1D,customKernel1D);

        for (int i = 1; i <= customInput1D.length; i++){
            System.out.println(Arrays.toString(convolution1DCustom.getConvolution1D(i)));
        }
    }
}
