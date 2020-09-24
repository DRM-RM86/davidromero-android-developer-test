package com.hugo.test.ui.entercar.domain

import com.hugo.test.model.CarModel

interface CarRepository {

    suspend fun addCarToParking(car: CarModel)

    suspend fun deleteCar(car: CarModel)


    suspend fun getAllCars():List<CarModel>
}