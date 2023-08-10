import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class ImageProcessor {
    public ImageProcessor() {}

    /*
     Method for creating a 2D array of pixels from an image.
     The image is retrieved from either the local filesystem or a URL.
     Each pixel will be a hexadecimal value (RGBA).
    */
    public static int[][] imageToMatrix(String path) {
        BufferedImage image = null;

        String pathPrefix = path.split("://")[0];
        // if path is a URL
        if (pathPrefix.equalsIgnoreCase("http") || pathPrefix.equalsIgnoreCase("https")) {
            try {
                URL url = new URL(path);
                image = ImageIO.read(url);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            // if it is not a URL it means it is a filesystem path
            try {
                image = ImageIO.read(new File(path));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        int imgHeight = image.getHeight();
        int imgWidth = image.getWidth();
        int[][] matrix = new int[imgHeight][imgWidth];

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                matrix[i][j] = image.getRGB(i, j);
            }
        }

        return matrix;
    }

    /*
     Method for transforming a 2D array of hexadecimal values into an image.
     We first create a new BufferedImage instance, where we specify the width, the height, and the type of value for each pixel.
     After adding each pixel value correctly from the 2D array into the image, a new local file path is created where we store the new image.
    */
    public static void matrixToImage(int[][] matrix, String location) {
        // we create an empty image
        BufferedImage image = new BufferedImage(matrix[0].length, matrix.length, BufferedImage.TYPE_INT_RGB);

        // "populating" the image
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                image.setRGB(j, i, matrix[i][j]);
            }
        }

        File filePath = new File(location);
        try {
            ImageIO.write(image, "jpg", filePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /*
     Method for retrieving an image name before converting it into a 2D array.
    */
    public static String getImageName(String path) {
        String pathPrefix = path.split("://")[0];
        // if path is a URL
        if (pathPrefix.equalsIgnoreCase("http") || pathPrefix.equalsIgnoreCase("https")) {
            return path.split("://")[1].split("/")[0].split("\\.")[0];
        } else {
            return path.split("\\\\")[path.split("\\\\").length - 1];
        }
    }

    /*
     Method for decomposing a pixel hexadecimal value into individual values for red, green, blue and alpha channels.
    */
    public static int[] getRGBAValueFromPixel(int pixel) {
        Color color = new Color(pixel);
        return new int[] {color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()};
    }

    /*
     Method for creating a pixel hexadecimal value from separate RGBA values.
    */
    public static int getIntValueFromRGBA(int[] rgba) {
        Color color = new Color(rgba[0], rgba[1], rgba[2], rgba[3]);
        return color.getRGB();
    }
}
