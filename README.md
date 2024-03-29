This java application will generate random weather reports, bulletins, etc from OpenAI and create morse code audio from it. Any generated messages will be saved into the "history" directory. The entire app can be run in a GUI (recommended) or in a terminal.

# UI Version
This is how the application is packaged by default.

Java 1.8 is required. May work with higher versions, but was coded using Java 1.8.\
Generate an OpenAI key from https://platform.openai.com/api-keys \
Go into the "dist" directory and type "./run.sh" or "./run.bat".\
\
The following screen will open:

![Screenshot 2024-03-21 at 10 15 37 PM](https://github.com/septantrionalis/MorseCodeGenerator/assets/16886560/f1bba53b-3125-434e-a4f7-fb49e709e0bf)

Click on the "Configuration" tab and add your OpenAI key into the "OpenAI API Key" text field.

![Screenshot 2024-03-21 at 10 18 08 PM](https://github.com/septantrionalis/MorseCodeGenerator/assets/16886560/789c754d-b610-45d3-a877-9a7244f83af1)

Modify other parameters as needed.\
Go back to the "Main" tab once your configuration is set.\
Click on the "Generate Text" button at the bottom to generate your random text. When this is done, the button will change to "Run". Click on this to start the morse code.

# Terminal Version
The application is not packaged for this to run, but the documentation is left here just incase anyone wants to repackage.\
All configuration is done in the "config.properties" file.

## To Run
Java 1.8 is required. May work with higher versions, but was coded using Java 1.8.\
Generate an OpenAI key from https://platform.openai.com/api-keys \
Open a terminal or console.\
Go into the "dist" directory,\
Add your OpenAI API key to the config.properties file under "api.ai.key".

![Screenshot 2024-03-15 at 12 45 56 PM](https://github.com/septantrionalis/MorseCodeGenerator/assets/16886560/30526995-d51a-4982-93a6-afa6996c6226)

"run.sh" or "run.bat" to run.

You should see :

![Screenshot 2024-03-15 at 12 50 09 PM](https://github.com/septantrionalis/MorseCodeGenerator/assets/16886560/cc9bce45-31e8-4745-9fbf-42a3ac19b118)

Once you see, "Press return to start" the program is ready to go. Once you press return, a slight delay will happen before the morse code is sent.

# Configuration File
**api.ai.key**: The OpenAI Api key. This is needed to generate a random response.\
**api.ai.textprompt**: The prompt to send to OpenAI. Default: "a lengthy weather report"\
**api.ai.url**: The api URL of openapi. Default: https://api.openai.com/v1/chat/completions \
**apiapi.ai.model**: The ai model to use. Default: gpt-3.5-turbo\
**app.wpm**: The WPM to send the morse code. Default: 23. Valid Values: 5 to 30.\
**app.textsource**: The source from which to generate the text. Default: mock. Valid values are:
  * open_ai: Generate the source text from open ai. All of the "api.ai.*" values need to be filled in.
  * file: Generate morse code from a file located in the "history" directory. app.filename will need to be set.
  * paris: Just sent the word "paris". This was used to get morse code timings.
  * mock: A hard coded value used for testing.

**app.filename**: used when "app.textsource" property file is set to file. This is the file to read from. Default: space_weather.txt\
**app.startdelay**: The number of seconds to delay sending morse code after hitting return. Default: 5. Not needed in the UI version\
**audio.frequency**: The audio frequency. Default: 600

## Notes
Any generated text is saved in the "history" directory as the filename history_MMddyy_HHmmss.txt. This allows you to replay the text if you need to adjust the tone, wpm, etc. Just use the "app.textsource=file" and "app.filename=history_MMddyy_HHmmss.txt" as outlined above.\
\
Prosigns are not included in this release. Valid values are A-Z, 0-9, comma, period, equal, and question mark.

