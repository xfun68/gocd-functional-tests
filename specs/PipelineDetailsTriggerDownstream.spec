PipelineDetailsTriggerDownstream
================================

Setup of contexts
* Secure configuration - setup
* Login as "admin" - setup
* Using pipeline "up-with-2-stages,down-depend-on-1,down-depend-on-2" - setup
* With "1" live agents in directory "StageDetails" - setup
* Capture go state "PipelineDetailsTriggerDownstream" - setup

PipelineDetailsTriggerDownstream
--------------------------------

tags: 3785, dependency pipeline, trigger with options, automate

* Looking at pipeline "up-with-2-stages"
* Trigger pipeline
* Verify stage "1" is "Passed" on pipeline with label "1"
* Looking at pipeline "down-depend-on-1"
* Trigger pipeline
* Verify stage "1" is "Passed" on pipeline with label "1"
* Navigate to pipeline dependencies for "up-with-2-stages" "1" "first" "1"

* Verify cannot trigger "down-depend-on-2"
* Open trigger lightbox for "down-depend-on-1"

* Verify material summary is marked as modified
* Verify material is pegged with revision "${runtime_name:up-with-2-stages}/1/first/1"
* Deploy

* Looking at pipeline "down-depend-on-1"
* Verify stage "1" is "Passed" on pipeline with label "2"
* Navigate to pipeline dependencies for "up-with-2-stages" "1" "first" "1"

* Trigger stage "second" - Already On Stage Detail Pipeline Dependencies Page

* Looking at pipeline "up-with-2-stages"
* Verify stage "2" is "Passed" on pipeline with label "1"
* Looking at pipeline "down-depend-on-2"
* Trigger pipeline
* Verify stage "1" is "Passed" on pipeline with label "1"
* Navigate to pipeline dependencies for "up-with-2-stages" "1" "second" "1"

* Open trigger lightbox for "down-depend-on-2"

* Verify material is pegged with revision "${runtime_name:up-with-2-stages}/1/second/1"

* Looking at pipeline "up-with-2-stages"
* Trigger pipeline
* Verify stage "1" is "Passed" on pipeline with label "2"
* Navigate to pipeline dependencies for "up-with-2-stages" "2" "first" "1"

* Open trigger lightbox for "down-depend-on-1"

* Verify currently deployed revision is "${runtime_name:up-with-2-stages}/1/first/1"
* Verify material is pegged with revision "${runtime_name:up-with-2-stages}/2/first/1"
* Close - Already on deployment light box





Teardown of contexts
* Capture go state "PipelineDetailsTriggerDownstream" - teardown
* With "1" live agents in directory "StageDetails" - teardown
* Using pipeline "up-with-2-stages,down-depend-on-1,down-depend-on-2" - teardown
* Login as "admin" - teardown
* Secure configuration - teardown


