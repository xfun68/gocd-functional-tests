JobRerunRunMultipleInstance
===========================

Setup of contexts
* Basic configuration - setup
* Using pipeline "pipeline-for-job-rerun-run-multiple-instance" - setup
* With "1" live agents in directory "JobRerunRunMultipleInstance" - setup
* Capture go state "JobRerunRunOnAllAgents" - setup

JobRerunRunMultipleInstance
---------------------------

tags: stage-details, job-rerun, scheduling, rerun, run-multiple-instance, rails4only

* Click on pipeline "pipeline-for-job-rerun-run-multiple-instance" for editing

* Open stage listing page

* Open stage "defaultStage"

* Open jobs

* Open job "first"

* Open job settings
* Check run multiple instance with "2"
* Click save - Already On Job Edit Page
* Verify that job saved sucessfully

* Looking at pipeline "pipeline-for-job-rerun-run-multiple-instance"
* Trigger pipeline
* Wait for first stage to pass with pipeline label "1"
* Navigate to stage "defaultStage" of run "1" having counter "1"

* Go to jobs tab

* Verify job "first-runInstance-1" has state "Completed" and result "Passed"
* Verify job "first-runInstance-2" has state "Completed" and result "Passed"
* Navigate to "first-runInstance-1" job

* Open console tab
* Verify console has environment variable "GO_JOB_RUN_COUNT" with value "2"
* Verify console has environment variable "GO_JOB_RUN_INDEX" with value "1"
* Click on bread crumb "defaultStage / 1"

* Navigate to job "first-runInstance-2"

* Open console tab
* Verify console has environment variable "GO_JOB_RUN_COUNT" with value "2"
* Verify console has environment variable "GO_JOB_RUN_INDEX" with value "2"
* Click on bread crumb "defaultStage / 1"

* Go to jobs tab

* Rerun "first-runInstance-2" jobs

* Looking at pipeline "pipeline-for-job-rerun-run-multiple-instance"
* Wait for first stage to pass with pipeline label "1"
* Navigate to stage "defaultStage" of run "1" having counter "2"

* Go to jobs tab

* Verify job "first-runInstance-1" has state "Completed" and result "Passed"
* Verify job "first-runInstance-2" has state "Completed" and result "Passed"
* Navigate to "first-runInstance-2" job

* Open console tab
* Verify console has environment variable "GO_JOB_RUN_COUNT" with value "2"
* Verify console has environment variable "GO_JOB_RUN_INDEX" with value "2"
* Click on bread crumb "defaultStage / 2"

* Go to jobs tab

* Looking at pipeline "pipeline-for-job-rerun-run-multiple-instance" - Configure Pipeline
* Set run instance count to "0" for job in pipeline "first"

* Rerun "first-runInstance-1" jobs
* Verify rerun failed with cause "Cannot rerun job 'first'. Run configuration for job has been changed to 'simple'."

* Looking at pipeline "pipeline-for-job-rerun-run-multiple-instance"
* Trigger pipeline
* Wait for first stage to pass with pipeline label "2"
* Navigate to stage "defaultStage" of run "2" having counter "1"

* Go to jobs tab

* Verify job "first" has state "Completed" and result "Passed"





Teardown of contexts
* Capture go state "JobRerunRunOnAllAgents" - teardown
* With "1" live agents in directory "JobRerunRunMultipleInstance" - teardown
* Using pipeline "pipeline-for-job-rerun-run-multiple-instance" - teardown
* Basic configuration - teardown


