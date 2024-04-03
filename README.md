## Reproduce bug

1. Update applicationId & namespace in app/build.gradle to your app in firebase project.
2. Update app/google-services.json to your firebase project.
3. Run `npm i` in backend/functions directory followed by `npm run deploy` to deploy the cloud functions to your firebase project.
4. Run the app on an emulator and press the button in the bottom right hand corner to set the language, create a user and trigger the cloud function.
5. Head to the cloud function logs and observe the locale as authBeforeUserCreated {"locale":"und",....other properties}. "und" means undetermined locale. This should be "en-US".
6. In logcat, I observed the following log which explains why there is a bug:
```
2024-04-03 09:50:57.889 26056-26147 System                  io.flutter.plugins.firebase.tests    W  Ignoring header X-Firebase-Locale because its value was null.
```