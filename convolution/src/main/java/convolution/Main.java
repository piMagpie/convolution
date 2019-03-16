package convolution;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main {

    private static final String IMAGE_PATH = "src/main/resources/womanImage.jpg";
    private static final String RESULT_IMAGE_PATH = "src/main/resources/convolutionImage.jpg";
    private static final int[][] edgeDetection = {{-1, -1, -1}, {-1, 8, -1}, {-1, -1, -1}};
    private static final int[][] kernelIdentity = {{0, 0, 0}, {0,1,0}, {0,0,0}};

    private static final int averageNumber = 50;

    public static void main(String[] args) {

        double[] customInput1D = Convolution1D.generateRandomInput(1000000);
        double[] customKernel1D = Convolution1D.generateRandomInput(5);

        Convolution1D convolution1DCustom = new Convolution1D(customInput1D,customKernel1D);

        for (int i = 1; i <= 100; i++){
            long time = 0;
            for (int j = 0; j < averageNumber; j++) {
                long startTime = System.currentTimeMillis();
                double[] result = convolution1DCustom.getConvolution1D(i);
                long elapsedTime = System.currentTimeMillis() - startTime;
                time += elapsedTime;
            }
            time = time / averageNumber;
            System.out.println(i + "\t" + time);
        }

        System.out.println("\n\nEnd\n\n");

        BufferedImage img = null;
        File f = null;

        // read image
        try
        {
            f = new File(IMAGE_PATH);
            img = ImageIO.read(f);
            System.out.println(img.getWidth() + "  " + img.getHeight());
        }
        catch(IOException e)
        {
            System.out.println(e);
        }


        int[][] avgMatrix = null;

        try {
            avgMatrix = ConvolutionUtils.getMatrixFromImage(img);

        } catch (IOException e) {
            e.printStackTrace();
        }

        int[][] convolutedImg = null;
        for (int i = 1;i <= 100; i++){
            long time = 0;
            for (int j = 0; j < averageNumber; j++){
                long startTime = System.currentTimeMillis();
                Convolution2D convolution2DR = new Convolution2D(avgMatrix, edgeDetection);
                convolutedImg = convolution2DR.getConvolution2D(i);
                long elapsedTime = System.currentTimeMillis() - startTime;
                time += elapsedTime;
            }
            time = time / averageNumber;
            System.out.println(i + "\t" + time);
        }



        // write image
        try {
            ConvolutionUtils.writeOutputImage( RESULT_IMAGE_PATH,img,convolutedImg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
