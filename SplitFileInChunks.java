package action;


import java.io.*;  


import java.util.Scanner;  

public class SplitFileInChunks {
	
	//split big file into chunks and return number of pieces
	public static int splitInChunks(String destinationFile) 
	{  	 int numOfChunks = 0;
		 try{  
			  // Reading file and getting no. of files to be generated  
			  //String inputfile = "/Users/krishnaprathyushavelmakanti/Downloads/F_5500_2017_Latest/temp.json"; //  Source File Name.  
			  double nol = 40000.0; //  No. of lines to be split and saved in each output file.  
			  File file = new File(destinationFile);  
			  Scanner scanner = new Scanner(file);  
			  int count = 0;  
			  while (scanner.hasNextLine())   
			  {  
			   scanner.nextLine();  
			   count++;  
			  }  
			  System.out.println("Lines in the file: " + count);     // Displays no. of lines in the input file.  
			
			  double temp = (count/nol);  
			  int temp1=(int)temp;  
			  int nof=0;  
			  if(temp1==temp)  
			  {  
			   nof=temp1;  
			  }  
			  else  
			  {  
			   nof=temp1+1;  
			  }  
			  System.out.println("No. of files to be generated :"+nof); // Displays no. of files to be generated.
			  numOfChunks = nof;
			
			  //---------------------------------------------------------------------------------------------------------  
			
			  // Actual splitting of file into smaller files  
			
			  FileInputStream fstream = new FileInputStream(destinationFile); DataInputStream in = new DataInputStream(fstream);  
			
			  BufferedReader br = new BufferedReader(new InputStreamReader(in)); String strLine;  
			
			  for (int j=1;j<=nof;j++)  
			  {  
			   FileWriter fstream1 = new FileWriter("/Users/krishnaprathyushavelmakanti/Downloads/F_5500_2017_Latest/chunk"+j+".json");     // Destination File Location  
			   BufferedWriter out = new BufferedWriter(fstream1);   
			   for (int i=1;i<=nol;i++)  
			   {  
			    strLine = br.readLine();   
			    if (strLine!= null)  
			    {  
			     out.write(strLine);   
			     if(i!=nol)  
			     {  
			      out.newLine();  
			     }
			     else {
			    	 out.write("\n");
			     }
			    }  
			   }  
			   out.close();  
			  }  
			
			  in.close();
			  
		 }catch (Exception e)  
		 {  
		  System.err.println("Error: " + e.getMessage());  
		 } 
		 
		 return numOfChunks;
	}  

}
