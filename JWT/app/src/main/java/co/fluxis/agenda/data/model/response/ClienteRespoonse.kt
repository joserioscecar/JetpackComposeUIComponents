package co.fluxis.agenda.data.model.response

import kotlinx.serialization.Serializable

@Serializable
data class ClienteRespoonse(
    val id: Int? = null,
    val nombres: String? = null,
    val apellidos: String? = null,
    val fechaNacimiento: String? = null,
    val celular: String? = null,
    val correo: String? = null,
    val documento: String? = null
)
