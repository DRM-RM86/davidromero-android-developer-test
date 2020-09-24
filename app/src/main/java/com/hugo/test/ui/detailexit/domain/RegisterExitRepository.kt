package com.hugo.test.ui.detailexit.domain

import com.hugo.test.model.RegisterExitModel

interface RegisterExitRepository {

    suspend fun addDetailExit(car: RegisterExitModel)

    suspend fun deleteRegister(car: RegisterExitModel)

    suspend fun deleteAllRegister()

    suspend fun getRegisterByPlaca(placa:String):List<RegisterExitModel>

    suspend fun getAllRegisters():List<RegisterExitModel>
}