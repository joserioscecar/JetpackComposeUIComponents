package co.edu.cececar.uicomponents.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import co.edu.cececar.uicomponents.ui.component.ItemList

@Composable
fun ItemListScreen() {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(25.dp)
    ) {



        data class Producto(
            val sku: Int,
            val descripcion: String,
            val precio: Double,
            val stock: Int
        )

        val productos = listOf(
            Producto(sku = 170479, descripcion = "Lenovo ThinkPad X1 2026-1", precio = 5500000.0, stock = 25),
            Producto(sku = 180556, descripcion = "Lenovo ThinkPad X4 PROMAAX", precio = 8500000.0, stock = 100),
            Producto(sku = 186714, descripcion = "Impresora Pos Digitalpos Dig K200l USB USB+LAN Color Negro", precio = 250000.0, stock = 10),
            Producto(sku = 271317, descripcion = "Lenovo ThinkPad X1 Carbon", precio = 5500000.0, stock = 25),
            Producto(sku = 658677, descripcion = "Motorola Edge 50 Fusion 5g 256gb 8gb Azul Artico", precio = 1266100.0, stock = 200),
            Producto(sku = 888830, descripcion = "Lenovo ThinkPad X1 2026-132", precio = 5500000.0, stock = 25),
            Producto(sku = 945919, descripcion = "Miniprinter Epson Termica Tmt20iii-001 Usb Serial C31ch51001 Color Negro", precio = 725000.0, stock = 1000),
            Producto(sku = 974025, descripcion = "Lenovo ThinkPad X1 Carbon PRO", precio = 5500000.0, stock = 25),
            Producto(sku = 974026, descripcion = "MacBook Pro M4 MAX", precio = 12500000.0, stock = 10),
            Producto(sku = 974027, descripcion = "TUFF GAMING 4K", precio = 12500000.0, stock = 10),
            Producto(sku = 974031, descripcion = "DELL Otiplex CF", precio = 5500000.0, stock = 25),
            Producto(sku = 974032, descripcion = "Alienware 16", precio = 11226060.0, stock = 15),
            Producto(sku = 974033, descripcion = "Lenovo X1 Carbon", precio = 500000.0, stock = 25),
            Producto(sku = 974034, descripcion = "Lenovo ThinkPad X1 2026-2", precio = 5500000.0, stock = 25)
        )

        // Productos
        ItemList(
            items = productos,
            fields = { producto ->
                listOf(
                    "Descripción" to producto.descripcion,
                    "SKU" to "${producto.sku}",
                    "Precio" to "$${"%,.0f".format(producto.precio)}",
                    "Stock" to "${producto.stock}"
                )
            }
        )

    }
}