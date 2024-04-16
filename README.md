# Let's play Bingo with Josse!

This is the starter code our bingo client.

Your job is to extend this code to make everything look pretty by playing around with Compose, and 
possibly automate the process of "selecting" drawn numbers.

## Getting started

1. Install IntelliJ IDEA Community Edition (CE) from here:

```
https://www.jetbrains.com/idea/download/
```

2. Clone this repository using git:

```
git clone git@github.com:roberthorgen/BingoJetpackComposeDesktop.git
```

3. Open the project in IDEA, open the file named `Main.kt`
4. If you see a green play button to the left of the line saying `fun main()...` then press that, if not click the hammer in the upper right corner of your screen. After all the dependencies are set up you should see the green play button.
5. That's it!

## Tips and tricks! (Read this)

The hardest part is to figure out what is possible. Use your GPT and Google skills. But here are some useful things:

### Clicks

To make _something_ clickable, use the `clickable` modifier on any element:

```kotlin
Box( // <-- Works with any composable, not only Box.
    modifier = Modifier.clickable { /* Click action code goes here */ }
) {
    // More UI here
}
```

An exception from this: some composables, e.g. `Button`, has built-in support for click:

```kotlin
Button(
    onClick = { /* Click action code goes here */ },
) {
    // Button UI goes here
}
```

### Inspect code

It's very useful to "jump" into some source-code to see how it's made and what arguments it accepts. To do so, hold
`cmd` and click on any call. To go back, press `opt+cmd+arrow left` (might not work depending on your shortcut settings).

### Components

Compose has many built-in components that you can use. See all of them here: https://developer.android.com/develop/ui/compose/components
(note: That is a list for Android components, and not everyone is available for Desktop)

### Modifiers

Modifiers are something you can pass into a composable to _modify it_. E.g. color, shape, size, etc. There's no extensive list
for all modifiers, but review this page to learn more: https://developer.android.com/develop/ui/compose/modifiers, and start
typing `Modifier.` and click `cmd+space` to get suggestions from IDEA on available modifiers.

### Animations

Animations in compose are easy and fun. Go to this site to see what is possible: https://developer.android.com/develop/ui/compose/animation/quick-guide

### Not sure what to make next?

We have put in some todos around in the code, find them and get inspired. Also, find an image of a bingo board on google and see
if you can make it yourself!

## Having troubles?

If you see this exception in the log:

```
FirebaseApp Device unlocked: initializing all Firebase APIs for app [DEFAULT]
User state: null
Logging in...
Exception in thread "OkHttp Dispatcher" java.lang.IllegalArgumentException: Given String is empty or null
	at com.google.android.gms.common.internal.Preconditions.checkNotEmpty(com.google.android.gms:play-services-basement@@18.1.0:2)
	at com.google.firebase.auth.FirebaseAuthException.<init>(com.google.firebase:firebase-auth-interop@@20.0.0:2)
	at com.google.firebase.auth.FirebaseAuthInvalidUserException.<init>(FirebaseAuthInvalidUserException.kt:3)
	at com.google.firebase.auth.FirebaseAuth$signInWithCustomToken$1.onResponse(FirebaseAuth.kt:215)
	at okhttp3.RealCall$AsyncCall.execute(RealCall.java:203)
	at okhttp3.internal.NamedRunnable.run(NamedRunnable.java:32)
	at java.base/java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1136)
	at java.base/java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:635)
	at java.base/java.lang.Thread.run(Thread.java:833)
```

(note: FirebaseAuth$signInWithCustomToken$1 is important here)

It means something is wrong with your firebase user. Create a new one by opening `FirebaseHelper`, and un-comment the line 
saying `preferences.clear()`, start the program and verify that you have gotten a new user with new boars, then comment
the line back out again (if not you get a new user every time you launch the program).