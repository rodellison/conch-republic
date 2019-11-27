### conch-republic-backend 

This module is for the API Gateway/Lambda Serverless Vertx backend, which will provide
two simple APIs (loaddata, getdata). 

**/loaddata** is the main interface to invoke functionality which will:
1. Obtain an array of 12 search query dates in YYYYMM format
2. Iterate through that array to construct a URL String and http get the remote
data containing a list of calendar events in raw html form
3. For each raw HTML scrape, parse and extract the EventItem elements into an Hashmap (used to 
eliminate dups, as many of the Keys events go on for months)
4. Convert the Hashmap unique items to an ArrayList sorted by StartDate
5. Iterate the ArrayList of EventItems and insert into DynamoDB.

/getdata/{location} is a secondary interface just to see the items in the DynamoDB



Environmental Variables Required:

DYNAMO_DB_ENV 			= 	**Prod** (or **Dev**  if testing locally)
CONCH_REPUBLIC_BASE_URL = 	https://fla-keys.com/calendar/all
DYNAMO_DB_TABLENAME 	= 	FloridaKeysEvents
