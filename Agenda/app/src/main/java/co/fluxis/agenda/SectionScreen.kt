package co.fluxis.agenda


import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

// Colores (mismos del dashboard principal)
private val DeepCharcoal = Color(0xFF1A1D23)
private val SageGreen = Color(0xFF7BA68A)
private val LightSage = Color(0xFF9FC4A8)
private val CreamWhite = Color(0xFFF5F5F0)
private val WarmGray = Color(0xFFB8B8B0)
private val AccentAmber = Color(0xFFE8A542)
private val ContentBg = Color(0xFFF8F8F5)
private val CardWhite = Color(0xFFFFFFFF)

// ==================== HORARIOS SCREEN ====================

data class TimeSlot(
    val time: String,
    val isAvailable: Boolean,
    val appointments: Int = 0
)

@Composable
fun HorariosScreen() {
    var visible by remember { mutableStateOf(false) }
    var selectedDay by remember { mutableStateOf("Lunes") }

    LaunchedEffect(Unit) {
        visible = true
    }

    val days = listOf("Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo")
    val timeSlots = remember {
        listOf(
            TimeSlot("09:00", true, 2),
            TimeSlot("10:00", true, 3),
            TimeSlot("11:00", false, 0),
            TimeSlot("12:00", true, 1),
            TimeSlot("13:00", true, 4),
            TimeSlot("14:00", false, 0),
            TimeSlot("15:00", true, 2),
            TimeSlot("16:00", true, 3),
            TimeSlot("17:00", true, 1),
            TimeSlot("18:00", true, 2)
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        // Days Selector
        AnimatedVisibility(
            visible = visible,
            enter = fadeIn(tween(400)) + slideInVertically(
                initialOffsetY = { -30 },
                animationSpec = tween(500)
            )
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = CardWhite)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    days.forEach { day ->
                        DayChip(
                            day = day,
                            isSelected = selectedDay == day,
                            onClick = { selectedDay = day },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
        }

        // Time Slots Grid
        AnimatedVisibility(
            visible = visible,
            enter = fadeIn(tween(400, delayMillis = 100)) + slideInVertically(
                initialOffsetY = { 30 },
                animationSpec = tween(500, delayMillis = 100)
            )
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = CardWhite)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp)
                ) {
                    Text(
                        text = "Horarios de $selectedDay",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = DeepCharcoal
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    LazyVerticalGrid(
                        columns = GridCells.Fixed(3),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(timeSlots) { slot ->
                            TimeSlotCard(slot = slot)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun DayChip(
    day: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(
                if (isSelected) SageGreen else ContentBg
            )
            .clickable { onClick() }
            .padding(vertical = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = day.take(3),
            fontSize = 12.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
            color = if (isSelected) CreamWhite else WarmGray
        )
    }
}

@Composable
private fun TimeSlotCard(slot: TimeSlot) {
    Card(
        modifier = Modifier.aspectRatio(1f),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (slot.isAvailable) SageGreen.copy(alpha = 0.1f) else ContentBg
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(
                imageVector = if (slot.isAvailable) Icons.Filled.CheckCircle else Icons.Filled.Block,
                contentDescription = null,
                tint = if (slot.isAvailable) SageGreen else WarmGray,
                modifier = Modifier.size(20.dp)
            )

            Text(
                text = slot.time,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = DeepCharcoal
            )

            if (slot.isAvailable && slot.appointments > 0) {
                Text(
                    text = "${slot.appointments} citas",
                    fontSize = 10.sp,
                    color = WarmGray
                )
            }
        }
    }
}

// ==================== CLIENTES SCREEN ====================

data class Cliente(
    val id: String,
    val nombre: String,
    val telefono: String,
    val email: String,
    val ultimaVisita: String,
    val totalVisitas: Int
)

@Composable
fun ClientesScreen() {
    var visible by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        visible = true
    }

    val clientes = remember {
        listOf(
            Cliente("1", "María González", "+57 300 123 4567", "maria@email.com", "Hace 2 días", 15),
            Cliente("2", "Carlos Rodríguez", "+57 310 234 5678", "carlos@email.com", "Hace 1 semana", 8),
            Cliente("3", "Ana Martínez", "+57 320 345 6789", "ana@email.com", "Hace 3 días", 22),
            Cliente("4", "Luis Pérez", "+57 300 456 7890", "luis@email.com", "Hoy", 5),
            Cliente("5", "Sofia López", "+57 315 567 8901", "sofia@email.com", "Hace 4 días", 12)
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        // Search Bar
        AnimatedVisibility(
            visible = visible,
            enter = fadeIn(tween(400)) + slideInVertically(
                initialOffsetY = { -30 },
                animationSpec = tween(500)
            )
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Buscar cliente...") },
                leadingIcon = {
                    Icon(Icons.Filled.Search, contentDescription = null)
                },
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = CardWhite,
                    unfocusedContainerColor = CardWhite,
                    focusedBorderColor = SageGreen,
                    unfocusedBorderColor = ContentBg
                )
            )
        }

        // Stats Row
        AnimatedVisibility(
            visible = visible,
            enter = fadeIn(tween(400, delayMillis = 100)) + slideInVertically(
                initialOffsetY = { -30 },
                animationSpec = tween(500, delayMillis = 100)
            )
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                ClientStatCard(
                    title = "Total Clientes",
                    value = "127",
                    icon = Icons.Filled.People,
                    modifier = Modifier.weight(1f)
                )
                ClientStatCard(
                    title = "Nuevos (mes)",
                    value = "12",
                    icon = Icons.Filled.PersonAdd,
                    modifier = Modifier.weight(1f)
                )
                ClientStatCard(
                    title = "Activos",
                    value = "89",
                    icon = Icons.Filled.TrendingUp,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        // Clients List
        AnimatedVisibility(
            visible = visible,
            enter = fadeIn(tween(400, delayMillis = 200)) + slideInVertically(
                initialOffsetY = { 30 },
                animationSpec = tween(500, delayMillis = 200)
            )
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = CardWhite)
            ) {
                LazyColumn(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(clientes) { cliente ->
                        ClienteCard(cliente = cliente)
                    }
                }
            }
        }
    }
}

@Composable
private fun ClientStatCard(
    title: String,
    value: String,
    icon: ImageVector,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = CardWhite),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(SageGreen.copy(alpha = 0.1f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = SageGreen,
                    modifier = Modifier.size(20.dp)
                )
            }

            Column {
                Text(
                    text = value,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = DeepCharcoal
                )
                Text(
                    text = title,
                    fontSize = 11.sp,
                    color = WarmGray
                )
            }
        }
    }
}

@Composable
private fun ClienteCard(cliente: Cliente) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = ContentBg)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                // Avatar
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(
                            Brush.linearGradient(
                                colors = listOf(SageGreen, LightSage)
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = cliente.nombre.take(2).uppercase(),
                        color = CreamWhite,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Column {
                    Text(
                        text = cliente.nombre,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = DeepCharcoal
                    )
                    Text(
                        text = cliente.telefono,
                        fontSize = 12.sp,
                        color = WarmGray
                    )
                    Text(
                        text = "${cliente.totalVisitas} visitas • ${cliente.ultimaVisita}",
                        fontSize = 11.sp,
                        color = SageGreen
                    )
                }
            }

            IconButton(onClick = { }) {
                Icon(
                    imageVector = Icons.Filled.ChevronRight,
                    contentDescription = "Ver detalles",
                    tint = WarmGray
                )
            }
        }
    }
}

// ==================== RESERVAS SCREEN ====================

data class Reserva(
    val id: String,
    val cliente: String,
    val servicio: String,
    val profesional: String,
    val hora: String,
    val estado: EstadoReserva
)

enum class EstadoReserva {
    CONFIRMADA, PENDIENTE, CANCELADA, COMPLETADA
}

@Composable
fun ReservasScreen() {
    var visible by remember { mutableStateOf(false) }
    var selectedFilter by remember { mutableStateOf("Todas") }

    LaunchedEffect(Unit) {
        visible = true
    }

    val filters = listOf("Todas", "Hoy", "Mañana", "Esta semana")
    val reservas = remember {
        listOf(
            Reserva("1", "María González", "Corte de cabello", "Ana López", "09:00", EstadoReserva.CONFIRMADA),
            Reserva("2", "Carlos Rodríguez", "Tinte", "Pedro Martínez", "10:30", EstadoReserva.PENDIENTE),
            Reserva("3", "Ana Martínez", "Peinado", "Ana López", "11:00", EstadoReserva.CONFIRMADA),
            Reserva("4", "Luis Pérez", "Corte y barba", "Juan García", "14:00", EstadoReserva.PENDIENTE),
            Reserva("5", "Sofia López", "Manicure", "Laura Torres", "15:30", EstadoReserva.CONFIRMADA)
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        // Filters
        AnimatedVisibility(
            visible = visible,
            enter = fadeIn(tween(400)) + slideInVertically(
                initialOffsetY = { -30 },
                animationSpec = tween(500)
            )
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                filters.forEach { filter ->
                    FilterChip(
                        selected = selectedFilter == filter,
                        onClick = { selectedFilter = filter },
                        label = { Text(filter) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = SageGreen,
                            selectedLabelColor = CreamWhite
                        )
                    )
                }
            }
        }

        // Reservas List
        AnimatedVisibility(
            visible = visible,
            enter = fadeIn(tween(400, delayMillis = 100)) + slideInVertically(
                initialOffsetY = { 30 },
                animationSpec = tween(500, delayMillis = 100)
            )
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = CardWhite)
            ) {
                LazyColumn(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(reservas) { reserva ->
                        ReservaCard(reserva = reserva)
                    }
                }
            }
        }
    }
}

@Composable
private fun ReservaCard(reserva: Reserva) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = ContentBg)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.weight(1f)
            ) {
                // Time Badge
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(SageGreen.copy(alpha = 0.1f))
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = reserva.hora,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = SageGreen
                    )
                }

                Column {
                    Text(
                        text = reserva.cliente,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = DeepCharcoal
                    )
                    Text(
                        text = reserva.servicio,
                        fontSize = 13.sp,
                        color = WarmGray
                    )
                    Text(
                        text = "Con ${reserva.profesional}",
                        fontSize = 11.sp,
                        color = WarmGray
                    )
                }
            }

            // Status Badge
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(
                        when (reserva.estado) {
                            EstadoReserva.CONFIRMADA -> Color(0xFF6BBF59).copy(alpha = 0.1f)
                            EstadoReserva.PENDIENTE -> AccentAmber.copy(alpha = 0.1f)
                            EstadoReserva.CANCELADA -> Color(0xFFE85D75).copy(alpha = 0.1f)
                            EstadoReserva.COMPLETADA -> SageGreen.copy(alpha = 0.1f)
                        }
                    )
                    .padding(horizontal = 12.dp, vertical = 6.dp)
            ) {
                Text(
                    text = reserva.estado.name.lowercase().replaceFirstChar { it.uppercase() },
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = when (reserva.estado) {
                        EstadoReserva.CONFIRMADA -> Color(0xFF6BBF59)
                        EstadoReserva.PENDIENTE -> AccentAmber
                        EstadoReserva.CANCELADA -> Color(0xFFE85D75)
                        EstadoReserva.COMPLETADA -> SageGreen
                    }
                )
            }
        }
    }
}