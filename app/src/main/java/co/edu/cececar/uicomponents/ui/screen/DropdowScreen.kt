package co.edu.cececar.uicomponents.ui.screen

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import co.edu.cececar.uicomponents.ui.component.ComponentItem
import co.edu.cececar.uicomponents.ui.component.Dropdow

@Composable
fun DropdowScreen() {

    var tipoDocumentoSeleccionado by remember { mutableStateOf<ComponentItem?>(null) }
    var tipoSangreSeleccionado by remember { mutableStateOf<String>("") }
    var tipoRiezgo by remember { mutableStateOf<Int?>(null) }
    val context = LocalContext.current

    val tiposDocumentos = listOf(
        ComponentItem("CC", "Cédula de ciudadanía"),
        ComponentItem("TI", "Tarjeta de identidad"),
        ComponentItem("CE", "Cedula de Extranjería"),
        ComponentItem("PA", "Pasaporte")
    )



    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(25.dp)
    ) {



        Spacer(modifier = Modifier.height(16.dp))   // ← Spacer DESPUÉS del campo, no antes

        // ── Tipo de documento ─────────────────────────────────────────────────
        Dropdow(
            items = tiposDocumentos,
            selectedItem = tipoDocumentoSeleccionado,
            onItemSelected = { item ->
                tipoDocumentoSeleccionado = item
                Toast.makeText(
                    context,
                    "ID: ${tipoDocumentoSeleccionado?.id} - ${tipoDocumentoSeleccionado?.text}",
                    Toast.LENGTH_SHORT
                ).show()
            },
            label = "Tipo de documento",
            itemLabel = { it.text }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            Toast.makeText(
                context,
                "ID: ${tipoDocumentoSeleccionado?.id} - ${tipoDocumentoSeleccionado?.text}",
                Toast.LENGTH_SHORT
            ).show()
        }) {
            Text("Ver selección")
        }


        Spacer(modifier = Modifier.height(20.dp))

        val tiposDeSangre = listOf("A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-")


        Dropdow(
            items = tiposDeSangre,
            label = "Tipo de Sangre",
            selectedItem = tipoSangreSeleccionado,
            onItemSelected = { tipoSangreSeleccionado = it }
        )


        val riezgos = listOf(1, 2, 3, 4)

        Spacer(modifier = Modifier.height(20.dp))

        Dropdow(
            items = riezgos,
            label = "Riesgo Laboral",
            selectedItem = tipoRiezgo,
            onItemSelected = { tipoRiezgo = it }
        )

    }
}