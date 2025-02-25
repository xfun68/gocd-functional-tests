HungJobTermination
==================

Setup of contexts
* Basic configuration - setup
* Using pipeline "hung-job-pipeline" - setup
* With "1" live agents in directory "HungJobTermination" - setup
* Capture go state "HungJobTermination" - setup

HungJobTermination
------------------

tags: #4584, Clicky Admin

* Click on pipeline "hung-job-pipeline" for editing

* Open stage "defaultStage" - Using Pipeline Navigation

* Open jobs

* Open job "defaultJob"

* Open job settings
* Select override option
* Override job time out with "3" minutes
* Click save - Already On Job Edit Page
* Verify that job saved sucessfully

* Turn on autoRefresh - On Pipeline Dashboard Page
* Looking at pipeline "hung-job-pipeline"
* Trigger pipeline

* Looking at pipeline "hung-job-pipeline"
* Verify stage "1" is "Building" on pipeline with label "1"
* Navigate to stage "defaultStage" of run "1"

* Wait for stage result to show "Cancelled"
* Navigate to job "defaultJob"

* Open console tab
* Verify console contains "Go cancelled this job as it has not generated any console output for more than 3 minute(s)"

* Verify there are no warnings




Teardown of contexts
* Capture go state "HungJobTermination" - teardown
* With "1" live agents in directory "HungJobTermination" - teardown
* Using pipeline "hung-job-pipeline" - teardown
* Basic configuration - teardown


