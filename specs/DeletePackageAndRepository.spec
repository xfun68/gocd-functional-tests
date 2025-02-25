DeletePackageAndRepository
==========================

Setup of contexts
* Delete package configuration - setup
* Login as "admin" - setup
* Using pipeline "one,two" - setup
* Capture go state "DeletePackage" - setup

DeletePackageAndRepository
--------------------------

tags: #7474, plugins-tests, Permissions, #7644

* Open "Package Repositories" tab

* Expand repository listing for "repo"
* Verify repository "repo" is shown with delete icon disabled
* Verify package "used" is shown with delete icon disabled
* Verify package "another-unused" is shown with delete icon enabled
* Verify package "unused" is shown with delete icon enabled
* Verify repository "empty-repo" is shown with delete icon enabled
* Click on package "used" in repository listing

Super-admin sees all pipelines as links
* Verify package with name "used" and spec "used" is shown
* Verify delete button is "disabled"
* Click on "Show pipelines using this package" link
* Verify message "Pipelines currently associated with this package" is shown in the heading
* Verify pipeline "one" as link with group "first" on row "1"
* Verify pipeline "two" as link with group "second" on row "2"
* Click on pipeline "two" on row "2"

* Verify pipeline name is "two"
* Verify that package with name "repo:used" is added with url "Repository: [repo_url=file://tmp] - Package: [package_spec=used]"

* Logout - On Any Page

* Login as "group1Admin"

* Open "Package Repositories" tab

* Expand repository listing for "repo"
* Verify package "used" is shown with delete icon disabled
* Verify tooltip message "This package is being used in one or more pipeline(s), cannot delete the package" when package "used" cannot be deleted from tree view
* Verify package "unused" is shown with delete icon enabled
* Verify tooltip message "Delete this package." when package "unused" can be deleted from tree view
* Verify package "another-unused" is shown with delete icon enabled
* Click on package "used" in repository listing

* Verify package with name "used" and spec "used" is shown
* Verify delete button is "disabled"
* Verify tooltip message "This package is being used in one or more pipeline(s), cannot delete the package" when package cannot be deleted
* Click on "Show pipelines using this package" link
* Verify message "Pipelines currently associated with this package" is shown in the heading
* Verify pipeline "one" as link with group "first" on row "1"
* Verify pipeline "two" with group "second" on row "2"
* Click on pipeline "one" on row "1"

* Verify pipeline name is "one"
* Verify that package with name "repo:used" is added with url "Repository: [repo_url=file://tmp] - Package: [package_spec=used]"

* Open "Package Repositories" tab

* Delete repository "empty-repo" from tree
* Verify repository "empty-repo" is not shown in the tree
* Expand repository listing for "repo"
* Delete package "unused" from tree
* Verify package "unused" is not shown in the tree
* Click on package "another-unused" in repository listing

* Verify message "No Pipelines currently use this package" is shown
* Verify tooltip message "Delete this package." when package can be deleted
* Delete package "another-unused"

* Open "Package Repositories" tab

* Expand repository listing for "repo"
* Verify package "another-unused" is not shown in the tree




Teardown of contexts
* Capture go state "DeletePackage" - teardown
* Using pipeline "one,two" - teardown
* Login as "admin" - teardown
* Delete package configuration - teardown


