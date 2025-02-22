@echo off
set JAVA_HOME=C:\PROGRA~1\JAVA\JDK-23
set M2_HOME=c:\\tools\\apache-maven-3.9.9
pushd %cd%
cd ..
call %M2_HOME%\bin\mvn clean install spring-boot:run
pause
popd