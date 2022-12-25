package skinDetection;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javax.imageio.ImageIO;

public class testMain {

	public static void main(String[] args) throws IOException {
		 double[][][] probability = new double[256][256][256];
	        Scanner sc = new Scanner(new File("filename.txt"));
	        for (int i = 0; i < 256; i++) {

	            for (int j = 0; j < 256; j++) {

	                for (int k = 0; k < 256; k++) {
	                    probability[i][j][k] = sc.nextDouble();
	                }
	            }
	        }


	            File testFile = new File("input.jpg");

	            BufferedImage testImg = ImageIO.read(testFile);

	            for (int y = 0; y < testImg.getHeight(); y++) {

	                for (int x = 0; x < testImg.getWidth(); x++) {

	//Retrieving contents of a pixel

	                    int testPixel = testImg.getRGB(x, y);

	//Creating a Color object from pixel value

	                    Color testColor = new Color(testPixel, true);
	                    Color myWhite = new Color(255, 255, 255); // Color white
	                    int rgb = myWhite.getRGB();

	//Retrieving the R G B values

	                    int testRed = testColor.getRed();

	                    int testGreen = testColor.getGreen();

	                    int testBlue = testColor.getBlue();

	                    if (probability[testRed][testGreen][testBlue] < 0.4) {
	                        testImg.setRGB(x, y, rgb);
	                    }
	                }
	            }

	            File outputfile = new File("output.jpg");
	            ImageIO.write(testImg, "jpg", outputfile);
	            System.out.println("done");
	        }

	}


