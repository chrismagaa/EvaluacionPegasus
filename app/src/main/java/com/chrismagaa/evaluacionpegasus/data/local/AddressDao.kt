package com.chrismagaa.evaluacionpegasus.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.Query
import com.chrismagaa.evaluacionpegasus.data.local.Address

@Dao
interface AddressDao {
    @Query("SELECT * FROM address ORDER by distanceValue ASC")
    fun getAll(): LiveData<List<Address>>

    @Insert(onConflict = IGNORE)
    fun insert(adress: Address)

    @Delete
    fun delete(address: Address)


}