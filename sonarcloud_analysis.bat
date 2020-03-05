@echo off
echo ====================================
echo = Have you launched the database ? =
echo ====================================
pause
mvn verify sonar:sonar
pause