SvnMaterialViewerAndEditor
==========================

Setup of contexts
* Basic configuration - setup
* Using pipeline "edit-pipeline" - setup
* Capture go state "SvnMaterialViewerAndEditor" - setup

SvnMaterialViewerAndEditor
--------------------------

tags: Clicky Admin, 4598

* Click on pipeline "edit-pipeline" for editing

* Open material listing page

* Open new subversion material creation popup

* Enter material name "svn_material"
* Enter url "http://foo.bar"
* Enter username "user"
* Enter password "abc"
* Enter destination directory "svn"
* Make autoupdate to be "false" - Already on Subversion Material Add Popup
* Make checkexternals to be "true"
* Enter black list "*.htm" - Already on Subversion Material Add Popup
* Click save - Already on Subversion Material Add Popup

* Verify that material saved successfully
* Verify that "svn" with name "svn_material" is added with attributes "materialName>svn_material,url>http://foo.bar,username>user,encryptedPassword>/yPnfnXXsrc=,dest>svn,checkexternals>true,autoUpdate>false"
* Edit material "svn_material"

* Verify material name is "svn_material" - Already on Subversion Material Add Popup
* Verify url is "http://foo.bar" - Already on Subversion Material Add Popup
* Verify username is "user" - Already on Subversion Material Add Popup
* Verify "password" field is "disabled" and contains "**********" - Already on Subversion Material Add Popup
* Verify destination directory is "svn" - Already on Subversion Material Add Popup
* Verify autoupdate is "false" - Already on Subversion Material Add Popup
* Verify checkexternals is "true"
* Verify black list is "*.htm" - Already on Subversion Material Add Popup
* Enter username "pavan"
* Click save - Already on Subversion Material Add Popup

* Verify that material saved successfully
* Verify that "svn" with name "svn_material" is added with attributes "materialName>svn_material,username>pavan"





Teardown of contexts
* Capture go state "SvnMaterialViewerAndEditor" - teardown
* Using pipeline "edit-pipeline" - teardown
* Basic configuration - teardown


