@echo off
docker-compose up -d &&^
start "Scrapper" java -jar scrapper/target/scrapper.jar &&^
set TOKEN=%1&&^
start "Bot" java -jar bot/target/bot.jar
