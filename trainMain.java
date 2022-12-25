package skinDetection;

public class trainMain {
    public static void main(String[] args)throws Exception
    {
    	Train tr=new Train();
    	Test test = new Test();
    	double [] accuracy= new double[5];
    	for(int i=0;i<5;i++) {
    		tr.callTrain(i);
    		accuracy[i]=test.callTest(i);
    	}
      double max=accuracy[0];
      int i,maxIndex=0;
      for(i=1;i<5;i++) {
    	  if(accuracy[i]>max) {
    		  max=accuracy[i];
    		  maxIndex=i;
    	  }
      }
      tr.callTrain(maxIndex);
    }
}