UserAdmin
=========

Setup of contexts
* With no users - setup
* Secure configuration - setup
* Login as "admin" - setup
* Using pipeline "basic-pipeline" - setup
* Capture go state "UserAdmin" - setup

UserAdmin
---------

tags: 3237, security, Admin, stage1, oss

Scenario marked as manual because of Sahi problem of navigateTo

Create users in DB
* CreateUsers 
     |username    |email        |alias         |
     |------------|-------------|--------------|
     |admin       |admin@tw.com |adminAlias    |
     |view        |view@tw.com  |zzz,viewAlias |
     |operate     |op@tw.com    |opAlias       |
     |pavan       |pavan@tw.com |pavanu,ps     |
     |raghu       |raghu@tw.com |rrr,rags,ragsu|
     |operatorUser|opUser@tw.com|opUser        |



* Login as "admin"

* Open "User Summary" tab

* Verify "6" enabled and "0" disabled users
* Sort column "Username" - Already On Users Summary page
* Verify column "Username" has "admin,operate,operatorUser,pavan,raghu,view" in order
* Sort column "Username" - Already On Users Summary page
* Verify column "Username" has "view,raghu,pavan,operatorUser,operate,admin" in order
* Sort column "Aliases" - Already On Users Summary page
* Verify column "Aliases" has "adminAlias,opAlias,opUser,pavanu | ps,rags | ragsu | rrr,viewAlias | zzz" in order
* Sort column "Email" - Already On Users Summary page
* Verify column "Email" has "admin@tw.com,op@tw.com,opUser@tw.com,pavan@tw.com,raghu@tw.com,view@tw.com" in order





Teardown of contexts
* Capture go state "UserAdmin" - teardown
* Using pipeline "basic-pipeline" - teardown
* Login as "admin" - teardown
* Secure configuration - teardown
* With no users - teardown


