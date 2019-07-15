package action;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class ConvertAndAddDataToAWSES {
	
	//Provide the location of the destination file
	public static String destinationFile = "/Users/krishnaprathyushavelmakanti/Downloads/F_5500_2017_Latest/result.json";
	public static String dir = "/Users/krishnaprathyushavelmakanti/Downloads/F_5500_2017_Latest";
	
	public static void main(String[] args) throws Exception{
		
		//converting from csv to JSON file
		convertToJSON();
		
		//split json to chunks
		SplitFileInChunks SF = new SplitFileInChunks();
		int numOfChunks = SF.splitInChunks(destinationFile);
		
		//upload each chunk file to AWS ES using curl
		for(int i =1; i<= numOfChunks; i++) {
			String chunkFileName = dir+"/chunk"+i+".json";
			uploadDatatoAWS(chunkFileName);
		}
		
		
	}
	
	//converting from csv to JSON file
	public static void convertToJSON() throws Exception{
		
		//Provide the location of the destination file
		//String FILENAME1 = "/Users/krishnaprathyushavelmakanti/Downloads/F_5500_2017_Latest/result.json"; 							
		File file1 = new File(destinationFile);

		FileWriter writer = new FileWriter(file1, true);

		Class.forName("org.relique.jdbc.csv.CsvDriver");
		
		// Create a connection. The first command line parameter is
		// the directory containing the .csv files.
		// A single connection is thread-safe for use by several threads.
		Connection conn = DriverManager.getConnection("jdbc:relique:csv:" + "/Users/krishnaprathyushavelmakanti/Downloads/F_5500_2017_Latest");	//Source CSV file
		
		// Create a Statement object to execute the query with.
		Statement stmt = conn.createStatement();
		String query= "SELECT * FROM f_5500_2017_latest";					//f_5500_2017_latest is the source file
		
		// Select the ID and NAME columns from csvdata.csv
		ResultSet results = stmt.executeQuery(query);
		ResultSetMetaData metadata = results.getMetaData();
		int numColumns = metadata.getColumnCount();
		int count=1;
		while(results.next())             //iterate rows
		{
			JSONObject obj = new JSONObject();      //extends HashMap
			for (int i = 1; i <= numColumns; ++i)           //iterate columns
			{
			    String column_name = metadata.getColumnName(i);
			    obj.put(column_name, results.getObject(column_name));
			    if(column_name.equals("SPONS_SIGNED_DATE") ) {
			    	
			    	if(results.getObject(column_name).equals("")) {
			    		
			    		obj.put(column_name, null);
			    	}
			    }
			}

			writer.write("{ \"index\" : { \"_index\": \"plans\", \"_type\" : \"listings\", \"_id\" : \""+count+"\" } }\n");
			writer.write(obj.toString());
			writer.write("\n");
			count++;
			
		}
		
		// Clean up
		conn.close();
	}
	
	//upload each chunk file to AWS ES using curl
	public static void uploadDatatoAWS(String bulkFileName) {

			
			String url = "https://search-pc-aws-es-23vmio4fa5eux3kvwqvea55zle.us-west-1.es.amazonaws.com";
			
			//curl -XPOST elasticsearch_domain_endpoint/_bulk --data-binary @bulk_movies.json -H 'Content-Type: application/json'
			//Equivalent command conversion for Java execution
			String[] command = { "curl","-X", "POST", url+"/_bulk","--data-binary","@" + bulkFileName,  
					"-H",  "Content-Type: application/json"};
			

			ProcessBuilder process = new ProcessBuilder(command);
			Process p;
			try {
				p = process.start();
				BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
				StringBuilder builder = new StringBuilder();
				String line = null;
				while ((line = reader.readLine()) != null) {
					builder.append(line);
					builder.append(System.getProperty("line.separator"));
				}
				String result = builder.toString();
				System.out.print(result);

			} catch (IOException e) {
				System.out.print("error");
				e.printStackTrace();
			}
		
	}
}
