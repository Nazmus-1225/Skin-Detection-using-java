package skinDetection;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class Test {
	
	double [][][] probability= new double[256][256][256];
    File directoryPath = new File("dataset\\Mask");
    File directoryPath1 = new File("dataset\\ibtd");
    File filesList[] = directoryPath.listFiles();
    File filesList1[] = directoryPath1.listFiles();
    double [][] confusionMatrix=new double[2][2];

    public double callTest(int fold) throws Exception {
        BufferedReader br=new BufferedReader(new FileReader("filename.txt"));
        Arrays.sort(filesList);
        Arrays.sort(filesList1);
        int start=fold*111;
        int end=start+110;
        
        for (int i = 0; i < 256; i++) {

            for (int j = 0; j < 256; j++) {

                for (int k = 0; k < 256; k++) {
                    probability[i][j][k] = Double.parseDouble(br.readLine());
                   
                }
            }
        }
        
        for (int i = 0; i < 2; i++) {

            for (int j = 0; j < 2; j++) {

                confusionMatrix[i][j]=0;}
        }

        for(int i=start;i<=end;i++) {
        		test(i);}
        
        double precision=confusionMatrix[0][0]/(confusionMatrix[0][0]+confusionMatrix[0][1]);
        double recall=confusionMatrix[0][0]/(confusionMatrix[0][0]+confusionMatrix[1][0]);
        double fScore=(2*precision*recall)/(precision+recall);
        return fScore;
        }
    
    private void test(int i) throws IOException {
    	 File testFile = new File(filesList1[i].getAbsolutePath());

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
         BufferedImage outImg =ImageIO.read(outputfile);
         File maskFile = new File(filesList[i].getAbsolutePath());
         BufferedImage maskImg = ImageIO.read(maskFile);
         
         for (int y = 0; y < maskImg.getHeight(); y++)
         {

             for (int x = 0; x < maskImg.getWidth(); x++)
             {
                 int maskPixel = maskImg.getRGB(x,y);
                 Color maskColor = new Color(maskPixel, true);
                 int pixel = outImg.getRGB(x,y);
                 Color outColor = new Color(pixel, true);
                 Color myWhite = new Color(255, 255, 255);
                 if(!(maskColor.equals(myWhite)) && !(outColor.equals(myWhite))) {
                	 confusionMatrix[0][0]++;
                 }
                 else if((maskColor.equals(myWhite)) && (outColor.equals(myWhite))) {
                	 confusionMatrix[1][1]++;
                 }
                 else if((maskColor.equals(myWhite)) && !(outColor.equals(myWhite))) {
                	 confusionMatrix[0][1]++;
                 }
                 else if(!(maskColor.equals(myWhite)) && (outColor.equals(myWhite))) {
                	 confusionMatrix[1][0]++;
                 }
                 
             }}
         
    }

}
