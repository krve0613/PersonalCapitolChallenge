package com.pc.aws;

/*
 * This class is to pass the parameters in the request and read them from GateWayApi class
 */

public class PlansOperateUtil{
	String planName;
	String sponsorName;
	String sponsorState;
	
	public String getPlanName() {
		return planName;
	}
	public void setPlanName(String planName) {
		this.planName = planName;
	}
	public String getSponsorName() {
		return sponsorName;
	}
	public void setSponsorName(String sponsorName) {
		this.sponsorName = sponsorName;
	}
	public String getSponsorState() {
		return sponsorState;
	}
	public void setSponsorState(String sponsorState) {
		this.sponsorState = sponsorState;
	}
	
	public PlansOperateUtil(String planName, String sponsorName, String sponsorState){
		this.planName = planName;
		this.sponsorName = sponsorName;
		this.sponsorState = sponsorState;
	}
	
	public PlansOperateUtil(){
		
	}
	
}