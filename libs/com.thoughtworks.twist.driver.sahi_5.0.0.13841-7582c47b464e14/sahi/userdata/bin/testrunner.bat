@ECHO OFF
if "%1"=="" goto ERROR
if "%2"=="" goto ERROR
if "%3"=="" goto ERROR
SET SAHI_HOME=..\..
SET USERDATA_DIR=..\
SET SCRIPTS_PATH=scripts
SET START_URL=%2
SET THREADS=5
SET SINGLE_SESSION=false
java -cp %SAHI_HOME%\lib\ant-sahi.jar net.sf.sahi.test.TestRunner -test %SCRIPTS_PATH%/%1 -browserType %3 -baseURL "%START_URL%" -host localhost -port 9999 -threads %THREADS% -useSingleSession %SINGLE_SESSION%
goto :EOF

:ERROR
echo "Usage: %0 <sah file|suite file> <startURL> <browserType>"
echo "File path is relative to userdata/scripts"
echo "Example:" 
echo %0 demo/demo.suite http://sahipro.com/demo/ firefox
echo %0 demo/sahi_demo.sah http://sahipro.com/demo/ ie