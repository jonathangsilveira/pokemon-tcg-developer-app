package br.edu.jonathangs.pokmontcgdeveloper.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import br.edu.jonathangs.pokmontcgdeveloper.data.local.dao.SetDao
import br.edu.jonathangs.pokmontcgdeveloper.data.local.model.Set

@Database(entities = [Set::class], version = 1, exportSchema = false)
abstract class PokemonDatabase: RoomDatabase() {

    abstract fun setDao(): SetDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: PokemonDatabase? = null

        fun getDatabase(context: Context): PokemonDatabase {
            return INSTANCE ?: createDatabase(context)
        }

        private fun createDatabase(context: Context): PokemonDatabase {
            synchronized(this) {
                return Room.databaseBuilder(
                    context.applicationContext,
                    PokemonDatabase::class.java,
                    "pokemon"
                ).build()
            }
        }
    }

}