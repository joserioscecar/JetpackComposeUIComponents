package co.fluxis.agenda

sealed class ScreenRoutes(val route: String) {
    object Horarios : ScreenRoutes("horarios")
    object Clientes : ScreenRoutes("clientes")
    object Reservas : ScreenRoutes("reservas")
    object Cierres : ScreenRoutes("cierres")
    object Comisiones : ScreenRoutes("comisiones")
    object Servicios : ScreenRoutes("servicios")
    object Profesionales : ScreenRoutes("profesionales")
    object Settings : ScreenRoutes("settings")
}
