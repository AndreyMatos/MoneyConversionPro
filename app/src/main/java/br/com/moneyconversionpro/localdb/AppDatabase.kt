package br.com.moneyconversionpro.localdb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import br.com.moneyconversionpro.dao.ConversionDAO
import br.com.moneyconversionpro.model.Conversion

@Database(entities = [Conversion::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun conversionDao(): ConversionDAO

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java, "moneyconversion.db"
            )
                .build()
    }
}