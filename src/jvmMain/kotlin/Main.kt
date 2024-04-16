// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.


import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


fun main() = application {
    remember { initFirebase() }

    LaunchedEffect(Unit) {
        delay(200)
        AuthHandler.loginOrCreateAnonymousUser()
    }

    val userState by AuthHandler.userState.collectAsState()

    LaunchedEffect(userState) {
        println("User state: ${userState?.uid}")
        if (userState != null) {
            launch { FirebaseQueryHandler.fetchName() }
            launch { FirebaseQueryHandler.fetchBoards() }
        }
    }

    Window(
        onCloseRequest = ::exitApplication,
        title = "Bingo"
    ) {
        if (userState == null) {
            UserNotLoggedInScreen()
        } else {
            BingoGameScreen()
        }
    }
}


