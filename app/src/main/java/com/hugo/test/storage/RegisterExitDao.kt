package com.hugo.test.storage

import androidx.room.*
import com.hugo.test.model.RegisterExitModel

@Dao
interface RegisterExitDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRegisterExit(resident: RegisterExitModel)



    @Query("Select * from exit_cars where placa like :placa")
    fun getRegisterByPlaca(placa : String): List<RegisterExitModel>


    @Query("Delete from exit_cars")
    fun deleteAllRegister()

    @Delete
    fun deleteRegister(resident: RegisterExitModel)


}