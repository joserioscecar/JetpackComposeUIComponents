package co.edu.cececar.uicomponents

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // enableEdgeToEdge()


        setContent {

            MainScreen()
        }

    }
}

@Composable
fun MainScreen(){

    val context = LocalContext.current

    var seleccionRadioButton by remember { mutableStateOf<ComponentItem?>(null) }
    var seleccionDropdown by remember { mutableStateOf<ComponentItem?>(null) }
    var seleccionBusqueda by remember { mutableStateOf<ComponentItem?>(null) }

    var opciones by remember {
        mutableStateOf(
            listOf(
                CheckboxItem("1", "Acepto terminos"),
                CheckboxItem("2", "Recibir noticias"),
                CheckboxItem("3", "Guardar sesion")
            )
        )
    }

    val tiposDocumentos = listOf(
        ComponentItem("CC", "Cédula de ciudadanía"),
        ComponentItem("TI", "Tarjeta de identidad"),
        ComponentItem("CE", "Cedula de Extranjería"),
        ComponentItem("PA", "Pasaporte"))

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
            items =  tiposDocumentos,
            selectedItem = seleccionDropdown,
            onItemSelected = { seleccionDropdown = it },
            placeholder = "Elige un tipo de documento"
        )

        RadioButtonGroup(

            items = listOf(
                ComponentItem("1", "Masculino"),
                ComponentItem("2", "Femenino"),
                ComponentItem("3", "Otro")
            ),
            selectedId = seleccionRadioButton?.id,
            onItemSelected = { seleccionRadioButton = it }

        ){
            Text("Genero")
        }

        CheckboxGroup(
            items = opciones,
            onItemToggled = { toggled ->
                opciones = opciones.map { item ->
                    if (item.id == toggled.id) item.copy(checked = !item.checked)
                    else item
                }
            }
        ){
            Text("Preferencias")
        }

        Button(onClick = {
            Toast.makeText(
                context,
                "ID: ${seleccionDropdown?.id} - ${seleccionDropdown?.text}" ,
                Toast.LENGTH_SHORT
            ).show()
        }) {
            Text("Mostrar Dropdown Item")
        }


        Button(onClick = {
            Toast.makeText(
                context,
                "ID: ${seleccionRadioButton?.id} - ${seleccionRadioButton?.text}" ,
                Toast.LENGTH_SHORT
            ).show()
        }) {
            Text("Mostrar RadioButton")
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

        Button(onClick = {
            val seleccionados = opciones.filter { it.checked }
            val mensaje = if (seleccionados.isEmpty()) {
                "Ninguno seleccionado"
            } else {
                seleccionados.joinToString(separator = "\n") { "ID: ${it.id} - ${it.text}" }
            }
            Toast.makeText(context, mensaje, Toast.LENGTH_LONG).show()
        }) {
            Text("Mostrar checks seleccionados")
        }

    }

}
