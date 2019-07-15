package com.pc.aws;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
/*
 * this is the class which implements RequestHandler to read the request from aws API gateway
 * lamda function for AWS API GateWay
 */

public class GateWayApi implements RequestHandler<PlansOperateUtil, JSONObject> {
	
	@Override
	public JSONObject handleRequest(PlansOperateUtil request, Context context) {
		
		String url="https://search-pc-aws-es-23vmio4fa5eux3kvwqvea55zle.us-west-1.es.amazonaws.com/plans/_search?q=";
		System.out.println("entered");
		System.out.println(request);
		System.out.println(request.planName);
		if(request.planName!=null){
			try {
				url+="PLAN_NAME:\""+URLEncoder.encode(request.planName, "UTF-8")+"\"";
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}						
		}
		
		if(request.sponsorName!=null){
			try {
				url+="SPONSOR_DFE_NAME:\""+URLEncoder.encode(request.sponsorName, "UTF-8")+"\"";
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
				}
		if(request.sponsorState!=null){
			try {
				url+="SPONS_DFE_MAIL_US_STATE:\""+URLEncoder.encode(request.sponsorState, "UTF-8")+"\"";
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		StringBuffer response = new StringBuffer(); 
		JSONObject json = new JSONObject();
		try {
			URL obj= new URL(url);
			System.out.println(url);
			HttpURLConnection con= (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("GET");
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			while((inputLine = in.readLine()) != null){
				response.append(inputLine);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		JSONParser parser = new JSONParser();
		
		try {
			json = (JSONObject) parser.parse(response.toString());
			json = (JSONObject) json.get("hits");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return json; 
		}
}
