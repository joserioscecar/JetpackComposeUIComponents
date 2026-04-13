package co.edu.cecar.appcalculohipotecas

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlin.math.pow

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // enableEdgeToEdge()

        /*Estados*/

        setContent {

            Screen()

        }

    }

    @Composable
    fun Screen() {


        var precioInmuebleState by remember { mutableStateOf("120000000") }
        var cuotaInicialState by remember { mutableStateOf("20000000") }
        var tasaInteresAnualState by remember { mutableStateOf("23") }
        var plazoAnualState by remember { mutableStateOf("20") }

        var plazoMensualState by remember { mutableStateOf("0") }
        var tasaInteresMensualState by remember { mutableStateOf("0") }
        var valorFinanciedoState by remember { mutableStateOf("0") }
        var cuotaMensualState by remember { mutableStateOf("0") }

        val context = LocalContext.current

        Column(

            modifier = Modifier
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)

            /*
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)


             */

        ) {

            Text(text = "Calculadora de Hipotecas", style = MaterialTheme.typography.titleLarge)

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = precioInmuebleState,
                onValueChange = { precioInmuebleState = it },
                label = { Text("Precio del inmueble") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = cuotaInicialState,
                onValueChange = { cuotaInicialState = it },
                label = { Text("Couta inicial") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)

            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                OutlinedTextField(
                    modifier = Modifier.weight(2f),  // ocupa la mitad
                    value = tasaInteresAnualState,
                    onValueChange = { tasaInteresAnualState = it },
                    label = { Text("Tasa de intéres Anual") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
                )

                OutlinedTextField(
                    modifier = Modifier.weight(1f),  // ocupa la mitad
                    value = plazoAnualState,
                    onValueChange = { plazoAnualState = it },
                    label = { Text("Plazo Anual") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }


            Row(

                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)

            ) {

                Button(onClick = {

                    // 1. Todos los campos deben estar diligenciados
                    if (precioInmuebleState.isBlank() || cuotaInicialState.isBlank() ||
                        tasaInteresAnualState.isBlank() || plazoAnualState.isBlank()
                    ) {
                        Toast.makeText(
                            context,
                            "Todos los campos son obligatorios",
                            Toast.LENGTH_SHORT
                        ).show()
                        return@Button
                    }

                    // 2. Los valores deben ser numéricos
                    val valorInmueble = precioInmuebleState.toDoubleOrNull()
                    val cuotaInicial = cuotaInicialState.toDoubleOrNull()
                    val tasaInteresAnual = tasaInteresAnualState.toDoubleOrNull()
                    val plazoAnual = plazoAnualState.toIntOrNull()

                    if (valorInmueble == null || cuotaInicial == null ||
                        tasaInteresAnual == null || plazoAnual == null
                    ) {
                        Toast.makeText(
                            context,
                            "Los valores deben ser numéricos",
                            Toast.LENGTH_SHORT
                        ).show()
                        return@Button
                    }

                    // 3. Precio del inmueble mayor que cero
                    if (valorInmueble <= 0 || valorInmueble > 500_000_000) {
                        Toast.makeText(
                            context,
                            "El precio del inmueble debe ser mayor que cero y mejor que $ 500,000,000",
                            Toast.LENGTH_SHORT
                        ).show()
                        return@Button
                    }

                    // 4. Cuota inicial >= 0 y no puede superar el precio del inmueble
                    if (cuotaInicial < 0) {
                        Toast.makeText(
                            context,
                            "La cuota inicial no puede ser negativa",
                            Toast.LENGTH_SHORT
                        ).show()
                        return@Button
                    }
                    if (cuotaInicial >= valorInmueble) {
                        Toast.makeText(
                            context,
                            "La cuota inicial no puede superar el precio del inmueble",
                            Toast.LENGTH_SHORT
                        ).show()
                        return@Button
                    }

                    // 5. Tasa de interés mayor que cero
                    if (tasaInteresAnual <= 0) {
                        Toast.makeText(
                            context,
                            "La tasa de interés debe ser mayor que cero",
                            Toast.LENGTH_SHORT
                        ).show()
                        return@Button
                    }

                    // 6. Plazo debe ser entero positivo
                    if (plazoAnual <= 0) {
                        Toast.makeText(
                            context,
                            "El período del préstamo debe ser un número entero positivo",
                            Toast.LENGTH_SHORT
                        ).show()
                        return@Button
                    }

                    // 7. Valor financiado debe ser mayor que cero
                    val valorFinanciado = calcularValorFinanciado(valorInmueble, cuotaInicial)
                    if (valorFinanciado <= 0) {
                        Toast.makeText(
                            context,
                            "El valor financiado debe ser mayor que cero",
                            Toast.LENGTH_SHORT
                        ).show()
                        return@Button
                    }

                    // Todo valido — calcular
                    val tasaMensual = calcularTasaInteresMensual(tasaInteresAnual)
                    val plazoMensual = calcularPlazoMensual(plazoAnual)
                    val cuotaMensual =
                        calcularCuotaMensual(valorFinanciado, tasaMensual, plazoMensual)

                    valorFinanciedoState = valorFinanciado.toString()
                    tasaInteresMensualState = "%,.2f".format(tasaMensual)
                    plazoMensualState = plazoMensual.toString()
                    cuotaMensualState = cuotaMensual.toString()


                    Toast.makeText(
                        context,
                        "Calculos realzados, puedes ver el resultado",
                        Toast.LENGTH_SHORT
                    ).show()


                }) {
                    Text("Calcular")
                }

                Button(
                    onClick = {

                        precioInmuebleState = ""
                        cuotaInicialState = ""
                        tasaInteresAnualState = ""
                        plazoAnualState = ""
                        valorFinanciedoState = "0"
                        tasaInteresMensualState = "0"
                        plazoMensualState = "0"
                        cuotaMensualState = "0"

                    }, colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.secondary)
                ) {
                    Text(text = "Limpiar")
                }

            }




            Text(
                text = "Resultados",
                style = MaterialTheme.typography.titleMedium
            )

            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {


                    Spacer(modifier = Modifier.padding(vertical = 8.dp))

                    Row() {

                        Text(text = "Valor Financiado:")
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Right,
                            text = "$ ${"%,.0f".format(valorFinanciedoState.toDouble())}"
                        )
                    }

                    Spacer(modifier = Modifier.padding(vertical = 8.dp))

                    HorizontalDivider()

                    Spacer(modifier = Modifier.padding(vertical = 8.dp))

                    Row() {

                        Text(text = "Tasa mensual:")
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Right,
                            text = "% $tasaInteresMensualState"
                        )
                    }

                    Spacer(modifier = Modifier.padding(vertical = 8.dp))
                    HorizontalDivider()
                    Spacer(modifier = Modifier.padding(vertical = 8.dp))

                    Row() {

                        Text(text = "Plazo Mensual:")
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Right,
                            text = plazoMensualState
                        )
                    }

                    Spacer(modifier = Modifier.padding(vertical = 8.dp))
                    HorizontalDivider()
                    Spacer(modifier = Modifier.padding(vertical = 8.dp))


                    Row() {

                        Text(text = "Cuota Mensual:")
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Right,
                            text = "$ ${"%,.0f".format(cuotaMensualState.toDouble())}"
                        )
                    }

                }
            }


        }
    }



    fun calcularTasaInteresMensual(tasaInteresAnual: Double): Double {
        return tasaInteresAnual / 12

    }


    fun calcularCuotaMensual(valorHipeca: Double, tasaMensual: Double, plazoMensual: Int): Double {

        return valorHipeca + (tasaMensual * (1 + tasaMensual).pow(plazoMensual)) / ((1 + tasaMensual).pow(
            plazoMensual
        ) - 1)


    }

    fun calcularPlazoMensual(plazoAnual: Int): Int {
        return plazoAnual * 12
    }


    fun calcularValorFinanciado(valorInmueble: Double, cuotaInicial: Double): Double {

        return valorInmueble - cuotaInicial

    }

}
