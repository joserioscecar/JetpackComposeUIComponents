package co.fluxis.agenda



import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// ==================== AUTH VIEW MODEL ====================

sealed class AuthState {
    object Initial : AuthState()
    object Loading : AuthState()
    data class Success(val user: User) : AuthState()
    data class Error(val message: String) : AuthState()
}

data class User(
    val id: String,
    val name: String,
    val email: String,
    val role: String = "Administrador"
)

class AuthViewModel : ViewModel() {
    private val _authState = MutableStateFlow<AuthState>(AuthState.Initial)
    val authState: StateFlow<AuthState> = _authState.asStateFlow()

    private val _isAuthenticated = MutableStateFlow(false)
    val isAuthenticated: StateFlow<Boolean> = _isAuthenticated.asStateFlow()

    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser.asStateFlow()

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading

            try {
                // Simula una llamada a la API
                delay(1500)

                // Validación simple (reemplaza con tu lógica real)
                if (email.isNotEmpty() && password.isNotEmpty()) {
                    val user = User(
                        id = "1",
                        name = "John Doe",
                        email = email,
                        role = "Administrador"
                    )

                    _currentUser.value = user
                    _isAuthenticated.value = true
                    _authState.value = AuthState.Success(user)
                } else {
                    _authState.value = AuthState.Error("Credenciales inválidas")
                }
            } catch (e: Exception) {
                _authState.value = AuthState.Error(e.message ?: "Error desconocido")
            }
        }
    }

    fun logout() {
        _currentUser.value = null
        _isAuthenticated.value = false
        _authState.value = AuthState.Initial
    }

    fun resetAuthState() {
        _authState.value = AuthState.Initial
    }
}

// ==================== NAVIGATION ====================

@Composable
fun AppNavigationWithAuth(
    authViewModel: AuthViewModel = viewModel()
) {
    val navController = rememberNavController()
    val authState by authViewModel.authState.collectAsState()
    val isAuthenticated by authViewModel.isAuthenticated.collectAsState()

    // Observa el estado de autenticación
    LaunchedEffect(authState) {
        when (authState) {
            is AuthState.Success -> {
                navController.navigate("dashboard") {
                    popUpTo("login") { inclusive = true }
                }
            }
            else -> {}
        }
    }

    NavHost(
        navController = navController,
        startDestination = if (isAuthenticated) "dashboard" else "login"
    ) {
        composable("login") {
            LoginRoute(
                authViewModel = authViewModel,
                navController = navController
            )
        }

        composable("dashboard") {
            DashboardRoute(
                authViewModel = authViewModel,
                navController = navController
            )
        }
    }
}

@Composable
private fun LoginRoute(
    authViewModel: AuthViewModel,
    navController: NavHostController
) {
    val authState by authViewModel.authState.collectAsState()

    LaunchedEffect(Unit) {
        authViewModel.resetAuthState()
    }

    LoginScreen(
        onLoginClick = { email, password ->
            authViewModel.login(email, password)
        },
        onForgotPasswordClick = {
            // Navegar a recuperación de contraseña
        },
        onSignUpClick = {
            // Navegar a registro
        }
    )

    // Mostrar errores si hay
    LaunchedEffect(authState) {
        if (authState is AuthState.Error) {
            // Aquí puedes mostrar un SnackBar o Toast
            val error = (authState as AuthState.Error).message
            // Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
        }
    }
}

@Composable
private fun DashboardRoute(
    authViewModel: AuthViewModel,
    navController: NavHostController
) {
    val currentUser by authViewModel.currentUser.collectAsState()

    DashboardScreen(
        onMenuItemClick = { menuItemId ->
            // Manejar navegación entre secciones
        }
    )
}