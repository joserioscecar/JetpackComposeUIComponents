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
import co.edu.cececar.uicomponents.ui.component.Autocomplete

@Composable
fun AutoCompleteScreen() {

    var seleccionCiudad by remember { mutableStateOf<ComponentItem?>(null) }

    val context = LocalContext.current


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

        // ── Ciudad ────────────────────────────────────────────────────────────
        Autocomplete(
            items = ciudades,
            selectedItem = seleccionCiudad,
            onItemSelected = { item ->
                seleccionCiudad = item


                Toast.makeText(
                    context,
                    "ID: ${seleccionCiudad?.id} - ${seleccionCiudad?.text}",
                    Toast.LENGTH_SHORT
                ).show()

            },
            placeholder = "Buscar ciudad...",
            label = "Ciudad",
        )

        Spacer(modifier = Modifier.height(16.dp))   // ← Spacer DESPUÉS del campo, no antes


        Button(onClick = {
            Toast.makeText(
                context,
                "ID: ${seleccionCiudad?.id} - ${seleccionCiudad?.text}",
                Toast.LENGTH_SHORT
            ).show()
        }) {
            Text("Mostrar ciudad")
        }

        val profesiones = listOf(
            "Ingeniero",
            "Doctor",
            "Abogado",
            "Profesor",
            "Arquitecto",
            "Diseñador",
            "Programador",
            "Contador",
            "Administrador",
            "Enfermero",
            "Psicólogo",
            "Odontólogo",
            "Electricista",
            "Mecánico",
            "Chef",
            "Periodista",
            "Fotógrafo",
            "Policía",
            "Bombero",
            "Veterinario"
        )

        HorizontalDivider()

        var seleccionProfesion by remember { mutableStateOf<String?>(null) }

        Autocomplete(
            items = profesiones,
            selectedItem = seleccionProfesion,
            onItemSelected = { item ->
                seleccionProfesion = item

                Toast.makeText(
                    context,
                    seleccionProfesion,
                    Toast.LENGTH_SHORT
                ).show()

            },
            placeholder = "Buscar profesión...",
            label = "Profesión",
        )

    }




}