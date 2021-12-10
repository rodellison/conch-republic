### The Conch Republic is a Serverless Java based Amazon Alexa Skill project. 


1. **conch-republic-common**  - A simple Java module, housing data structures, enums, and utilities common to both the backend
and front end.

2. **conch-republic-skill** - A Serverless Java Alexa APL (Alexa Presentation Language) skill, which receives User requests
and works with a backend DynamoDB database to retrieve data, and subsequently present it in conversation response. Utilizes DynamoDB, SNS, and 
Translate (to perform real-time English to Spanish text conversion)

![The Conch Republic](https://s3.amazonaws.com/content.rodellison.net/Images/ConchRepublic/ConchRepublic.jpg "Conch Republic")

