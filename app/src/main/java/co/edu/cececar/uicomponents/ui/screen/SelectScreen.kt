package co.edu.cececar.uicomponents.ui.screen

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
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
import co.edu.cececar.uicomponents.ui.component.SelectField
import co.edu.cececar.uicomponents.ui.component.AutocompleteField

@Composable
fun SelectScreen() {

    var tipoDocumentoSeleccionado by remember { mutableStateOf<ComponentItem?>(null) }
    var seleccionBusqueda by remember { mutableStateOf<ComponentItem?>(null) }

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
        SelectField(
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

    }
}