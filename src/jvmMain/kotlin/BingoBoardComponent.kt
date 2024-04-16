import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
fun BingoBoardComponent(
    modifier: Modifier = Modifier,
    board: BingoBoard
) {
    // TODO: Make boards look prettier
    Row(
        modifier = modifier
            .background(
                color = Color.White,
                shape = RoundedCornerShape(16.dp)
            )
            .border(
                width = 1.dp,
                color = Color.Black,
                shape = RoundedCornerShape(16.dp),
            )
            .clip(RoundedCornerShape(16.dp))
    ) {
        Column {
            BingoBoardBox("B") // TODO: Create a separate component for headings
            board.BColumn?.forEach {
                BingoBoardBox(it)
            }
        }
        Column {
            BingoBoardBox("I")
            board.IColumn?.forEach {
                BingoBoardBox(it)
            }
        }
        Column {
            BingoBoardBox("N")
            board.NColumn?.forEach {
                BingoBoardBox(it)
            }
        }
        Column {
            BingoBoardBox("G")
            board.GColumn?.forEach {
                BingoBoardBox(it)
            }
        }
        Column {
            BingoBoardBox("O")
            board.OColumn?.forEach {
                BingoBoardBox(it)
            }
        }
    }
}

@Composable
fun BingoBoardBox(
    value: String,
) {
    // TODO: Make single board elements look prettier
//    var isSelected by remember { mutableStateOf(false) } // TODO: Look into auto-filling the board
//    val bgColor by animateColorAsState(if (isSelected) Color.Red else Color.Transparent) // Animate color example
    Box(
        modifier = Modifier
            .size(48.dp)
            .border(
                width = 2.dp,
                color = Color.Black
            ),
        contentAlignment = Alignment.Center,
    ) {
        Text(text = value)
    }
}
