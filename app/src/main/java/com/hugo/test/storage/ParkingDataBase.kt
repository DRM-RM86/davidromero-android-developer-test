package com.hugo.test.storage

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.hugo.test.model.CarModel
import com.hugo.test.model.RegisterExitModel
import com.hugo.test.model.ResidentModel


@Database(entities = [CarModel::class,ResidentModel::class,RegisterExitModel::class],
version = 1)
abstract class ParkingDataBase:RoomDatabase(){
    abstract fun carDao():CarDao
    abstract fun residentDao():ResidentDao
    abstract fun registerExitDao():RegisterExitDao



    companion object{
        private var INSTANCE:ParkingDataBase? = null
        const val DATABASE_ERROR =-1L


        /**
         * Create a instance of [ParkingDataBase] using [context] only when [INSTANCE] is null.
         * @return [ParkingDataBase].
         */
        operator fun invoke(context: Context):ParkingDataBase{
            return INSTANCE ?: synchronized(ParkingDataBase::class) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }
        }

        private fun buildDatabase(context: Context): ParkingDataBase {
            return Room.databaseBuilder(
                context.applicationContext,
                ParkingDataBase::class.java, "parking_database.db"
            ).fallbackToDestructiveMigration().build()
        }
    }

    /**
     * Destroy instance of [ParkingDataBase].
     */
    fun destroyDataBase() {
        INSTANCE = null
    }


}