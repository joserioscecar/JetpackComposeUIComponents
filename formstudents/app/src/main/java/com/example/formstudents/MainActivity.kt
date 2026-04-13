package com.example.formstudents

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StudentRegistrationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    StudentRegistrationScreen()
                }
            }
        }
    }
}

@Composable
fun StudentRegistrationTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = lightColorScheme(
            primary = MaterialTheme.colorScheme.primary,
            secondary = MaterialTheme.colorScheme.secondary,
        ),
        content = content
    )
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentRegistrationScreen() {
    var nombre by remember { mutableStateOf("") }
    var apellido by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var direccion by remember { mutableStateOf("") }
    var fechaNacimiento by remember { mutableStateOf("") }
    var genero by remember { mutableStateOf("") }
    var grado by remember { mutableStateOf("") }
    var aceptaTerminos by remember { mutableStateOf(false) }

    var nombreError by remember { mutableStateOf(false) }
    var apellidoError by remember { mutableStateOf(false) }
    var emailError by remember { mutableStateOf(false) }
    var telefonoError by remember { mutableStateOf(false) }
    var direccionError by remember { mutableStateOf(false) }
    var fechaError by remember { mutableStateOf(false) }

    var showDialog by remember { mutableStateOf(false) }
    var showDatePicker by remember { mutableStateOf(false) }
    var expandedGenero by remember { mutableStateOf(false) }
    var expandedGrado by remember { mutableStateOf(false) }
    var showSnackbar by remember { mutableStateOf(false) }

    val grados = listOf(
        "1° Primaria", "2° Primaria", "3° Primaria",
        "4° Primaria", "5° Primaria", "6° Primaria",
        "1° Secundaria", "2° Secundaria", "3° Secundaria"
    )

    val generos = listOf("Masculino", "Femenino", "Otro")

    val snackbarHostState = remember { SnackbarHostState() }

    fun validarFormulario(): Boolean {
        nombreError = nombre.isBlank()
        apellidoError = apellido.isBlank()
        emailError = email.isBlank() || !email.contains("@")
        telefonoError = telefono.isBlank()
        direccionError = direccion.isBlank()
        fechaError = fechaNacimiento.isBlank()

        return !nombreError && !apellidoError && !emailError &&
                !telefonoError && !direccionError && !fechaError &&
                genero.isNotBlank() && grado.isNotBlank()
    }

    fun limpiarFormulario() {
        nombre = ""
        apellido = ""
        email = ""
        telefono = ""
        direccion = ""
        fechaNacimiento = ""
        genero = ""
        grado = ""
        aceptaTerminos = false
        nombreError = false
        apellidoError = false
        emailError = false
        telefonoError = false
        direccionError = false
        fechaError = false
    }

    LaunchedEffect(showSnackbar) {
        if (showSnackbar) {
            snackbarHostState.showSnackbar(
                message = "Debe aceptar los términos y condiciones",
                duration = SnackbarDuration.Short
            )
            showSnackbar = false
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Inscripción de Estudiantes") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(20.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = "Datos del Estudiante",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 20.dp)
            )

            // Nombre
            OutlinedTextField(
                value = nombre,
                onValueChange = {
                    nombre = it
                    nombreError = false
                },
                label = { Text("Nombre") },
                leadingIcon = { Icon(Icons.Default.Person, "Nombre") },
                isError = nombreError,
                supportingText = {
                    if (nombreError) Text("Por favor ingrese el nombre")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp)
            )

            // Apellido
            OutlinedTextField(
                value = apellido,
                onValueChange = {
                    apellido = it
                    apellidoError = false
                },
                label = { Text("Apellido") },
                leadingIcon = { Icon(Icons.Default.Person, "Apellido") },
                isError = apellidoError,
                supportingText = {
                    if (apellidoError) Text("Por favor ingrese el apellido")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp)
            )

            // Email
            OutlinedTextField(
                value = email,
                onValueChange = {
                    email = it
                    emailError = false
                },
                label = { Text("Correo Electrónico") },
                leadingIcon = { Icon(Icons.Default.Email, "Email") },
                isError = emailError,
                supportingText = {
                    if (emailError) Text("Ingrese un correo válido")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp)
            )

            // Teléfono
            OutlinedTextField(
                value = telefono,
                onValueChange = {
                    telefono = it
                    telefonoError = false
                },
                label = { Text("Teléfono") },
                leadingIcon = { Icon(Icons.Default.Phone, "Teléfono") },
                isError = telefonoError,
                supportingText = {
                    if (telefonoError) Text("Por favor ingrese el teléfono")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp)
            )

            // Fecha de Nacimiento
            OutlinedTextField(
                value = fechaNacimiento,
                onValueChange = { },
                label = { Text("Fecha de Nacimiento") },
                leadingIcon = { Icon(Icons.Default.DateRange, "Fecha") },
                readOnly = true,
                isError = fechaError,
                supportingText = {
                    if (fechaError) Text("Por favor seleccione la fecha")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp),
                trailingIcon = {
                    IconButton(onClick = { showDatePicker = true }) {
                        Icon(Icons.Default.DateRange, "Seleccionar fecha")
                    }
                }
            )

            // Género
            ExposedDropdownMenuBox(
                expanded = expandedGenero,
                onExpandedChange = { expandedGenero = !expandedGenero },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp)
            ) {
                OutlinedTextField(
                    value = genero,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Género") },
                    leadingIcon = { Icon(Icons.Default.Person, "Género") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedGenero) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor()
                )
                ExposedDropdownMenu(
                    expanded = expandedGenero,
                    onDismissRequest = { expandedGenero = false }
                ) {
                    generos.forEach { item ->
                        DropdownMenuItem(
                            text = { Text(item) },
                            onClick = {
                                genero = item
                                expandedGenero = false
                            }
                        )
                    }
                }
            }

            // Grado
            ExposedDropdownMenuBox(
                expanded = expandedGrado,
                onExpandedChange = { expandedGrado = !expandedGrado },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp)
            ) {
                OutlinedTextField(
                    value = grado,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Grado a Inscribir") },
                    leadingIcon = { Icon(Icons.Default.Star, "Grado") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedGrado) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor()
                )
                ExposedDropdownMenu(
                    expanded = expandedGrado,
                    onDismissRequest = { expandedGrado = false }
                ) {
                    grados.forEach { item ->
                        DropdownMenuItem(
                            text = { Text(item) },
                            onClick = {
                                grado = item
                                expandedGrado = false
                            }
                        )
                    }
                }
            }

            // Dirección
            OutlinedTextField(
                value = direccion,
                onValueChange = {
                    direccion = it
                    direccionError = false
                },
                label = { Text("Dirección") },
                leadingIcon = { Icon(Icons.Default.LocationOn, "Dirección") },
                isError = direccionError,
                supportingText = {
                    if (direccionError) Text("Por favor ingrese la dirección")
                },
                minLines = 3,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp)
            )

            // Términos y condiciones
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = aceptaTerminos,
                    onCheckedChange = { aceptaTerminos = it }
                )
                Text("Acepto los términos y condiciones")
            }

            // Botones
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(
                    onClick = {
                        if (validarFormulario()) {
                            if (!aceptaTerminos) {
                                showSnackbar = true
                            } else {
                                showDialog = true
                            }
                        }
                    },
                    modifier = Modifier
                        .weight(1f)
                        .height(50.dp)
                ) {
                    Text("Inscribir")
                }

                OutlinedButton(
                    onClick = { limpiarFormulario() },
                    modifier = Modifier
                        .weight(1f)
                        .height(50.dp)
                ) {
                    Text("Limpiar")
                }
            }
        }

        // Diálogo de confirmación
        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text("¡Inscripción Exitosa!") },
                text = {
                    Text(
                        "Estudiante: $nombre $apellido\n" +
                                "Grado: $grado\n" +
                                "Email: $email"
                    )
                },
                confirmButton = {
                    TextButton(onClick = {
                        showDialog = false
                        limpiarFormulario()
                    }) {
                        Text("Aceptar")
                    }
                }
            )
        }

        // DatePicker (simulado con texto)
        if (showDatePicker) {
            AlertDialog(
                onDismissRequest = { showDatePicker = false },
                title = { Text("Seleccionar Fecha") },
                text = {
                    Text("Aquí iría un DatePicker.\nPor ahora, ingresamos una fecha de ejemplo.")
                },
                confirmButton = {
                    TextButton(onClick = {
                        fechaNacimiento = "15/05/2010"
                        fechaError = false
                        showDatePicker = false
                    }) {
                        Text("Seleccionar")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDatePicker = false }) {
                        Text("Cancelar")
                    }
                }
            )
        }
    }
}