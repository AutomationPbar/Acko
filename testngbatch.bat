set projectLocation=C:\Users\Avani\workspace\Acko
cd %projectLocation%
set classpath=%projectLocation%\bin;%projectLocation%\Libs\*
java org.testng.TestNG %projectLocation%\testng.xml
