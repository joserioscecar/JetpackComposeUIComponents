package co.edu.cececar.uicomponents.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import co.edu.cececar.uicomponents.ui.component.DataList

@Composable
fun KeyValueListScreen() {


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(25.dp)
            .verticalScroll(rememberScrollState())  // ← agrega esta línea
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
        DataList(item = producto)
        Spacer(modifier = Modifier.height(35.dp))

        Text("Ignorar propiedades", fontWeight = FontWeight.Bold)

// Ignorar propiedades
        DataList(
            item = producto,
            exclude = listOf("sku")
        )

        Spacer(modifier = Modifier.height(35.dp))

        Text("Renombrar propiedades", fontWeight = FontWeight.Bold)

// Renombrar propiedades
        DataList(
            item = producto,
            labels = mapOf(
                "nombre"   to "Producto",
                "precio"   to "Precio unitario",
                "cantidad" to "Unidades",
            )
        )


        val productos = listOf(
            Producto(323292,"Arroz", 3500.0, 5),
            Producto(345231,"Leche", 4200.0, 2),
            Producto(124233,"Pan",   1800.0, 3),
        )

        Spacer(modifier = Modifier.height(50.dp))

        Text("Lista de objetos", fontWeight = FontWeight.Bold)

// Lista de objetos
        DataList(items = productos)


        Spacer(modifier = Modifier.height(50.dp))

        Text("Lista de objetos con opciones", fontWeight = FontWeight.Bold)

// Con opciones
        DataList(
            items = productos,
            exclude = listOf("id"),
            labels  = mapOf("nombre" to "Producto", "precio" to "Precio unitario")
        )

    }
}