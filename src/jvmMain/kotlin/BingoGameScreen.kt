import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun BingoGameScreen() {
    val lastDrawnNumber by FirebaseQueryHandler.getLastDrawnNumber().collectAsState("")
    val boards by FirebaseQueryHandler.getBoards().collectAsState(emptyList())
    val name by FirebaseQueryHandler.getName().collectAsState()

    LaunchedEffect(Unit) {
        FirebaseQueryHandler.fetchDrawnNumbers()
    }

    Column(
        modifier = Modifier
            .wrapContentHeight()
            .padding(horizontal = 24.dp)
            .padding(top = 24.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Text(
            text = "Hello $name",
            modifier = Modifier.fillMaxWidth()
        ) // TODO: Figure out a way to change your name.

//        Row {
//            var updatedName by remember { mutableStateOf("") }
//            TextField(
//                modifier = TODO(),
//                value = updatedName,
//                onValueChange = {
//                    updatedName = it
//                }
//            )
//            Button(
//              onClick = TODO() // See `FirebaseQueryHandler::updateName`
//                content = { TODO() }
//            )
//        }

        if (lastDrawnNumber != null) {
            Text(
                text = "Last drawn number: $lastDrawnNumber",
            )
        } else {
            Text(
                text = "No ongoing bingo game",
            )
        }
        LazyVerticalGrid(
            columns = GridCells.Adaptive(300.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            if (boards.isEmpty()) {
                item {
                    Text(
                        text = "Loading boards from Firebase...",
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            } else {
                items(boards) { board ->
                    Box(
                        contentAlignment = Alignment.Center
                    ) {
                        BingoBoardComponent(board = board.board)
                    }
                }
            }
        }
    }
}