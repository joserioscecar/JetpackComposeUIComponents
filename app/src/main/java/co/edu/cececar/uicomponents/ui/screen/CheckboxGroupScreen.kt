package co.edu.cececar.uicomponents.ui.screen

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import co.edu.cececar.uicomponents.CheckboxGroup
import co.edu.cececar.uicomponents.CheckboxItem

@Composable
fun CheckboxGroupScreen() {

    val context = LocalContext.current

    var opciones by remember {
        mutableStateOf(
            listOf(
                CheckboxItem("1", "Acepto terminos"),
                CheckboxItem("2", "Recibir noticias"),
                CheckboxItem("3", "Guardar sesion")
            )
        )
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(25.dp)
    ) {

        CheckboxGroup(
            items = opciones,
            onItemToggled = { toggled ->
                opciones = opciones.map { item ->
                    if (item.id == toggled.id) item.copy(checked = !item.checked)
                    else item
                }
            }
        ) {
            Text("Preferencias")
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