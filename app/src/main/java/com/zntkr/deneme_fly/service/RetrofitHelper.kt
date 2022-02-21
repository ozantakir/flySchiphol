import com.zntkr.deneme_fly.service.ApiClient
import com.zntkr.deneme_fly.service.DestinationApi
import com.zntkr.deneme_fly.service.QApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {
    private const val baseUrl = "https://api.schiphol.nl/public-flights/"

    fun getInstance(): Retrofit {
        return Retrofit.Builder().baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val service : QApi by lazy {
        getInstance().create(QApi::class.java)
    }

    val servideDest : DestinationApi by lazy {
        getInstance().create(DestinationApi::class.java)
    }
    val apiClient = ApiClient(service, servideDest)
}