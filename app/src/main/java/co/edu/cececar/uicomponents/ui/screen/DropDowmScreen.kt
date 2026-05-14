package co.edu.cececar.uicomponents.ui.screen

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
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
import co.edu.cececar.uicomponents.ui.component.DropdownField
import co.edu.cececar.uicomponents.ui.component.SearchableDropdownField

@Composable
fun DropDowmScreen() {

    var tipoDocumentoSeleccionado by remember { mutableStateOf<ComponentItem?>(null) }
    var seleccionBusqueda by remember { mutableStateOf<ComponentItem?>(null) }

    val context = LocalContext.current

    val tiposDocumentos = listOf(
        ComponentItem("CC", "Cédula de ciudadanía"),
        ComponentItem("TI", "Tarjeta de identidad"),
        ComponentItem("CE", "Cedula de Extranjería"),
        ComponentItem("PA", "Pasaporte")
    )

    val ciudades = listOf(
        ComponentItem("1", "Bogotá"),
        ComponentItem("2", "Medellin"),
        ComponentItem("3", "Cali"),
        ComponentItem("4", "Cartagena"),
        ComponentItem("5", "Barranquilla"),
        ComponentItem("6", "Bucaramanga"),
        ComponentItem("7", "Pereira"),
        ComponentItem("8", "Manizales"),
        ComponentItem("9", "Santa Marta"),
        ComponentItem("10", "Cucuta"),
        ComponentItem("11", "Sincelejo"),
        ComponentItem("12", "Montería")
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(25.dp)
    ) {

        Text(
            text = "Ciudad",
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        SearchableDropdownField(
            items = ciudades,
            selectedItem = seleccionBusqueda,
            onItemSelected = { seleccionBusqueda = it },
            placeholder = "Buscar ciudad..."
        )
        Text(
            text = "Tipo de documento",
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier.padding(bottom = 4.dp)
        )

        DropdownField(
            items = tiposDocumentos,
            selectedItem = tipoDocumentoSeleccionado,
            onItemSelected = { item ->
                tipoDocumentoSeleccionado = item
            },
            placeholder = "Elige un tipo de documento",
            itemLabel = { it.text }
        )


        Button(onClick = {
            Toast.makeText(
                context,
                "ID: ${tipoDocumentoSeleccionado?.id} - ${tipoDocumentoSeleccionado?.text}" ,
                Toast.LENGTH_SHORT
            ).show()
        }) {
            Text("Mostrar Dropdown Item")
        }


        Button(onClick = {
            Toast.makeText(
                context,
                "ID: ${seleccionBusqueda?.id} - ${seleccionBusqueda?.text}",
                Toast.LENGTH_SHORT
            ).show()
        }) {
            Text("Mostrar ciudad")
        }


    }
}