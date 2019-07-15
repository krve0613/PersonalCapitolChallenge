# PersonalCapitolChallenge

Challenge:
Using Java, write a microservice that invokes AWS elastic search and make it available using API gateway.

Step 1:
1. Upload the data to AWS elastic search.
2. Open the AWS console and create Elastic search domain. 
3. Firstly Convert csv file to json because AWS-ES accepts only json data.
4. There is a limit on json file size to upload to AWS-ES. So make the json into small chunks so that we can upload the data piece by piece.
command to upload json file to domain is: 
curl -XPOST elasticsearch_domain_endpoint/_bulk --data-binary @chunk1.json -H 'Content-Type: application/json'
Files:
ConvertAndAddDataToAWSES.java
SplitFileInChunks.java



Step 2:
1.	Create a lamda function to create API gate way and make it available to use it from client from AWS console.
2.	Now write a code using maven project to make the functionality working according to the requirements.
3.	Upload the jar file and make the required configuration in AWS API Gateway

Files: 
GateWayApi.java
PlansOperateUtil.java

EndPoints to use:

https://ccef29lyql.execute-api.us-west-1.amazonaws.com/api/pc-aws-es-api
This API accepts 3 parameters:
1. planName
2. sponsorState
3. sponsorName

https://ccef29lyql.execute-api.us-west-1.amazonaws.com/api/pc-aws-es-api?planName=tiffany
https://ccef29lyql.execute-api.us-west-1.amazonaws.com/api/pc-aws-es-api?sponsorName=A & A
https://ccef29lyql.execute-api.us-west-1.amazonaws.com/api/pc-aws-es-api?sponsorState=ca

We can search using below api as well to give all the 3 inputs
https://ccef29lyql.execute-api.us-west-1.amazonaws.com/api/pc-aws-es-api?planName=XXX&sponsorName=XXX&sponsorState=XXX
