package com.example.tic_tac_toe

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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
import kotlin.random.Random



class MainGameActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val levelstr = intent.getStringExtra("key_string") ?: "Tic Tac Toe"
            val algo = intent.getIntExtra("key_value", 0)

            TicTacToeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainGameScreen(modifier = Modifier.padding(innerPadding),algo,levelstr)
                }
            }
        }

    }
}


@Composable
fun MainGameScreen(modifier: Modifier = Modifier,algo: Int,levelstr: String) {
    val solverUtils = SolverUtils()

    // State variables
    var grid by remember { mutableStateOf(Array(3) { Array(3) { -1 } }) }
    val buttonText = remember { mutableStateListOf(*Array(3 * 3) { "" }) }
    var winner by remember { mutableStateOf("") }
    var gameActive by remember { mutableStateOf(true) }

    // Get the context for showing Toast messages
    val context = LocalContext.current
    // Android Handler for delaying PC move
    val handler = Handler(Looper.getMainLooper())

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 50.dp)
    ) {
        Text(
            text = "$levelstr",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = Blue,
            modifier = Modifier
                .padding(bottom=150.dp)
        )

        val buttonSize = 100.dp

        // Creating rows of buttons
        repeat(3) { rowIndex ->
            Row {
                repeat(3) { columnIndex ->
                    Button(
                        onClick = {
                                    if (gameActive && grid[rowIndex][columnIndex] == -1) {
                                        grid[rowIndex][columnIndex] = 1
                                        buttonText[rowIndex * 3 + columnIndex] = "X" // Update button text

                                        winner = solverUtils.solver(grid)

                                        // No Winner (PC Chance)
                                        if(winner=="Continue Game"){
                                            gameActive = false
//                                            handler.postDelayed({
                                                // PC Move Logic
                                                var pcMoveMade = false
                                                while (!pcMoveMade) {
                                                    var location= arrayOf(-1,-1)
                                                    var row=-1
                                                    var column=-1
                                                    //Minimax algorithm
                                                    if(algo==3){
                                                        location=solverUtils.findBestMove(grid)
                                                        row=location[0]
                                                        column=location[1]
                                                    }
                                                    //Hard algorithm
                                                    else if(algo==2){
                                                        location=solverUtils.hardalgo(grid)
                                                        row=location[0]
                                                        column=location[1]
                                                        if(row==-1 || column==-1){
                                                            location=solverUtils.mediumalgo(grid)
                                                            row=location[0]
                                                            column=location[1]
                                                        }
                                                    }
                                                    //Medium algoritm
                                                    else if(algo==1){
                                                        location=solverUtils.mediumalgo(grid)
                                                        row=location[0]
                                                        column=location[1]

                                                    }
                                                    //Easy algorithm
                                                    if(row==-1 || column==-1) {
                                                        row = Random.nextInt(0, 3)
                                                        column = Random.nextInt(0, 3)
                                                    }

                                                    if (grid[row][column] == -1) {
                                                        grid[row][column] = 0
                                                        buttonText[row * 3 + column] = "O"

                                                        winner = solverUtils.solver(grid)

                                                        pcMoveMade = true // PC made a move
                                                        gameActive = winner == "Continue Game"
                                                    }
                                                }

//                                            }, 500)

                                        }

                                        //Winner Found
                                        if(winner!="Continue Game"){
                                            gameActive = false
                                            handler.postDelayed({
                                                // Reset the game state
                                                grid = Array(3) { Array(3) { -1 } } // Reset grid state
                                                buttonText.fill("") // Reset button text
                                                winner = ""
                                                gameActive = true
                                            },1500)
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
                            fontSize = 32.sp,
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
        if (winner != "Continue Game") {
            Text(
                text = "$winner",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Blue,
                modifier = Modifier.padding(bottom = 150.dp)
            )
        }
    }
}



@Preview(showBackground = true)
@Composable
fun MainGameScreenPreview() {
    TicTacToeTheme {
        MainGameScreen(
            levelstr = "level",  // Default value for preview
            algo = 0               // Default value for preview
        )
    }
}

