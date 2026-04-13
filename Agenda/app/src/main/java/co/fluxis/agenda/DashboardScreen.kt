package co.fluxis.agenda

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

// Color Palette - Professional Sage & Charcoal Theme
private val DeepCharcoal = Color(0xFF1A1D23)
private val SoftCharcoal = Color(0xFF2C3038)
private val SageGreen = Color(0xFF7BA68A)
private val LightSage = Color(0xFF9FC4A8)
private val CreamWhite = Color(0xFFF5F5F0)
private val WarmGray = Color(0xFFB8B8B0)
private val AccentAmber = Color(0xFFE8A542)
private val SidebarBg = Color(0xFF252931)
private val ContentBg = Color(0xFFF8F8F5)
private val CardWhite = Color(0xFFFFFFFF)

// Menu Item Data Class
data class MenuItem(
    val id: String,
    val title: String,
    val icon: ImageVector,
    val badge: Int? = null,
    val isExpanded: Boolean = false,
    val subItems: List<String> = emptyList()
)

@Composable
fun DashboardScreen(
    modifier: Modifier = Modifier,
    onMenuItemClick: (String) -> Unit = {}
) {
    var selectedMenuItem by remember { mutableStateOf("horarios") }
    var isCollapsed by remember { mutableStateOf(false) }

    val sidebarWidth by animateDpAsState(
        targetValue = if (isCollapsed) 80.dp else 280.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "sidebar_width"
    )

    val menuItems = remember {
        listOf(
            MenuItem(id = "horarios", title = "Horarios", icon = Icons.Filled.Schedule),
            MenuItem(id = "clientes", title = "Clientes", icon = Icons.Filled.People, badge = 12),
            MenuItem(id = "reservas", title = "Reservas", icon = Icons.Filled.EventNote, badge = 5),
            MenuItem(id = "cierres", title = "Cierres", icon = Icons.Filled.Lock),
            MenuItem(id = "comisiones", title = "Comisiones", icon = Icons.Filled.Payments),
            MenuItem(id = "servicios", title = "Servicios", icon = Icons.Filled.MiscellaneousServices),
            MenuItem(id = "profesionales", title = "Profesionales", icon = Icons.Filled.Badge, badge = 8)
        )
    }

    BoxWithConstraints(
        modifier = modifier
            .fillMaxSize()
            .background(ContentBg)
    ) {
        val totalWidth = maxWidth
        val stableContentWidth = totalWidth - 80.dp

        Row(modifier = Modifier.fillMaxSize()) {
            // Sidebar
            Sidebar(
                menuItems = menuItems,
                selectedMenuItem = selectedMenuItem,
                isCollapsed = isCollapsed,
                currentWidth = sidebarWidth,
                onMenuItemClick = { itemId ->
                    selectedMenuItem = itemId
                    onMenuItemClick(itemId)
                },
                onCollapseToggle = { isCollapsed = !isCollapsed }
            )

            // Main Content
            MainContent(
                selectedMenuItem = selectedMenuItem,
                modifier = Modifier
                    .requiredWidth(stableContentWidth)
                    .fillMaxHeight()
            )
        }
    }
}

@Composable
private fun Sidebar(
    menuItems: List<MenuItem>,
    selectedMenuItem: String,
    isCollapsed: Boolean,
    currentWidth: Dp,
    onMenuItemClick: (String) -> Unit,
    onCollapseToggle: () -> Unit
) {
    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        visible = true
    }

    Column(
        modifier = Modifier
            .width(currentWidth)
            .fillMaxHeight()
            .background(SidebarBg)
            .padding(vertical = 24.dp)
    ) {
        // Header with Logo
        AnimatedVisibility(
            visible = visible,
            enter = fadeIn(tween(400)) + slideInHorizontally(
                initialOffsetX = { -40 },
                animationSpec = tween(600, easing = FastOutSlowInEasing)
            )
        ) {
            SidebarHeader(isCollapsed = isCollapsed)
        }

        Spacer(modifier = Modifier.height(40.dp))

        // Menu Items
        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(menuItems) { item ->
                AnimatedVisibility(
                    visible = visible,
                    enter = fadeIn(
                        animationSpec = tween(
                            400,
                            delayMillis = 100 + (menuItems.indexOf(item) * 50)
                        )
                    ) + slideInHorizontally(
                        initialOffsetX = { -40 },
                        animationSpec = tween(
                            600,
                            delayMillis = 100 + (menuItems.indexOf(item) * 50),
                            easing = FastOutSlowInEasing
                        )
                    )
                ) {
                    SidebarMenuItem(
                        item = item,
                        isSelected = selectedMenuItem == item.id,
                        isCollapsed = isCollapsed,
                        onClick = { onMenuItemClick(item.id) }
                    )
                }
            }
        }

        // Collapse Toggle Button
        AnimatedVisibility(
            visible = visible,
            enter = fadeIn(tween(400, delayMillis = 600))
        ) {
            CollapseButton(
                isCollapsed = isCollapsed,
                onClick = onCollapseToggle
            )
        }
    }
}

@Composable
private fun SidebarHeader(isCollapsed: Boolean) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        horizontalAlignment = if (isCollapsed) Alignment.CenterHorizontally else Alignment.Start
    ) {
        if (isCollapsed) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(
                        Brush.linearGradient(
                            colors = listOf(SageGreen, LightSage)
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "A",
                    color = CreamWhite,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        } else {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(
                            Brush.linearGradient(
                                colors = listOf(SageGreen, LightSage)
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "A",
                        color = CreamWhite,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Column {
                    Text(
                        text = "AgendaPro",
                        color = CreamWhite,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = (-0.5).sp
                    )
                    Text(
                        text = "Sistema de Gestión",
                        color = WarmGray,
                        fontSize = 11.sp,
                        letterSpacing = 0.3.sp
                    )
                }
            }
        }
    }
}

@Composable
private fun SidebarMenuItem(
    item: MenuItem,
    isSelected: Boolean,
    isCollapsed: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor by animateColorAsState(
        targetValue = if (isSelected) SageGreen.copy(alpha = 0.15f) else Color.Transparent,
        animationSpec = tween(300),
        label = "bg_color"
    )

    val scale by animateFloatAsState(
        targetValue = if (isSelected) 1f else 0.98f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "scale"
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .scale(scale)
            .padding(horizontal = 12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(backgroundColor)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) { onClick() }
                .padding(
                    horizontal = if (isCollapsed) 0.dp else 16.dp,
                    vertical = 14.dp
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = if (isCollapsed) Arrangement.Center else Arrangement.Start
        ) {
            Icon(
                imageVector = item.icon,
                contentDescription = item.title,
                tint = if (isSelected) SageGreen else WarmGray,
                modifier = Modifier.size(24.dp)
            )

            AnimatedVisibility(
                visible = !isCollapsed,
                enter = fadeIn(tween(200)) + expandHorizontally(),
                exit = fadeOut(tween(200)) + shrinkHorizontally()
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = item.title,
                        color = if (isSelected) CreamWhite else WarmGray,
                        fontSize = 15.sp,
                        fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Medium,
                        modifier = Modifier.padding(start = 16.dp)
                    )

                    item.badge?.let { count ->
                        Badge(count = count)
                    }
                }
            }
        }

        AnimatedVisibility(
            visible = isSelected,
            enter = fadeIn() + expandHorizontally(),
            exit = fadeOut() + shrinkHorizontally()
        ) {
            Box(
                modifier = Modifier
                    .width(4.dp)
                    .height(40.dp)
                    .clip(RoundedCornerShape(topEnd = 4.dp, bottomEnd = 4.dp))
                    .background(SageGreen)
                    .align(Alignment.CenterStart)
            )
        }
    }
}

@Composable
private fun Badge(count: Int) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(AccentAmber)
            .padding(horizontal = 8.dp, vertical = 4.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = count.toString(),
            color = DeepCharcoal,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun CollapseButton(
    isCollapsed: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(SoftCharcoal)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { onClick() }
            .padding(vertical = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = if (isCollapsed) Icons.Filled.ChevronRight else Icons.Filled.ChevronLeft,
            contentDescription = if (isCollapsed) "Expandir" else "Contraer",
            tint = WarmGray,
            modifier = Modifier.size(20.dp)
        )
    }
}

@Composable
private fun MainContent(
    selectedMenuItem: String,
    modifier: Modifier = Modifier
) {
    var visible by remember { mutableStateOf(false) }

    LaunchedEffect(selectedMenuItem) {
        visible = false
        delay(100)
        visible = true
    }

    Column(
        modifier = modifier.background(ContentBg)
    ) {
        TopBar(selectedMenuItem = selectedMenuItem)

        Box(modifier = Modifier.fillMaxSize()) {
            AnimatedContent(
                targetState = selectedMenuItem,
                transitionSpec = {
                    fadeIn(tween(400)) + slideInVertically(
                        initialOffsetY = { 30 },
                        animationSpec = tween(400)
                    ) togetherWith fadeOut(tween(200))
                },
                label = "content_transition"
            ) { menuItem ->
                when (menuItem) {
                    "horarios" -> HorariosScreen()
                    "clientes" -> ClientesScreen()
                    "reservas" -> ReservasScreen()
                    else -> DefaultPlaceholderContent(menuItem, visible)
                }
            }
        }
    }
}

@Composable
private fun DefaultPlaceholderContent(title: String, visible: Boolean) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        AnimatedVisibility(
            visible = visible,
            enter = fadeIn(tween(400, delayMillis = 100)) + slideInVertically(
                initialOffsetY = { 30 },
                animationSpec = tween(500, delayMillis = 100)
            )
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                StatsCard("Total Hoy", "24", "+12% vs ayer", Icons.Filled.TrendingUp, SageGreen, Modifier.weight(1f))
                StatsCard("Pendientes", "5", "Para confirmar", Icons.Filled.Schedule, AccentAmber, Modifier.weight(1f))
                StatsCard("Completadas", "19", "Esta semana", Icons.Filled.CheckCircle, Color(0xFF6BBF59), Modifier.weight(1f))
            }
        }

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
                colors = CardDefaults.cardColors(containerColor = CardWhite),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(24.dp)) {
                    Text("Sección: ${title.replaceFirstChar { it.uppercase() }}", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = DeepCharcoal)
                    Spacer(modifier = Modifier.height(20.dp))
                    Text("Contenido de $title en desarrollo...", color = WarmGray)
                }
            }
        }
    }
}

@Composable
private fun TopBar(selectedMenuItem: String) {
    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        visible = true
    }

    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = CardWhite,
        shadowElevation = 2.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp, vertical = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            AnimatedVisibility(
                visible = visible,
                enter = fadeIn(tween(500)) + slideInHorizontally(
                    initialOffsetX = { -40 },
                    animationSpec = tween(600)
                )
            ) {
                Column {
                    Text(
                        text = selectedMenuItem.replaceFirstChar { it.uppercase() },
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = DeepCharcoal,
                        letterSpacing = (-0.5).sp
                    )
                    Text(
                        text = "Gestiona tu negocio de forma eficiente",
                        fontSize = 14.sp,
                        color = WarmGray,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { },
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .background(ContentBg)
                ) {
                    Icon(imageVector = Icons.Filled.Search, contentDescription = "Buscar", tint = DeepCharcoal)
                }

                Box {
                    IconButton(
                        onClick = { },
                        modifier = Modifier
                            .size(44.dp)
                            .clip(CircleShape)
                            .background(ContentBg)
                    ) {
                        Icon(imageVector = Icons.Filled.Notifications, contentDescription = "Notificaciones", tint = DeepCharcoal)
                    }
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .clip(CircleShape)
                            .background(AccentAmber)
                            .align(Alignment.TopEnd)
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(ContentBg)
                        .clickable { }
                        .padding(horizontal = 12.dp, vertical = 8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(Brush.linearGradient(colors = listOf(SageGreen, LightSage))),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "JD", color = CreamWhite, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                    }
                    Column {
                        Text(text = "John Doe", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = DeepCharcoal)
                        Text(text = "Administrador", fontSize = 11.sp, color = WarmGray)
                    }
                    Icon(imageVector = Icons.Filled.KeyboardArrowDown, contentDescription = "Menu", tint = WarmGray, modifier = Modifier.size(18.dp))
                }
            }
        }
    }
}

@Composable
private fun StatsCard(title: String, value: String, subtitle: String, icon: ImageVector, color: Color, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = CardWhite),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text(text = title, fontSize = 14.sp, color = WarmGray, fontWeight = FontWeight.Medium)
                Box(modifier = Modifier.size(40.dp).clip(CircleShape).background(color.copy(alpha = 0.1f)), contentAlignment = Alignment.Center) {
                    Icon(imageVector = icon, contentDescription = null, tint = color, modifier = Modifier.size(22.dp))
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(text = value, fontSize = 32.sp, fontWeight = FontWeight.Bold, color = DeepCharcoal, letterSpacing = (-1).sp)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = subtitle, fontSize = 12.sp, color = color, fontWeight = FontWeight.Medium)
        }
    }
}
