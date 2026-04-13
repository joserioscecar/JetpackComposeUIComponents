package co.edu.cecar.nav.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable



@Serializable
object HomeRoute : NavKey

@Serializable
data class DetailRoute(val itemId: Int, val title: String) : NavKey

@Serializable
object ProfileRoute : NavKey