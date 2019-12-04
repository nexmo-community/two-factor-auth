# 2FA Example using Java/Spark and the Verify API

This is the source code to accompany the [How to Add Two-Factor Authentication with Java and Spark blog post]()

## Setup

1. Clone the repository
2. Replace `YOUR_API_KEY` and `YOUR_API_SECRET` in `src/main/java/two/factor/auth/App.java` with your own Nexmo API key and secret from the [Developer Dashboard](https://dashboard.nexmo.com)
3. Execute `gradle run`

## Run it

1. Visit `http://localhost:3000` in your browser
2. Enter your cell phone number with the international dialing code but omit the `+` symbol and any leading zeros
3. Press the Register button. In a moment or two you should receive a verification code via SMS.
4. Enter the verification code and press Check.
5. If you have entered the code successfully, you should receive a message that reads "Registration successful". If not, you will receive a message that reads "Verification failed".
