@ECHO off
REM Change the drive and/or directory name in the java command below if the same is not replicated during your testing.

@ECHO on
java -cp "c:\xeneta\XenetaDemo\lib\*;c:\xeneta\XenetaDemo\bin" org.testng.TestNG testng.xml