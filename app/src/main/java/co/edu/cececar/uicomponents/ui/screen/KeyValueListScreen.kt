package co.edu.cececar.uicomponents.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import co.edu.cececar.uicomponents.ui.component.KeyValueList

@Composable
fun KeyValueListScreen() {


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(25.dp)
    ) {

        data class Producto(

            val sku: Int,
            val nombre: String,
            val precio: Double,
            val cantidad: Int,
        )

        val producto = Producto(323232,"Arroz", 3500.0, 5)


        Text("Mínimo — se adapta solo", fontWeight = FontWeight.Bold)

// Mínimo — se adapta solo
        KeyValueList(item = producto)
        Spacer(modifier = Modifier.height(35.dp))

        Text("Ignorar propiedades", fontWeight = FontWeight.Bold)

// Ignorar propiedades
        KeyValueList(
            item = producto,
            exclude = listOf("sku")
        )

        Spacer(modifier = Modifier.height(35.dp))

        Text("Renombrar propiedades", fontWeight = FontWeight.Bold)

// Renombrar propiedades
        KeyValueList(
            item = producto,
            labels = mapOf(
                "nombre"   to "Producto",
                "precio"   to "Precio unitario",
                "cantidad" to "Unidades",
            )
        )

    }
}