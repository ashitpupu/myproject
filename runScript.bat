call mvn clean
call mvn package
md .\Project_setup
echo copying file
xcopy /s .\target\weather_simulator-0.0.1-SNAPSHOT.jar .\Project_setup
md .\Project_setup\config
xcopy /s .\config .\Project_setup\config /E