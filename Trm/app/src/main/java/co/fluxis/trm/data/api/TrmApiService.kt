package co.fluxis.trm.data.api

import co.fluxis.trm.BuildConfig
import co.fluxis.trm.data.dto.TrmResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
interface TrmApiService {
    @GET("/")
    suspend fun getTrmByDate(
        @Query("date") date: String
    ): TrmResponse
}

object RetrofitClient {

    val api: TrmApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BuildConfig.API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TrmApiService::class.java)
    }
}