package br.edu.jonathangs.pokmontcgdeveloper

import android.app.Application
import br.edu.jonathangs.pokmontcgdeveloper.database.PokemonDatabase
import br.edu.jonathangs.pokmontcgdeveloper.domain.CardRepository
import br.edu.jonathangs.pokmontcgdeveloper.domain.SetRepository
import br.edu.jonathangs.pokmontcgdeveloper.network.Endpoint
import br.edu.jonathangs.pokmontcgdeveloper.network.WebService
import br.edu.jonathangs.pokmontcgdeveloper.ui.cards.CardsViewModel
import br.edu.jonathangs.pokmontcgdeveloper.ui.set.SetViewModel
import br.edu.jonathangs.pokmontcgdeveloper.ui.sets.SetsViewModel
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit

class PokemonApp : Application() {

    override fun onCreate() {
        super.onCreate()
        configKoin()
    }

    private fun configKoin() {
        startKoin {
            androidContext(this@PokemonApp)
            modules(listOf(databaseModule(), networkModule(), appModule()))
        }
    }


    private fun appModule() = module {
        single {
            SetRepository(endpoint = get(), dao = get())
        }
        single {
            CardRepository(endpoint = get(), dao = get())
        }
        viewModel {
            SetsViewModel(
                application = this@PokemonApp,
                repo = get()
            )
        }
        viewModel { (setCode: String) ->
            SetViewModel(
                application = this@PokemonApp,
                repo = get(),
                setCode = setCode
            )
        }
        viewModel {
            CardsViewModel(
                application = this@PokemonApp,
                repo = get()
            )
        }
    }

    private fun networkModule() = module {
        val client = OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .build()
        val gson = GsonBuilder().setDateFormat("MM/dd/yyyy").create()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.pokemontcg.io/v1/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        val webservice = retrofit.create<WebService>()
        single { Endpoint(webservice) }
    }

    private fun databaseModule() = module {
        val db = PokemonDatabase.getDatabase(this@PokemonApp)
        single { db }
        single { db.cardsDao() }
        single { db.setsDao() }
    }

}