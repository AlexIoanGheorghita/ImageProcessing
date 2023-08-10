import java.util.Map;

public class Transformations {
    public Transformations() {}

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

    public static void adjustSize(int[][] imageMatrix, double sizeFactor, String newName) {
        
    }
}
