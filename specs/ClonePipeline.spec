ClonePipeline
=============

Setup of contexts
* Secure configuration - setup
* Login as "admin" - setup
* Using pipeline "edit-pipeline" - setup
* Capture go state "ClonePipeline" - setup

ClonePipeline
-------------

tags: Clone, #5164, Clicky Admin

* Open "Pipelines" tab

* Verify "basic" has pipelines "edit-pipeline"
* Click clone button for pipeline "edit-pipeline"

* Save - Already On Clone Pipeline Popup
* Verify error message "Invalid pipeline name ''. This must be alphanumeric and can contain underscores and periods (however, it cannot start with a period). The maximum allowed length is 255 characters." - Already On Clone Pipeline Popup
* Enter pipeline name "Some Invalid Pipeline"
* Verify error message "Invalid pipeline name ''. This must be alphanumeric and can contain underscores and periods (however, it cannot start with a period). The maximum allowed length is 255 characters." - Already On Clone Pipeline Popup
* Enter pipeline name "cloned-pipeline"
* Save for success

* Verify "Cloned successfully." message is displayed
* Verify pipeline "cloned-pipeline" is paused with message "Paused by admin"

* Open "Pipelines" tab

* Verify "basic" has pipelines "edit-pipeline, cloned-pipeline"
* Click clone button for pipeline "edit-pipeline"

* Enter pipeline name "another-cloned-pipeline"
* Enter pipeline group name ""
* Save for success

* Verify "Cloned successfully." message is displayed
* Verify pipeline "another-cloned-pipeline" is paused with message "Paused by admin"

* Open "Pipelines" tab

* Verify "basic" has pipelines "edit-pipeline, cloned-pipeline"
* Verify "defaultGroup" has pipelines "another-cloned-pipeline"










Teardown of contexts
* Capture go state "ClonePipeline" - teardown
* Using pipeline "edit-pipeline" - teardown
* Login as "admin" - teardown
* Secure configuration - teardown


