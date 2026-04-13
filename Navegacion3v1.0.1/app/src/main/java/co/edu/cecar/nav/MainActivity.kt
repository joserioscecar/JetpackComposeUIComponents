package co.edu.cecar.nav

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import co.edu.cecar.nav.navigation.AppNavigation
import co.edu.cecar.nav.ui.theme.Navegacion3v101Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Navegacion3v101Theme {
                AppNavigation();
            }
        }
    }
}
