# IDrive360

This is a IDrive360 automation project.


Contains Automation code of www.idrive360.com with page object model framework using TestNG. 
The project has developed and runs on below versions
Java = 21
Selenium = 4.30.0

Softwares to be used:

•Eclipse 
•TestNG
•GIT Plugin 

Important project setup steps:

1.Install TestNG From Help >> Eclipse Marketplace >> search for TestNG
2.Create a folder WebAutomation/IDrive360 in the Users path - C:\Users\{USERNAME}\WebAutomation\IDrive360 Example: C:\Users\Surbhi\WebAutomation\IDrive360
3.Create sub folders named "Resources" and "Screenshots" in IDrive360 folder
4.Place the file named "IDrive360_Results_Of_Testing.xlsx" within the Resource folder.


Below is the directory structure of the project:

Under Main:

•Resources: contains "IDrive360_Accounts_for_testing.xlsx", message.properties, log4j2.xml

•Page Library: Contains all the pages which contains the functions to build the tests.

•TestBase: This contains all the functions that is globally used in the project(Loading of properties files, Webdriver creation, Wait creation etc). 

•Utilities: Contains some of the helper utilities such as date and time, email sending etc which aid in the project script creation. 

•WebFunctionalities: This contains reusable class WebFunctioality which handles the functions required for the script development. This will not change further.

Under Test:

•scripts: This contains all the test scripts written for RemotePC web site.