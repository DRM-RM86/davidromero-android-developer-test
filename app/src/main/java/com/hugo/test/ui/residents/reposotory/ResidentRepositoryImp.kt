package com.hugo.test.ui.residents.reposotory

import android.util.Log
import com.hugo.test.model.CarModel
import com.hugo.test.model.ResidentModel
import com.hugo.test.storage.CarDao
import com.hugo.test.storage.ParkingStorage
import com.hugo.test.storage.ResidentDao
import com.hugo.test.ui.residents.domain.ResidentRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

class ResidentRepositoryImp(private val parkingStorage: ParkingStorage):ResidentRepository {

    private val residentDao: ResidentDao? by lazy {
        parkingStorage.database.residentDao()
    }

    override suspend fun addResident(resident: ResidentModel) {
        GlobalScope.async {
            return@async residentDao?.insertResident(resident)
        }.await()
    }

    override suspend fun deleteResident(resident: ResidentModel) {
        Log.e("Pending","Not yet implemented")
    }

    override suspend fun deleteAllResidents() {
        Log.e("Pending","Not yet implemented")
    }

    override suspend fun getAllResidents(): List<ResidentModel> {
        return GlobalScope.async {
            return@async residentDao?.getAllResidents() ?: emptyList()
        }.await()
    }

    override suspend fun getCarByPlaca(placa: String): ResidentModel {
        return GlobalScope.async {
            return@async residentDao?.getResidentByPlaca(placa)?:ResidentModel("",0,"")
        }.await()!!
    }


}