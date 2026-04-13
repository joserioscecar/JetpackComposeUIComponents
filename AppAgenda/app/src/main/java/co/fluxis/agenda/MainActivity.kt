package co.fluxis.agenda

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import co.fluxis.agenda.screens.LoginScreen
import co.fluxis.agenda.screens.MainScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                App()   // 👈 carga toda la app
            }
        }
    }
}


@Composable
fun App() {
    var isLoggedIn by remember { mutableStateOf(false) }

    if (isLoggedIn) {
        MainScreen()
    } else {
        LoginScreen {
            isLoggedIn = true
        }
    }
}