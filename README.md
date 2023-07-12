
### Solution

* It is a Maven project, Java was used as language and testng library for tests.
* I have used extentreports library for reporting.
* Following are the steps for running tests using docker:

step 1. Please go to root directory after cloning or downloading the project and execute following step to create docker image for tests

    docker build -t beyonnextestimage .

Step 2. Docker pull command for downloading image for selenium grid standalone with chrome

    docker pull selenium/standalone-chrome:latest


Step 3. Start standalone chrome

    docker run -d -p 4444:4444 -p 7900:7900 --shm-size="2g" selenium/standalone-chrome:latest

Step 4.(optional) Execution of tests inside container can be seen in following way:

    please open http://localhost:7900/ 
    click on Connect 
    enter secret in the password field
    vnc viewwer will get connected


Step 4. To run the tests

    docker run -d --network="host" beyonnextestimage mvn -f /home/beyonnex/pom.xml clean test
    

above command will generate an image

Step 5. To see the logs

    docker logs <generated image in the above command>


To run locally:

Step 1. We need to make following changes in setUp() method inside BaseTest.java class

    Inside try block, please comment  this line "driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), options);"

    and uncomment these two lines:
    System.setProperty("WebDriver.gecko.driver","src/test/resources/firefoxdriver_win32/geckodriver.exe" );
    driver = new ChromeDriver(options);



Step 2. To run test

        project root directory>mvn test

It can also be run by simply right-clicking on testng.xml and Run As testng.xml in intellij idea or eclipse(for this testng plugin needs to be 
installed in the IDE)

Reports:

1. Reports can be viewed under root directory>report>open report.html in browser
2. Also report can be seen  in root directory>target>surefile-Reports>emailable-report


