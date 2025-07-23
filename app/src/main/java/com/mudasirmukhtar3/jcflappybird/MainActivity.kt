package com.mudasirmukhtar3.jcflappybird

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.mudasirmukhtar3.jcflappybird.game.GameScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            initiateGame()

        }
    }

}
@Composable
fun initiateGame(){
    AndroidView(
        factory = { ctx ->
            GameScreen(ctx)
        },
        modifier = Modifier.fillMaxSize()
    )
}