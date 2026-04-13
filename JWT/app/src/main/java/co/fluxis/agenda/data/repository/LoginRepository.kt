package co.fluxis.agenda.data.repository

import co.fluxis.agenda.data.model.request.LoginRequest
import co.fluxis.agenda.data.model.response.LoginResponse
import co.fluxis.agenda.data.network.HttpClientProvider
import co.fluxis.agenda.data.local.SessionManager
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import co.fluxis.agenda.config.AppConfig

suspend fun login(usuario: String, clave: String): Boolean {
    return try {
        val response: LoginResponse = HttpClientProvider.client.post(
            "${AppConfig.API_BASE_URL}/sesion"
        ) {
            contentType(ContentType.Application.Json)
            setBody(LoginRequest(usuario, clave))
        }.body()

        // Guardar token en memoria
        SessionManager.token = response.accessToken

        true
    } catch (e: Exception) {
        e.printStackTrace()
        false
    }
}
