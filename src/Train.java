package skinDetection;

import java.io.File;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;

public class Train{
	
	double [][][] skin= new double[256][256][256];
    double [][][] nonSkin= new double[256][256][256];
    double [][][] probability= new double[256][256][256];
    
    File directoryPath = new File("dataset\\Mask");
    File directoryPath1 = new File("dataset\\ibtd");
    File filesList[] = directoryPath.listFiles();
    File filesList1[] = directoryPath1.listFiles();

    public void callTrain(int fold)throws Exception
    {
        int start=fold*111;
        int end=start+110;
        FileWriter myWriter = new FileWriter("filename.txt");

        

        for(int i=0;i<256;i++)
        {
            for(int j=0;j<256;j++)
            {
                for(int k=0;k<256;k++)
                {
                    skin[i][j][k]=0;
                    nonSkin[i][j][k]=0;
                    probability[i][j][k]=0;
                }

            }

        }
        

        Arrays.sort(filesList);
        Arrays.sort(filesList1);

        for(int i=0;i<start;i++) {
        	train(i);
        }
        
        for(int i=end+1;i<555;i++) {
        	train(i);
        }

            
        for(int i=0;i<256;i++)
        {
            for(int j=0;j<256;j++)
            {
                for(int k=0;k<256;k++)
                {
                    if(nonSkin[i][j][k]!=0)

                        probability[i][j][k]=skin[i][j][k]/nonSkin[i][j][k];
                    myWriter.write(String.valueOf(probability[i][j][k])+"\n");


                }

            }


        }

        myWriter.close();

    }
    
    private void train(int i) throws IOException {
    	File nonMask= new File(filesList1[i].getAbsolutePath());
    	File mask= new File(filesList[i].getAbsolutePath());
    	
    	BufferedImage maskImg = ImageIO.read(mask);
        BufferedImage img = ImageIO.read(nonMask);

        for (int y = 0; y < maskImg.getHeight(); y++)
        {

            for (int x = 0; x < maskImg.getWidth(); x++)
            {


                int maskPixel = maskImg.getRGB(x,y);

                Color maskColor = new Color(maskPixel, true);

                int maskRed = maskColor.getRed();

                int maskGreen = maskColor.getGreen();

                int maskBlue = maskColor.getBlue();

                int pixel = img.getRGB(x,y);


                Color color = new Color(pixel, true);


                int red = color.getRed();

                int green = color.getGreen();

                int blue = color.getBlue();


                if(maskRed==255 && maskGreen==255 && maskBlue==255)
                {
                    nonSkin[red][green][blue]++;
                }
                else
                {
                    skin[red][green][blue]++;
                }
            }

        }

    
    }

}

