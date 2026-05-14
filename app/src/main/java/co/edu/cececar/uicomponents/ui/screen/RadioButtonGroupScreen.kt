package co.edu.cececar.uicomponents.ui.screen

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposableTarget
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import co.edu.cececar.uicomponents.ComponentItem
import co.edu.cececar.uicomponents.RadioButtonGroup

@Composable

fun RadioButtonGroupScreen(){
    val context = LocalContext.current
    var seleccionRadioButton by remember { mutableStateOf<ComponentItem?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(25.dp)
    ) {

        RadioButtonGroup(

            items = listOf(
                ComponentItem("1", "Masculino"),
                ComponentItem("2", "Femenino"),
                ComponentItem("3", "Otro")
            ),
            selectedItem = seleccionRadioButton,
            onItemSelected = { seleccionRadioButton = it }

        ){
            Text("Genero")
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

    }

}
