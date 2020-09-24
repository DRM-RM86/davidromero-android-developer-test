package com.hugo.test.ui.detailexit.repository

import android.util.Log
import com.hugo.test.model.RegisterExitModel
import com.hugo.test.storage.CarDao
import com.hugo.test.storage.ParkingStorage
import com.hugo.test.storage.RegisterExitDao
import com.hugo.test.ui.detailexit.domain.RegisterExitRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

class RegisterExitRepositoryImp (private val parkingStorage: ParkingStorage):
    RegisterExitRepository {


    private val registerExit:RegisterExitDao ? by lazy {
        parkingStorage.database.registerExitDao()
    }



    override suspend fun addDetailExit(car: RegisterExitModel) {
        GlobalScope.async {
            return@async registerExit?.insertRegisterExit(car)
        }.await()
    }

    override suspend fun deleteRegister(car: RegisterExitModel) {
        Log.e("Pending","Not yet implemented")
    }

    override suspend fun deleteAllRegister() {
        Log.e("Pending","Not yet implemented")
    }

    override suspend fun getRegisterByPlaca(placa:String): List<RegisterExitModel> {
        return GlobalScope.async {
            return@async registerExit?.getRegisterByPlaca(placa) ?: emptyList()
        }.await()
    }

    override suspend fun getAllRegisters(): List<RegisterExitModel> {
        return GlobalScope.async {
            return@async registerExit?.getRegisterByPlaca("") ?: emptyList()
        }.await()
    }


}