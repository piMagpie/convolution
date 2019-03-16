package convolution;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ConvolutionUtils {


    public static int[][] getMatrixFromImage(BufferedImage img) throws IOException
    {

        // get image's width and height
        int width = img.getWidth();
        int height = img.getHeight();
        int[][] matrix = new int[height][width];

        // convert to greyscale
        for (int y = 0; y < height; y++)
        {
            for (int x = 0; x < width; x++)
            {
                // Here (x,y)denotes the coordinate of image
                // for modifying the pixel value.
                int p = img.getRGB(x,y);

                int a = (p>>24)&0xff;
                int r = (p>>16)&0xff;
                int g = (p>>8)&0xff;
                int b = p&0xff;

                // calculate average
                int avg = (r+g+b)/3;

                matrix[y][x] = avg;
            }
        }
        return matrix;
    }

    public static void writeOutputImage(String filename,BufferedImage img,int[][] avgMatrix) throws IOException
    {

        // get image's width and height
        int width = img.getWidth();
        int height = img.getHeight();
        int[][] matrix = new int[height][width];

        // convert to greyscale
        for (int y = 0; y < height; y++)
        {
            for (int x = 0; x < width; x++)
            {
                // Here (x,y)denotes the coordinate of image
                // for modifying the pixel value.
                int p = img.getRGB(x,y);

                int a = (p>>24)&0xff;

                // calculate average
                int avg = avgMatrix[y][x];

                // replace RGB value with avg
                p = (a<<24) | (avg<<16) | (avg<<8) | avg;

                img.setRGB(x, y, p);
            }
        }
        // write image
        try
        {
            File f = new File(filename);
            ImageIO.write(img, "jpg", f);
        }
        catch(IOException e)
        {
            System.out.println(e);
        }
    }


    public static void convertGrayScale(String pathOrigin, String pathEnd){
        BufferedImage img = null;
        File f = null;

        // read image
        try
        {
            f = new File(pathOrigin);
            img = ImageIO.read(f);
        }
        catch(IOException e)
        {
            System.out.println(e);
        }

        // get image's width and height
        int width = img.getWidth();
        int height = img.getHeight();

        // convert to greyscale
        for (int y = 0; y < height; y++)
        {
            for (int x = 0; x < width; x++)
            {
                // Here (x,y)denotes the coordinate of image
                // for modifying the pixel value.
                int p = img.getRGB(x,y);

                int a = (p>>24)&0xff;
                int r = (p>>16)&0xff;
                int g = (p>>8)&0xff;
                int b = p&0xff;

                // calculate average
                int avg = (r+g+b)/3;

                // replace RGB value with avg
                p = (a<<24) | (avg<<16) | (avg<<8) | avg;

                img.setRGB(x, y, p);
            }
        }

        // write image
        try
        {
            f = new File(pathEnd);
            ImageIO.write(img, "jpg", f);
        }
        catch(IOException e)
        {
            System.out.println(e);
        }
    }
}
