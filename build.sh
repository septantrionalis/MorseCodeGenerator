echo Reseting the config file...
echo api.ai.key=TODO > config.properties
echo api.ai.textprompt=a lengthy weather report >> config.properties
echo api.ai.url=https://api.openai.com/v1/chat/completions >> config.properties
echo api.ai.model=gpt-3.5-turbo >> config.properties
echo app.wpm=23 >> config.properties
echo app.textsource=file >> config.properties
echo app.startdelay=3 >> config.properties
echo audio.frequency=600 >> config.properties
echo app.filename=space_weather1.txt >> config.properties

mvn clean compile assembly:single
cp ./target/mcgenerator-jar-with-dependencies.jar ./app
cp ./target/mcgenerator-jar-with-dependencies.jar ./dist

