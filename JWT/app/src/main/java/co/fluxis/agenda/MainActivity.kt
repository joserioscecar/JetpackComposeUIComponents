package co.fluxis.agenda

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import co.fluxis.agenda.ui.navigation.AppNavigation
import co.fluxis.agenda.ui.theme.JWTTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JWTTheme {

                AppNavigation()
            }
        }
    }
}
