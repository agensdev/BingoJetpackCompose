import android.app.Application
import com.google.firebase.FirebasePlatform
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.functions.FirebaseFunctions
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.FirebaseApp
import dev.gitlive.firebase.FirebaseOptions
import dev.gitlive.firebase.firestore.firestore
import dev.gitlive.firebase.initialize
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.*
import java.util.prefs.Preferences


internal fun initFirebase(): FirebaseApp {

    FirebasePlatform.initializeFirebasePlatform(object : FirebasePlatform() {
        val storage = mutableMapOf<String, String>()
        override fun store(key: String, value: String) = storage.set(key, value)
        override fun retrieve(key: String) = storage[key]
        override fun clear(key: String) {
            storage.remove(key)
        }

        override fun log(msg: String) = println(msg)
    })
    val opt = FirebaseOptions(
        apiKey = "AIzaSyCb5n0pZxdDygwz4xJEbOYit9jlVKnZ1s8",
        applicationId = "1:540580529540:android:a21f86acf790b83267c9d1",
        projectId = "bingofm-69",
        databaseUrl = "https://bingofm-69-default-rtdb.europe-west1.firebasedatabase.app"
    )
    val app = Firebase.initialize(Application(), opt)
    Firebase.firestore(app = app).apply {
        this.setSettings(persistenceEnabled = false)
    }
    return app
}

object AuthHandler {

    private val loggedInState = MutableStateFlow<FirebaseUser?>(null)

    val userState get() = loggedInState.asStateFlow()

    init {
        FirebaseAuth.getInstance().addAuthStateListener(object : FirebaseAuth.AuthStateListener {
            override fun onAuthStateChanged(auth: FirebaseAuth) {
                loggedInState.value = auth.currentUser
            }
        })
    }

    fun loginOrCreateAnonymousUser() {
        println("Logging in...")
        val preferences: Preferences = Preferences.userRoot().node("authhandler")
        /**
         * Uncomment to create new user
         */
        preferences.clear()
        if (preferences.get("token", null) == null) {
            val data = hashMapOf(
                "uid" to UUID.randomUUID().toString(),
            )
            FirebaseFunctions.getInstance().getHttpsCallable("createCustomToken").call(data).addOnSuccessListener {
                val token = it?.data.toString()
                preferences.put("token", token)
                signInWithToken(token)
                // this would be pointless if new users with custom token triggered a background function. ¯\_(ツ)_/¯
                FirebaseFunctions.getInstance().getHttpsCallable("createBoards").call(data).addOnSuccessListener {
                    println("boards created")
                }.addOnFailureListener { exception ->
                    exception.printStackTrace()
                    println("failed creating boards")
                }
            }.addOnFailureListener {
                it.printStackTrace()
            }

        } else {
            val token = preferences.get("token", null)
            signInWithToken(token)
        }
    }

    private fun signInWithToken(token: String) {
        FirebaseAuth.getInstance().signInWithCustomToken(token)
            .addOnSuccessListener {
                println("Logged in as ${it.user.uid}")
            }.addOnFailureListener {
                println("Failed to log in $it")
            }
    }
}
