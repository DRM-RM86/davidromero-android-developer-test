package com.hugo.test.storage

import android.content.Context

class ParkingStorageImp(private val context: Context):ParkingStorage {
    override val database: ParkingDataBase
        get() = ParkingDataBase(context)


}