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
ConvertAndAddDataToAWSES.java
SplitFileInChunks.java



Step 2:
1.	Create a lamda function to create API gate way and make it available to use it from client from AWS console.
2.	Now write a code using maven project to make the functionality working according to the requirements.
3.	Upload the jar file and make the required configuration in AWS API Gateway
