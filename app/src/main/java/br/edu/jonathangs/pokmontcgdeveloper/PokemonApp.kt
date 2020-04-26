package br.edu.jonathangs.pokmontcgdeveloper

import android.app.Application
import br.edu.jonathangs.pokmontcgdeveloper.domain.Repository
import br.edu.jonathangs.pokmontcgdeveloper.network.Endpoint
import br.edu.jonathangs.pokmontcgdeveloper.network.WebService
import br.edu.jonathangs.pokmontcgdeveloper.ui.sets.SetsViewModel
import com.google.gson.GsonBuilder
import io.realm.Realm
import io.realm.RealmConfiguration
import okhttp3.OkHttpClient
import org.koin.android.ext.android.get
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.qualifier.Qualifier
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit

class PokemonApp : Application() {

    override fun onCreate() {
        super.onCreate()
        configRealm()
        configKoin()
    }

    private fun configKoin() {
        startKoin {
            androidContext(this@PokemonApp)
            modules(listOf(networkModule(), appModule()))
        }
    }

    private fun configRealm() {
        Realm.init(this);
        val config = RealmConfiguration.Builder()
            .name("pokemontcg.realm")
            .schemaVersion(1)
            .deleteRealmIfMigrationNeeded()
            //.initialData {  }
            .build()
        Realm.setDefaultConfiguration(config)
    }

    private fun appModule() = module {
        viewModel {
            SetsViewModel(
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
        single { retrofit }
        val webservice = retrofit.create<WebService>()
        val endpoint = Endpoint(webservice)
        single { Repository(endpoint) }
    }

}