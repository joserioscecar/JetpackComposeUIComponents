package co.fluxis.appcredito

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import co.fluxis.appcredito.ui.screen.CreditoScreen
import co.fluxis.appcredito.ui.theme.AppCreditoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppCreditoTheme {

                CreditoScreen()
            }
        }
    }
}

