package com.example.tic_tac_toe

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tic_tac_toe.ui.theme.*
import kotlinx.coroutines.time.delay
import kotlin.random.Random


class MainGameActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TicTacToeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainGameScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }

    }
}


@Composable
fun MainGameScreen(modifier: Modifier = Modifier) {
    val solverUtils = SolverUtils()

    // State variables
    var grid by remember { mutableStateOf(Array(3) { Array(3) { -1 } }) }
    val buttonText = remember { mutableStateListOf(*Array(3 * 3) { "" }) }
    var winner by remember { mutableStateOf("") }
    var gameActive by remember { mutableStateOf(true) }

    // Get the context for showing Toast messages
    val context = LocalContext.current

    // Handle winner state and reset the game after a delay
    LaunchedEffect(winner) {
        if (winner != "Continue Game" && winner.isNotEmpty()) {
            gameActive=false
            kotlinx.coroutines.delay(1500) // Use kotlinx.coroutines.delay
            // Reset the game state
            grid = Array(3) { Array(3) { -1 } } // Reset grid state
            buttonText.fill("") // Reset button text
            winner=""
            gameActive=true
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 200.dp)
    ) {
        val buttonSize = 100.dp

        // Creating rows of buttons
        repeat(3) { rowIndex ->
            Row {
                repeat(3) { columnIndex ->
                    Button(
                        onClick = {
                            if (gameActive) {
                                for(row in 0..2){
                                    for(col in 0..2){
                                        print("${grid[row][col]} ")
                                    }
                                    println()
                                }
                                if (grid[rowIndex][columnIndex] == -1) {
                                    grid[rowIndex][columnIndex] = 1
                                    buttonText[rowIndex * 3 + columnIndex] = "X" // Update button text

                                    // Check for winner after player move
                                    winner = solverUtils.solver(grid)

    //                                if (winner != "Continue Game") {
    //                                    grid = Array(3) { Array(3) { -1 } } // Reset grid state
    //                                    buttonText.fill("")  // Reset button text
    //                                } else {
                                    if(winner=="Continue Game"){

                                        // PC Move Logic
                                        var pcMoveMade = false
                                        while (!pcMoveMade) {
                                            val randomRow = Random.nextInt(0, 3)
                                            val randomColumn = Random.nextInt(0, 3)

                                            if (grid[randomRow][randomColumn] == -1) {
                                                grid[randomRow][randomColumn] = 0
                                                buttonText[randomRow * 3 + randomColumn] = "0"

                                                winner = solverUtils.solver(grid)
    //                                            if (winner != "Continue Game") {
    //                                                grid = Array(3) { Array(3) { -1 } }
    //                                                buttonText.fill("") // Reset button text
    //                                            }
                                                pcMoveMade = true // PC made a move
                                            }
                                        }
                                    }
                                }
                            }
                                  },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent,
                            contentColor = MaterialTheme.colorScheme.onSurface
                        ),
                        modifier = Modifier
                            .size(buttonSize)
                            .padding(0.dp)
                    ) {
                        Text(
                            text = buttonText[rowIndex * 3 + columnIndex],
                            color = Blue,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier

                        )
                    }

                    // Add vertical divider between buttons
                    if (columnIndex < 2) {
                        Spacer(
                            modifier = Modifier
                                .size(2.dp, buttonSize)
                                .background(MaterialTheme.colorScheme.onSurface)
                        )
                    }
                }
            }

            // Add horizontal divider between rows
            if (rowIndex < 2) {
                Spacer(
                    modifier = Modifier
                        .size(buttonSize * 3, 2.dp)
                        .background(MaterialTheme.colorScheme.onSurface)
                )
            }
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom,
        modifier = Modifier.fillMaxSize()
    ) {
        if (winner.isNotEmpty() && winner != "Continue Game") {
            Text(
                text = "Outcome: $winner",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Blue,
                modifier = Modifier.padding(bottom = 250.dp)
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun MainGameScreenPreview() {
    TicTacToeTheme {
        MainGameScreen()
    }
}