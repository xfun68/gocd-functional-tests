oAuthProviderManual
===================

oAuthProviderManual
-------------------

tags: oAuth Provider, oAuth enabled server, manual

Scenario 1: Access token that never expires

* Jane Robarts and John Alley work for Dotworks a software concern
* Jane Robarts is managing the following gadgets
* 1. Gadgets for viewing pipelines related to Train Drain project
* 2. Gadgets for view environment related to Train Drain project
* 3. Project Management tool used by jane robarts is Mingle
* John Alley is managing the following gadgets
* 1. Gadgets for viewing pipelines from go application related to Informatics project
* 2. Gadgets for viewing Environments from go application related to Informatics project
* 3. Project management tool used by John Alley is Jira and Mingle
* Both Jira and Mingle clients are supported by Go for OAuth Authentication and they know how to interpret the data generated by Go
* Clients Jira and Mingle both have consumer key and consumer access from Go Application (Provider)


* Jane Robarts needs to expose pipeline group gadget of Train Drain Project so that it could be viewed by her without logging into Go Application

* Jane Robarts (JRobarts) logs into Mingle and requests for access for go pipeline page through an user agent
* Client id should be passed to Mingle
* Since the Go Server cannot authorize, it should take Jane to Go login page
* ane Robarts (JaneR) is taken to Authorization screen (login screen) for Go
* Jane Robarts Logs in and approves the request
* Authorization token should be generated and passed back to mingle
* Mingle using the Authorization token, Client Id, Client Secret should obtain a access token
* Access token should be registered with go for Jane's user id. In this case (JaneR, Accesstoken)
* Jane Robarts logsout of Go
* Jane Robarts logs into Mingle
* She should have access to the Pipelinegroup gadget authorized by her with out passing any credentials
* She should not have access to the environment gadgets which is not authorized by her in Mingle

Scenario 2: Client (consumer) should only be able to access and fetch details of a particular user using a particular access token

* Jane Robarts and John Alley has authorized viewing their respective Pipeline gadgets in Mingle

When: Jane Robarts logs into Mingle

Then:  She should be shown only the pipeline she had approved and not John Alley's gadgets

Scenario 3: When access token expires refresh token should be used to get new access token

* Jane  access token key has expired in Mingle
* Jane tries to access Pipeline group gadget in Mingle

* Using refresh token, new access token should be obtained for Jane robarts by Mingle from Go.
* The entire operation should be seam less


Scenario 4: Invalidate token if user is disabed/removed
* System Administor has removed Jane Robart’s access rights from Go since she has joined another project
* Jane access pipeline group gadget page
* She should be shown an appropriate message related to 401 error.

Scenario 5: Access token has expired and no refresh token provided

* Access token was expired for Jane's authroization in Go
* Jane access pipeline group gadget page in Mingle
* She should be shown an appropriate message related to 401 error.

Scenario 6: User has unauthorized the client
* Jane has unauthorized mingle from accessing go for her credentials
* Jane logs into Mingle
* She should not be able to view the details related to her gadget





