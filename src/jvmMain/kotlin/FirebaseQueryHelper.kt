import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.IgnoreExtraProperties
import com.google.firebase.firestore.Query
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.firestore
import dev.gitlive.firebase.firestore.orderBy
import kotlinx.coroutines.flow.*

object FirebaseQueryHandler {
    private val db = Firebase.firestore

    private var boards = MutableStateFlow<List<BingoBoardContainer>>(emptyList())
    fun getBoards() = boards.asStateFlow()

    private var drawnNumbers = MutableStateFlow<List<String>>(emptyList())
    fun getDrawnNumbers() = drawnNumbers.asStateFlow()
    fun getLastDrawnNumber() = drawnNumbers.asStateFlow().map { it.lastOrNull() }

    private var name = MutableStateFlow("")
    fun getName() = name.asStateFlow()

    suspend fun fetchDrawnNumbers() {
        try {
            val snapshots = db.collection("draw").orderBy("drawnAt", Query.Direction.DESCENDING).snapshots
            snapshots.collectLatest {
                drawnNumbers.value = it.android.documents.map { doc ->
                    doc.get("number") as String
                }
            }
        } catch (e: Exception) {
            println("error drawn numbers")
            e.printStackTrace()
        }
    }

    suspend fun fetchName() {
        try {
            val user = FirebaseAuth.getInstance().currentUser
            db.collection("users").document(user?.uid ?: "").snapshots.collectLatest { doc ->
                name.value = doc.get("name") ?: ""
            }
        } catch (e: Exception) {
            println("error fetching name")
            e.printStackTrace()
        }
    }

    suspend fun updateName(userName: String) {            
        try {
            val user = FirebaseAuth.getInstance().currentUser
            db.collection("users").document(user?.uid ?: "").set("name" to userName, merge = true)
        } catch (e: Exception) {
            println("Error setting name")
            e.printStackTrace()
        }
    }

    suspend fun fetchBoards() {
        try {
            val user = FirebaseAuth.getInstance().currentUser
            println("Fetching boards for user ${user?.uid}...")
            val bingoBoardSnapshot = db.collection("users/${user?.uid}/bingoBoards").snapshots
            bingoBoardSnapshot.collectLatest {
                boards.value = it.android.documents.mapNotNull { docBoard ->
                println("Got board: $docBoard")
                    docBoard.toObject(BingoBoardContainer::class.java)
                }
            }
        } catch (e: Exception) {
            println("error fetching boards")
            e.printStackTrace()
        }
    }
}

data class BingoBoard(
    val B: String? = null,
    val I: String? = null,
    val N: String? = null,
    val G: String? = null,
    val O: String? = null,
)

val BingoBoard.BColumn: List<String>? get() = this.B?.split(",")
val BingoBoard.IColumn: List<String>? get() = this.I?.split(",")
val BingoBoard.NColumn: List<String>? get() = this.N?.split(",")
val BingoBoard.GColumn: List<String>? get() = this.G?.split(",")
val BingoBoard.OColumn: List<String>? get() = this.O?.split(",")

@IgnoreExtraProperties
data class BingoBoardContainer(val board: BingoBoard = BingoBoard())
