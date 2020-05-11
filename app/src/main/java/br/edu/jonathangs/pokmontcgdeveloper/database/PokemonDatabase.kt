package br.edu.jonathangs.pokmontcgdeveloper.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import br.edu.jonathangs.pokmontcgdeveloper.database.dao.CardsDao
import br.edu.jonathangs.pokmontcgdeveloper.database.dao.SetsDao
import br.edu.jonathangs.pokmontcgdeveloper.database.model.Card
import br.edu.jonathangs.pokmontcgdeveloper.database.model.Set

@Database(entities = [Card::class, Set::class], version = 1, exportSchema = false)
abstract class PokemonDatabase: RoomDatabase() {

    abstract fun cardsDao(): CardsDao

    abstract fun setsDao(): SetsDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: PokemonDatabase? = null

        fun getDatabase(context: Context): PokemonDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PokemonDatabase::class.java,
                    "pokemon"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }

}