package com.hugo.test.ui.entercar.repository

import android.util.Log
import com.hugo.test.model.CarModel
import com.hugo.test.storage.CarDao
import com.hugo.test.storage.ParkingStorage
import com.hugo.test.ui.entercar.domain.CarRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

class CarRepositoryImp (private val parkingStorage: ParkingStorage): CarRepository{


    private val carDao:CarDao ? by lazy {
        parkingStorage.database.carDao()
    }


    override suspend fun addCarToParking(car: CarModel) {
            GlobalScope.async {
                return@async carDao?.insertCar(car)
            }.await()
    }

    override suspend fun deleteCar(car: CarModel) {
        GlobalScope.async {
            return@async carDao?.deleteCar(car)
        }
    }



    override suspend fun getAllCars(): List<CarModel> {
        return GlobalScope.async {
            return@async carDao?.getAllCars() ?: emptyList()
        }.await()
    }




}