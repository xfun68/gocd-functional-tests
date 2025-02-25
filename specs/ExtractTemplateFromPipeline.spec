ExtractTemplateFromPipeline
===========================

Setup of contexts
* Basic configuration - setup
* Using pipeline "simple-pipeline,template-params-pipeline-trunk" - setup
* Capture go state "ExtractTemplateFromPipeline" - setup

ExtractTemplateFromPipeline
---------------------------

tags: Clicky Admin, template, #5785, 6821


* Open "Pipelines" tab

* Extract template for pipeline "simple-pipeline"

* Enter template name "template_from_pipeline" - Already on extract template popup
* Verify extract template checkbox is disabled - Already on extract template popup
* Verify extract template checkbox is checked
* Verify pipeline selection dropdown has value "simple-pipeline"
* Verify pipeline selection dropdown is disabled
* Save for success - Already on extract template popup

* Open stages tab

* Verify "first" stage is present

* Open "Pipelines" tab

* Edit pipeline "simple-pipeline"

* Verify pipeline uses template "template_from_pipeline"
* Open parameters page

* Verify parameter "name" has value "value"
* Open general options page

* Go to environment variables page

* Verify that "simple-pipeline" has variable named "NAME" with value "hucha_raghuveer"
* Verify environment variable "NAME" has value "hucha_raghuveer" for "pipeline"
* Open stage listing page - Already On Environment Variables Page

* Click to edit template

* Open stages tab

* Open stage "first"

* Go to environment variables page - Already on edit stage page

* Verify environment variable "STAGE_LEVEL_VARIABLE" has value "stage" for "stage"
* Verify environment variable "PARAM_VALUE_ENV_VAR" has value "#{name}" for "stage"
* Open jobs - Already On Environment Variables Page

* Open job "job"


* Go to environment variables page - Already on Job edit page

* Verify environment variable "JOB_LEVEL_VARIABLE" has value "job" for "job"

* Open "Pipelines" tab

* Verify that extract template is disabled for "template-params-pipeline-trunk"




Teardown of contexts
* Capture go state "ExtractTemplateFromPipeline" - teardown
* Using pipeline "simple-pipeline,template-params-pipeline-trunk" - teardown
* Basic configuration - teardown


