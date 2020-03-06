### The Conch Republic is a Serverless Java based Amazon Alexa Skill project. 


1. **conch-republic-backend**  - A Java Vert.x module, implemented as a serverless API Gateway/Lambda process.
Note: Vert.x used here only to gain experience.. would NOT recommend using Vert.x in Lambda due to startup time, and general data flow
between api gateway to lambda. It can be used, but essentially enforces a long running API request. In hindsight, Vert.x in an always on 
ECS micro server instance would be better (but of course carry a monthly cost)
As written, this module in fronted by API Gateway invoked by a (task scheduled) powershell script to curl the API Gateway URL. 
The module recieves the request and performs 3 tasks, orchestrated by way of a Vert.x event bus.. 
    1. Fetch raw html data from the external Florida Keys web site.. yeah, ugly - but theres no API available.
    2. Parse out the HTML for event specific data elements creating a EventItems placed in an EventList
    3. Iterate the EventList and insert the data into DynamoDB. Existing matching data simply overwritten._

2. **conch-republic-common**  - A simple Java module, housing data structures, enums, and utilities common to both the backend
and front end.

2. **conch-republic-skill** - A Serverless Java Alexa APL (Alexa Presentation Language) skill, which receives User requests
and works with a backend DynamoDB database to retrieve data, and subsequently present it in conversation response. Utilizes DynamoDB, SNS, and 
Translate (to perform real-time English to Spanish text conversion)

![The Conch Republic](https://s3.amazonaws.com/content.rodellison.net/Images/ConchRepublic/ConchRepublic.jpg "Conch Republic")

