package com.hugo.test.ui.residents.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hugo.test.model.CarModel
import com.hugo.test.model.ResidentModel
import com.hugo.test.ui.residents.domain.ResidentRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ResidentViewModel(private  val residentRepository:ResidentRepository):ViewModel() {

    private val error = MutableLiveData<String>()
    fun getErrorResult(): LiveData<String> {
        return error
    }

    private val addResidentResult = MutableLiveData<Boolean>()
    fun addResident( placa:String , model:String,owner:String){

        if(placa.isEmpty()){
            error.value="Es necesario que ingrese la placa"
            return
        }
        if(model.isEmpty()){
            error.value="Es necesario que ingrese el modelo del vehiculo"
            return
        }

        if(owner.isEmpty()){
            error.value="Es necesario que ingrese el nombre del propietario"
            return
        }
        CoroutineScope(Dispatchers.Main).launch {

            residentRepository.addResident(ResidentModel(placa,model.toInt(),owner))
            addResidentResult.value=true
        }
    }
    fun getAddResidentResult(): LiveData<Boolean> {
        return addResidentResult
    }


    private val listResidents = MutableLiveData<List<ResidentModel>>()
    private fun setListResidents(listaResidents: List<ResidentModel>){
        listResidents.value = listaResidents
    }
    fun getListResidents():LiveData<List<ResidentModel>>{
        return listResidents
    }

    fun getAllResidents(){
        CoroutineScope(Dispatchers.Main).launch {
            withContext(Dispatchers.Main){
                    setListResidents(residentRepository.getAllResidents())
                }
        }
    }



    private val itemCar = MutableLiveData<ResidentModel>()
    private fun setItemCar(item: ResidentModel){
        itemCar.value = item
    }
    fun getCarByPlaca():LiveData<ResidentModel>{
        return itemCar
    }
    fun getCarByPlaca(placa:String){
        CoroutineScope(Dispatchers.Main).launch {
            withContext(Dispatchers.Main){
                setItemCar(residentRepository.getCarByPlaca(placa))
            }
        }
    }
}