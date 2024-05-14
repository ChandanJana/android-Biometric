# android-Biometric
The Traditional Approach

Traditionally to safeguard confidential and sensitive information, most applications require the user to sign in. For that, the user enters a username and password, the application sends the credentials to an auth server, and eventually, the server will return some kind of token that will be used by the application to query the remote server for restricted data.

This is fine and perfect in the case of the web interface, but for mobile, we improve the user experience a notch better and have an extra layer of security at the same time. You must be wondering what exactly we are trying to solve here. Let’s take some examples-

Let’s say our application is per session authentication like financial apps, banking apps, etc., and are in the given flow- the user is asked to enter the username and password every time he/she launched the app. In this case, the user experience will be not that great, as we just can’t save his credential in the app and use them to auto-login because it is prone to malicious fraud.
Other applications like social networking or emails usually ask for login credentials once and set auto-login for the upcoming sessions. In these scenarios, if any other person has your phone unlocked with them, they can use your account.

The Biometric Approach

All the flaws discussed above can be fixed by safeguarding login credentials under another layer of authentication i.e., Biometric Validation using a fingerprint sensor. This will solve the problem of per session authentication without the user have to enter the credentials every time, they can just use their biometric and a successful validation will enable their saved credentials to auto-login. Even for applications like social networking sites or email, user credentials will be safeguarded by biometric so as to prevent auto-login actions by unauthorized users.

BIOMETRIC_ERROR_HW_UNAVAILABLE: The hardware is unavailable. Try again later.
BIOMETRIC_ERROR_NONE_ENROLLED: The user does not have any biometrics enrolled. It means the user has the biometric but didn’t enable it yet, we can let the user know that if they enable it and add their biometric detail they can use the app in a more secure way.
BIOMETRIC_ERROR_NO_HARDWARE: There is no biometric hardware.
BIOMETRIC_SUCCESS: No error detected, It means we can use the biometric for validation without any worry.

If we want to have the PromptInfo with PIN, Device credential allowed we need to setDeviceCredentialAllowed(true).

How to get FIREBASE_TOKEN 

To obtain a Firebase authentication token (often referred to as a Firebase token or Firebase CI token) for use in CI/CD workflows, you typically generate it using the Firebase CLI.

Here are the steps to obtain a Firebase token:

Install Firebase CLI: If you haven't already installed the Firebase CLI, you can do so by running the following command in your terminal:

npm install -g firebase-tools

Login to Firebase: Once the Firebase CLI is installed, log in to Firebase by running:

firebase login:ci

This command initiates the login process and prompts you to authenticate with your Google account. Follow the instructions in your terminal to complete the authentication process.

Generate Firebase Token: After successfully logging in, the Firebase CLI generates a Firebase authentication token (also known as a Firebase CI token) and prints it to the terminal. It will look something like this:

[2022-05-04T15:45:30.123Z] Successfully obtained Firebase CLI token: <YOUR_FIREBASE_TOKEN>

Copy the <YOUR_FIREBASE_TOKEN> value. This is your Firebase authentication token.
