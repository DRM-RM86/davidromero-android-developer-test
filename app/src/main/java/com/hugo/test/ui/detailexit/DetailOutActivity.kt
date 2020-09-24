package com.hugo.test.ui.detailexit

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import com.hugo.test.R
import com.hugo.test.bases.BaseActivity
import com.hugo.test.databinding.ActivityDetailOutBinding
import com.hugo.test.model.CarModel
import com.hugo.test.model.RegisterExitModel
import com.hugo.test.storage.ParkingStorage
import com.hugo.test.ui.detailexit.domain.RegisterExitRepository
import com.hugo.test.ui.detailexit.repository.RegisterExitRepositoryImp
import com.hugo.test.ui.detailexit.viewModel.RegisterExitViewModel
import com.hugo.test.ui.entercar.domain.CarRepository
import com.hugo.test.ui.entercar.repository.CarRepositoryImp
import com.hugo.test.ui.entercar.viewModel.CarViewModel
import com.hugo.test.ui.residents.domain.ResidentRepository
import com.hugo.test.ui.residents.reposotory.ResidentRepositoryImp
import com.hugo.test.ui.residents.viewModel.ResidentViewModel
import com.hugo.test.utils.DateUtils
import com.hugo.test.utils.customviews.SimpleAlertDialog
import com.hugo.test.utils.customviews.progressdialog.ProgressDialog
import com.hugo.test.utils.getParkingStorage
import com.hugo.test.utils.getViewModel
import java.util.*

class DetailOutActivity : BaseActivity() {
    lateinit var car:CarModel
    lateinit var binding: ActivityDetailOutBinding

    private val parkingStorage: ParkingStorage by lazy {
        getParkingStorage()
    }
    private val residentRepository: ResidentRepository by lazy {
        ResidentRepositoryImp(parkingStorage)
    }
    private val residentViewModel: ResidentViewModel by lazy {
        getViewModel {
            ResidentViewModel(residentRepository)
        }
    }

    private val carRepository: CarRepository by lazy {
        CarRepositoryImp(parkingStorage)
    }
    private val carViewModel: CarViewModel by lazy {
        getViewModel {
            CarViewModel(carRepository)
        }
    }


    private val registerRepository: RegisterExitRepository by lazy {
        RegisterExitRepositoryImp(parkingStorage)
    }
    private val registerExitViewModel:RegisterExitViewModel by lazy {
        getViewModel {
            RegisterExitViewModel(registerRepository)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_detail_out)
         getExtras()




    }

    private fun getExtras(){
        intent.extras?.let {
            car = it.getParcelable("car")!!
            residentViewModel.getCarByPlaca(car.placa)
            binding.txPlaca.text=car.placa
            binding.txModelo.text=car.model.toString()
            binding.txObservs.text=car.observations
            binding.txDate.text= DateUtils.getFormatDate(Date(car.time))

            binding.txStance.text="${DateUtils.twoDatesBetweenTime(DateUtils.getFormatDate(Date(car.time)),1)}"
            binding.txDetailTime.text="${DateUtils.twoDatesBetweenTime(DateUtils.getFormatDate(Date(car.time)),0)}"


            if(car.isOffice==1){
                binding.txOffice.visibility= View.VISIBLE
                binding.txToPay.text="Sin cobrar a vehiculo oficial"
            }

            ProgressDialog.showProgressDialog(supportFragmentManager)
            setObservers()

            binding.btRegisterExit.setOnClickListener {
                ProgressDialog.showProgressDialog(supportFragmentManager)
                registerExitViewModel.registerExitCar(car.placa,20)
            }
        }
    }


    private fun setObservers(){
            setViewModel(residentViewModel)

        residentViewModel.getCarByPlaca().observe(this, androidx.lifecycle.Observer {
            ProgressDialog.hideProgressDialog()
            if(it!=null){
                if(it.placa.isNotEmpty()){
                    binding.txToPay.text="Es un residente, se le cobra al finalizar el mes"
                }
            }
        })

        registerExitViewModel.getAddRegisterResult().observe(this, androidx.lifecycle.Observer {
            carViewModel.deleteCar(car)
            ProgressDialog.hideProgressDialog()
            SimpleAlertDialog.Builder(this)
                .message("Se ha registrado la salida")
                .primaryButton("OK")
                .typeDialog(SimpleAlertDialog.SUCCESS)
                .create().apply {
                    setOnclickListener(onPrimary = {
                        dismiss()
                        onBackPressed()
                    }
                    )
                }.show(supportFragmentManager, null)

        })

        }

}