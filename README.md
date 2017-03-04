gonative
========
Just a proof of concept: download platform-specific native binary.
The main idea is to allow a user to download platform-specific native binary and run as a deamon to provide OS operation to a web page.

Run the system as follows
```bash
./gradlew clean jettyRun
```

At the moment binary being downloaded is the undying "Hello world", just prints the string :)