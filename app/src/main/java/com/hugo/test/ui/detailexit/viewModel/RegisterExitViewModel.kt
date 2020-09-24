package com.hugo.test.ui.detailexit.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hugo.test.model.CarModel
import com.hugo.test.model.RegisterExitModel
import com.hugo.test.ui.detailexit.domain.RegisterExitRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegisterExitViewModel(private  val registerExitRepository:RegisterExitRepository):ViewModel() {

    private val error = MutableLiveData<String>()
    fun getErrorResult(): LiveData<String> {
        return error
    }


    private val addRegisterResult = MutableLiveData<Boolean>()
    fun registerExitCar( placa:String , minutes:Long){

        if(placa.isEmpty()){
            error.value="Es necesario que ingrese la placa"
            return
        }

        CoroutineScope(Dispatchers.Main).launch {

            registerExitRepository.addDetailExit(RegisterExitModel(placa = placa,minutes = minutes))
            addRegisterResult.value=true
        }
    }
    fun getAddRegisterResult(): LiveData<Boolean> {
        return addRegisterResult
    }





    private val listRegisters = MutableLiveData<List<RegisterExitModel>>()
    private fun setListCars(listaRegisters: List<RegisterExitModel>){
        listRegisters.value = listaRegisters
    }
    fun getListAllRegisters():LiveData<List<RegisterExitModel>>{
        return listRegisters
    }
    fun getAllRegisters(){
        CoroutineScope(Dispatchers.Main).launch {
            withContext(Dispatchers.Main){
                    setListCars(registerExitRepository.getAllRegisters())
                }
        }
    }




    private val listRegistersByPlaca = MutableLiveData<List<RegisterExitModel>>()
    private fun setListCarsByPlaca(listaRegisters: List<RegisterExitModel>){
        listRegistersByPlaca.value = listaRegisters
    }
    fun getListAllRegistersByPlaca():LiveData<List<RegisterExitModel>>{
        return listRegistersByPlaca
    }
    fun getAllRegistersByPlaca(placa:String){
        CoroutineScope(Dispatchers.Main).launch {
            withContext(Dispatchers.Main){
                setListCarsByPlaca(registerExitRepository.getRegisterByPlaca(placa))
            }
        }
    }






}