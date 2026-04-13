package co.fluxis.agenda.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.fluxis.agenda.data.model.response.ClienteRespoonse
import co.fluxis.agenda.data.repository.obtenerClientes


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClientesScreen(onLogout: () -> Unit) {

    var clientes by remember { mutableStateOf<List<ClienteRespoonse>>(emptyList()) }
    var error by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    var searchQuery by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        try {
            clientes = obtenerClientes()
        } catch (e: Exception) {
            error = e.message
        } finally {
            isLoading = false
        }
    }

    // Lógica de filtrado y límite de 10
    val filteredClientes = remember(searchQuery, clientes) {
        clientes.filter { cliente ->
            val nombreCompleto = "${cliente.nombres} ${cliente.apellidos}".lowercase()
            val documento = (cliente.documento ?: "").lowercase()
            nombreCompleto.contains(searchQuery.lowercase()) || 
            documento.contains(searchQuery.lowercase())
        }.take(10) // Limitar a 10 resultados
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Clientes") },
                actions = {
                    IconButton(onClick = onLogout) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.Logout,
                            contentDescription = "Cerrar Sesión"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Barra de Búsqueda
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                placeholder = { Text("Buscar por nombre o documento...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                singleLine = true,
                textStyle = TextStyle(fontSize = 20.sp),
            )

            Box(modifier = Modifier.weight(1f)) {
                if (isLoading) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                } else if (error != null) {
                    Text(
                        text = "Error: $error",
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(16.dp).align(Alignment.Center)
                    )
                } else if (filteredClientes.isEmpty()) {
                    Text(
                        text = if (searchQuery.isEmpty()) "No hay clientes" else "No se encontraron resultados",
                        modifier = Modifier.align(Alignment.Center)
                    )
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(filteredClientes) { cliente ->
                            ClienteItem(cliente)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ClienteItem(cliente: ClienteRespoonse) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = "${cliente.nombres ?: ""} ${cliente.apellidos ?: ""}",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Documento: ${cliente.documento ?: "N/A"}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Correo: ${cliente.correo ?: "N/A"}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.secondary
            )
            if (cliente.celular != null) {
                Text(
                    text = "Celular: ${cliente.celular}",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}
