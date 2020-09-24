package com.hugo.test.ui.entercar.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hugo.test.model.CarModel
import com.hugo.test.ui.entercar.domain.CarRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class CarViewModel(private  val carRepository:CarRepository):ViewModel() {

    private val error = MutableLiveData<String>()
    fun getErrorResult(): LiveData<String> {
        return error
    }





    private val addCarResult = MutableLiveData<Boolean>()
    fun addCar( placa:String , model:String,observations:String="", isOffice:Int){

        if(placa.isEmpty()){
            error.value="Es necesario que ingrese la placa"
            return
        }
        if(model.isEmpty()){
            error.value="Es necesario que ingrese el modelo del vehiculo"
            return
        }

        if(model.toInt()>2022){
            error.value="Es necesario que ingrese un modelo valido para el vehiculo"
            return
        }
        CoroutineScope(Dispatchers.Main).launch {

            carRepository.addCarToParking(CarModel(placa,model.toInt(),observations, Date().time, isOffice))
            addCarResult.value=true
        }
    }
    fun getAddCarResult(): LiveData<Boolean> {
        return addCarResult
    }


    private val listCars = MutableLiveData<List<CarModel>>()
    private fun setListCars(listaMovies: List<CarModel>){
        listCars.value = listaMovies
    }
    fun getListCars():LiveData<List<CarModel>>{
        return listCars
    }
    fun getAllCars(){
        CoroutineScope(Dispatchers.Main).launch {
            withContext(Dispatchers.Main){
                    setListCars(carRepository.getAllCars())
                }
        }
    }

    fun deleteCar(car:CarModel){
        CoroutineScope(Dispatchers.Main).launch {
               carRepository.deleteCar(car)
        }
    }



}