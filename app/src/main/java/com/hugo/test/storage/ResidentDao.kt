package com.hugo.test.storage

import androidx.room.*
import com.hugo.test.model.ResidentModel

@Dao
interface ResidentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertResident(resident: ResidentModel)


    @Query("Select * from residents")
    fun getAllResidents():List<ResidentModel>

    @Query("Select * from residents where placa like :placa")
    fun getResidentByPlaca(placa : String): ResidentModel


    @Query("Delete from cars")
    fun deleteAllResidents()

    @Delete
    fun deleteResidentById(resident: ResidentModel)


}