package com.example.tic_tac_toe

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tic_tac_toe.ui.theme.TicTacToeTheme
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TicTacToeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@SuppressLint("RememberReturnType")
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    val context = LocalContext.current

    // Generate a random row and column for the "0"
    val randomRow = remember { Random.nextInt(0, 3) }
    val randomColumn = remember { Random.nextInt(0, 3) }

    // Initialize a 2D list to keep track of button text
    val buttonTexts = remember { mutableStateListOf<MutableList<String>>() }

    // Initialize the 3x3 grid with empty strings
    remember {
        for (i in 0..2) {
            buttonTexts.add(mutableListOf("", "", ""))
        }
        buttonTexts[randomRow][randomColumn] = "0" // Set a random button to "0" at the start
    }
    
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Text(
            text = "Tic Tac Toe",
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(top = 150.dp)
                .padding(bottom = 130.dp))
        Button(
            onClick = {
            /*TODO*/
                val intent = Intent(context, MainGameActivity::class.java)
                context.startActivity(intent)


            },
            modifier = Modifier
                .padding(bottom = 20.dp)


        ) {
            Text(
                text = "Easy",
                modifier = Modifier
                    .padding(8.dp),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Button(
            onClick = {
            /*TODO*/
                val intent = Intent(context, MainGameActivity::class.java)
                intent.putExtra("key_value", 1)
                context.startActivity(intent)


            },
            modifier = Modifier
                .padding(bottom =20.dp)

        ) {
            Text(
                text = "Medium",
                modifier = Modifier
                    .padding(8.dp),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Button(
            onClick = {
            /*TODO*/
                val intent = Intent(context, MainGameActivity::class.java)
                intent.putExtra("key_value", 2)
                context.startActivity(intent)
            }

        ) {
            Text(
                text = "Hard",
                modifier = Modifier
                    .padding(8.dp),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TicTacToeTheme {
        Greeting("Android")
    }
}