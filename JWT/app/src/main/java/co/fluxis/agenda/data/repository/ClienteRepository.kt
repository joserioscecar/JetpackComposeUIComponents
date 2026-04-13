package co.fluxis.agenda.data.repository

import co.fluxis.agenda.data.model.response.ClienteRespoonse
import co.fluxis.agenda.data.network.HttpClientProvider
import co.fluxis.agenda.data.local.SessionManager
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpHeaders
import io.ktor.http.isSuccess
import co.fluxis.agenda.config.AppConfig

suspend fun obtenerClientes(): List<ClienteRespoonse> {
    val token = SessionManager.token
        ?: throw IllegalStateException("Token no disponible")

    val response: HttpResponse = HttpClientProvider.client.get("${AppConfig.API_BASE_URL}/clientes") {
        header(HttpHeaders.Authorization, "Bearer $token")
    }

    if (response.status.isSuccess()) {
        return response.body()
    } else {
        val errorText = response.bodyAsText()
        throw Exception("Error del servidor (${response.status.value}): $errorText")
    }
}
