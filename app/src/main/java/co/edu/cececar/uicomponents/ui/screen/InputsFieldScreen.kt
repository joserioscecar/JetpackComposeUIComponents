package co.edu.cececar.uicomponents.ui.screen

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
import androidx.compose.ui.unit.dp
import co.edu.cececar.uicomponents.ui.component.EmailField
import co.edu.cececar.uicomponents.ui.component.EmailFieldConfig
import co.edu.cececar.uicomponents.ui.component.NumberField
import co.edu.cececar.uicomponents.ui.component.NumberFieldConfig
import co.edu.cececar.uicomponents.ui.component.NumberType
import co.edu.cececar.uicomponents.ui.component.PasswordField
import co.edu.cececar.uicomponents.ui.component.SearchField
import co.edu.cececar.uicomponents.ui.component.SearchFieldConfig
import co.edu.cececar.uicomponents.ui.component.formatThousands
import co.edu.cececar.uicomponents.ui.component.isValidEmail

@Composable
fun PasswordFieldScreen() {


    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(25.dp)
    ) {

        Text("PasswordField")

        PasswordField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            placeholder = { Text("Ingresa tu contraseña") },
            isError = password.length < 8 && password.isNotEmpty(),
            errorMessage = "Minimo 8 caracteres"
        )


        Spacer(modifier = Modifier.height(20.dp))
        Text("NumberField")

        var cantidad by remember { mutableStateOf<Int?>(null) }
        var precio by remember { mutableStateOf<Double?>(null) }
        var salario by remember { mutableStateOf<Double?>(null) }

// Entero con min/max
        NumberField(
            value = cantidad,
            onValueChange = { cantidad = it as? Int },
            config = NumberFieldConfig(
                label = "Cantidad",
                minValue = 1.0,
                maxValue = 9999.0,
                suffix = "und"
            )
        )

// Decimal tipo precio
        NumberField(
            value = precio,
            onValueChange = { precio = it?.toDouble() },
            config = NumberFieldConfig(
                label = "Precio",
                numberType = NumberType.Decimal(decimals = 2),
                minValue = 0.0,
                prefix = "$"
            )
        )

// Salario con rango
        NumberField(
            value = salario,
            onValueChange = { salario = it?.toDouble() },
            config = NumberFieldConfig(
                label = "Salario mensual",
                numberType = NumberType.Decimal(),
                minValue = 1_300_000.0,
                maxValue = 50_000_000.0,
                prefix = "$",
                supportingText = "Entre $1.300.000 y $50.000.000"
            )
        )

// Leer valores
        println(cantidad)               // 150
        println(precio)                 // 3500.0
        println(salario?.formatThousands()) // "2.500.000"


        Spacer(modifier = Modifier.height(20.dp))
        Text("EmailField")


        var email by remember { mutableStateOf("") }

// Mínimo
        EmailField(
            value = email,
            onValueChange = { email = it }
        )

// Con configuración
        EmailField(
            value = email,
            onValueChange = { email = it },
            config = EmailFieldConfig(
                label = "Email corporativo",
                clearable = true,
                validateOnChange = true
            )
        )

// Leer y validar antes de enviar
        Button(onClick = {
            if (email.isValidEmail()) {
                println("Email válido: $email")
            }
        }) {
            Text("Enviar")
        }


        var query by remember { mutableStateOf("") }
        var resultado by remember { mutableStateOf("") }

        SearchField(
            value = query,
            onValueChange = { query = it },
            onSearch = { texto ->
                resultado = "Buscando: $texto"
            },
            config = SearchFieldConfig(
                placeholder = "Buscar.."
            )
        )

// Mostrar resultado
        if (resultado.isNotEmpty()) {
            Text(resultado)
        }

    }


}