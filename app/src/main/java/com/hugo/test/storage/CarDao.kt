package com.hugo.test.storage

import androidx.room.*
import com.hugo.test.model.CarModel

@Dao
interface CarDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCar(car: CarModel)


    @Query("Select * from cars")
    fun getAllCars():List<CarModel>

    @Query("Select * from cars where placa like :placa")
    fun getCarByPlaca(placa : String): CarModel




    @Query("Delete from cars")
    fun deleteAllCars()

    @Delete
    fun deleteCar(car: CarModel)


}