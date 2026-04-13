package co.fluxis.calculadoranota

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.fluxis.calculadoranota.utils.formatear

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CalculadoraNotasScreen()
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalculadoraNotasScreen() {
    var notaCorte1 by remember { mutableStateOf("") }
    var notaQuiz by remember { mutableStateOf("") }
    var notaTaller by remember { mutableStateOf("") }
    var notaNecesaria by remember { mutableStateOf<Double?>(null) }
    var mensaje by remember { mutableStateOf("") }

    Scaffold(

        topBar = {

            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text("Calculo de NOTAS")
                }
            )

        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = notaCorte1,
                onValueChange = { notaCorte1 = it },
                label = { Text("Nota Corte 1 (40%)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )

            OutlinedTextField(
                value = notaQuiz,
                onValueChange = { notaQuiz = it },
                label = { Text("Nota Quiz (15%)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )

            OutlinedTextField(
                value = notaTaller,
                onValueChange = { notaTaller = it },
                label = { Text("Nota Taller (15%)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )



            if (notaNecesaria != null || mensaje.isNotEmpty()) {
                Spacer(modifier = Modifier.height(16.dp))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = mensaje,
                            style = MaterialTheme.typography.bodyLarge,
                            textAlign = TextAlign.Center
                        )

                        if (notaNecesaria != null && notaNecesaria!! in 0.0..5.0) {
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = notaNecesaria!!.formatear(2),
                                fontSize = 48.sp,
                                color = MaterialTheme.colorScheme.primary,
                                style = MaterialTheme.typography.displayLarge
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Proyecto (30%)",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }
            }

            Button(
                onClick = {
                    val corte1 = notaCorte1.toDoubleOrNull()
                    val quiz = notaQuiz.toDoubleOrNull()
                    val taller = notaTaller.toDoubleOrNull()

                    if (corte1 != null && quiz != null && taller != null) {
                        // Calcular lo acumulado hasta ahora
                        val acumulado = (corte1 * 0.40) + (quiz * 0.15) + (taller * 0.15)

                        // El proyecto vale 30% - calcular nota mínima necesaria para aprobar con 3.0
                        val notaProyecto = (3.0 - acumulado) / 0.30

                        notaNecesaria = notaProyecto

                        when {
                            notaProyecto > 5.0 -> {
                                mensaje = "No es posible aprobar. Necesitarías ${String.format("%.2f", notaProyecto)} en el proyecto"
                            }
                            notaProyecto < 0.0 -> {
                                mensaje = "¡Ya tienes la materia ganada con ${String.format("%.2f", acumulado)}!"
                            }
                            else -> {
                                mensaje = "Necesitas mínimo esta nota en el proyecto para aprobar con 3.0"
                            }
                        }
                    } else {
                        mensaje = "Por favor ingresa todas las notas"
                        notaNecesaria = null
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
            ) {
                Text("Calcular")
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Nota mínima para aprobar: 3.0 | Proyecto: 30%",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )
            
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
