package br.com.moneyconversionpro.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.com.moneyconversionpro.model.Conversion

@Dao
interface ConversionDAO {
    @Query("SELECT * FROM conversion ORDER BY time DESC")
    fun getAll(): LiveData<List<Conversion>>

    @Insert
    fun insert(conversion: Conversion)
}