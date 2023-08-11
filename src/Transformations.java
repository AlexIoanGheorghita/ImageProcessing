import java.awt.image.BufferedImage;
import java.util.Map;

public class Transformations {
    public Transformations() {}

    /*
     Method for cropping an image in all directions - top, right, bottom, left
    */
    public static int crop(int[][] imageMatrix, Map<String, Integer> sideValues, String newName) {
        Integer left = sideValues.get("left");
        Integer right = sideValues.get("right");
        Integer top = sideValues.get("top");
        Integer bottom = sideValues.get("bottom");

        int originalImgHeight = imageMatrix.length;
        int originalImgWidth = imageMatrix[0].length;
        int[][] croppedImage = null;

        if (left != null && right != null && top != null && bottom != null) {
            if ((left + right) < imageMatrix[0].length && (top + bottom) < imageMatrix.length) {
                croppedImage = new int[originalImgHeight - top - bottom][originalImgWidth - left - right];

                for (int i = top; i < (originalImgHeight - bottom); i++) {
                    for (int j = left; j < (originalImgWidth - right); j++) {
                        croppedImage[i-top][j-left] = imageMatrix[i][j];
                    }
                }

                ImageProcessor.matrixToImage(croppedImage, "./images/crop_" + newName);
                return 0;
            } else {
                System.out.println("One or more cropping values exceed the image dimensions.");
                return -1;
            }
        } else {
            System.out.println("One or more cropping values has not been given.");
            return -1;
        }
    }

    /*
     Method for making an image brighter or darker
    */
    public static void changeBrightness(int[][] imageMatrix, int offset, String newName) {
        int[][] newImage = new int[imageMatrix.length][imageMatrix[0].length];

        for (int i = 0; i < imageMatrix.length; i++) {
            for (int j = 0; j < imageMatrix[0].length; j++) {
                int[] rgba = ImageProcessor.getRGBAValueFromPixel(imageMatrix[i][j]);

                int red = rgba[0] + offset;
                int green = rgba[1] + offset;
                int blue = rgba[2] + offset;

                if (red < 0) {
                    red = 0;
                } else if (red > 255){
                    red = 255;
                }

                if (green < 0) {
                    green = 0;
                } else if (green > 255){
                    green = 255;
                }

                if (blue < 0) {
                    blue = 0;
                } else if (blue > 255){
                    blue = 255;
                }

                newImage[i][j] = ImageProcessor.getIntValueFromRGBA(new int[]{red, green, blue, rgba[3]});
            }
        }

        ImageProcessor.matrixToImage(newImage, "./images/brightness_" + newName);
        System.out.println("Adjusted brightness of image.");
    }

    /*
     Method for enlarging or shrinking an image. Here it is more difficult to work directly with pixels (2D array),
     especially when it comes to enlarging or shrinking an image by a decimal number. For a whole number it would be much easier.
     For example, if the scaleFactor would be 2, then we would create a 2D array with twice the height and width of the original array.
     Then, every second pixel position we would place the same pixel as in the previous position.
    */
//    public static void scale(int[][] imageMatrix, double scaleFactor, String newName) {
//        // We need to transform the 2D array into an image.
//        int[][] newImage = new int[(int)(imageMatrix.length * scaleFactor)][(int)(imageMatrix[0].length * scaleFactor)];
//
//        int loopCounter = 0;
//
//        while (loopCounter < newImage.length) {
//            for (int j = 0; j < newImage[0].length; j++) {
//
//            }
//
//            loopCounter++;
//        }
//    }

    public static void flipVertically(int[][] imageMatrix, String newName) {
        int[][] flippedImage = flip(imageMatrix, "vertical");
        ImageProcessor.matrixToImage(flippedImage, "./images/flip_vertical_" + newName);
        System.out.println("Flipped image vertically");
    }

    public static void flipHorizontally(int[][] imageMatrix, String newName) {
        int[][] flippedImage = flip(imageMatrix, "horizontal");
        ImageProcessor.matrixToImage(flippedImage, "./images/flip_horizontal_" + newName);
        System.out.println("Flipped image horizontally");
    }

    /*
     Utility method for flipping an image (mirroring) either vertically or horizontally
    */
    private static int[][] flip(int[][] imageMatrix, String direction) {
        int[][] newImage = new int[imageMatrix.length][imageMatrix[0].length];

        // Using row-major order for vertical flip
        if (direction.equals("vertical")) {
            for (int i = 0; i < imageMatrix.length; i++) {
                for (int j = 0; j < imageMatrix[0].length; j++) {
                    newImage[i][j] = imageMatrix[i][(imageMatrix[i].length - 1) - j];
                }
            }
        // Using column-major order for horizontal flip
        } else if (direction.equals("horizontal")) {
            for (int j = 0; j < imageMatrix[0].length; j++) {
                for (int i = 0; i < imageMatrix.length; i++) {
                    newImage[i][j] = imageMatrix[(imageMatrix[j].length - 1) - i][j];
                }
            }
        }

        return newImage;
    }
}
