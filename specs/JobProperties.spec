JobProperties
=============

Setup of contexts
* Basic configuration - setup
* Using pipeline "basic-pipeline-fast-with-job-properties" - setup
* With "1" live agents in directory "StageDetails" - setup
* Capture go state "JobProperties" - setup

JobProperties
-------------

tags: job properties, 2251, 2512, automate

* With material named "materialWithJobProperties" in pipeline "basic-pipeline-fast-with-job-properties"
* Commit file "junit-failures/fail-a-pass-b/TEST-cruise.testing.JUnit.xml" to directory "junit-output"

* Looking at pipeline "basic-pipeline-fast-with-job-properties"
* Trigger pipeline
* Wait for stage "defaultStage" status to be "Passed" with label "1"
* Navigate to stage "defaultStage" of run "1"

* Navigate to job "defaultJob"

* Open console tab
* Verify console contains "Start to create properties"
* Verify console contains "Failed to create property illegal.file. File"
* Verify console contains "Failed to create property illegal.xpath. Illegal xpath: \"!@\""
* Verify console contains "Failed to create property xpath.not.found. Nothing matched xpath \"buhao\" in the file"
* Verify console contains "Failed to create property src.is.folder."
* Verify console contains "Property suite.time = 2.0 created"
* Open properties tab

On Properties Tab
* OnPropertiesTab 
     |property name          |exists?|
     |-----------------------|-------|
     |cruise_agent           |true   |
     |cruise_job_duration    |true   |
     |cruise_job_id          |true   |
     |cruise_job_result      |true   |
     |cruise_pipeline_counter|true   |
     |cruise_pipeline_label  |true   |
     |cruise_stage_counter   |true   |
     |suite.time             |true   |






Teardown of contexts
* Capture go state "JobProperties" - teardown
* With "1" live agents in directory "StageDetails" - teardown
* Using pipeline "basic-pipeline-fast-with-job-properties" - teardown
* Basic configuration - teardown


