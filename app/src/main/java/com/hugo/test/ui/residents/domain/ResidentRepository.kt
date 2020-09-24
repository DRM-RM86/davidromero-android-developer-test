package com.hugo.test.ui.residents.domain

import com.hugo.test.model.CarModel
import com.hugo.test.model.ResidentModel

interface ResidentRepository {

    suspend fun addResident(resident:ResidentModel)
    suspend fun deleteResident(resident: ResidentModel)
    suspend fun deleteAllResidents()
    suspend fun getAllResidents():List<ResidentModel>
    suspend fun getCarByPlaca(placa: String): ResidentModel

}