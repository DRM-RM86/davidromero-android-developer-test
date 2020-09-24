package com.hugo.test.ui.entercar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.hugo.test.R
import com.hugo.test.bases.BaseFragment
import com.hugo.test.databinding.FragmentEnterCarBinding
import com.hugo.test.storage.ParkingStorage
import com.hugo.test.ui.entercar.domain.CarRepository
import com.hugo.test.ui.entercar.repository.CarRepositoryImp
import com.hugo.test.ui.entercar.viewModel.CarViewModel
import com.hugo.test.utils.customviews.SimpleAlertDialog
import com.hugo.test.utils.customviews.progressdialog.ProgressDialog
import com.hugo.test.utils.getParkingStorage
import com.hugo.test.utils.getViewModel

class EnterCarFragment : BaseFragment() {

    lateinit var binding : FragmentEnterCarBinding

    private val parkingStorage: ParkingStorage by lazy {
        getParkingStorage()
    }
    private val carRepository:CarRepository by lazy {
        CarRepositoryImp(parkingStorage)
    }
    private val carViewModel:CarViewModel by lazy {
        getViewModel {
            CarViewModel(carRepository)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setObservers()


        binding.btAddCar.setOnClickListener {
            ProgressDialog.showProgressDialog(childFragmentManager)
            carViewModel.addCar(binding.edPlaca.text.toString(),binding.edModel.text.toString(),binding.edObservations.text.toString(), if(binding.swOffice.isChecked){ 1 }else{0})
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_enter_car, container, false)
        return binding.root


    }


    private fun setObservers(){
        setViewModel(carViewModel)

        carViewModel.getAddCarResult().observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            ProgressDialog.hideProgressDialog()
            SimpleAlertDialog.Builder(requireActivity())
                .message("Registro exitoso")
                .primaryButton("OK")
                .typeDialog(SimpleAlertDialog.SUCCESS)
                .create().apply {
                    setOnclickListener(onPrimary = {
                        dismiss()
                        requireActivity().onBackPressed()
                    }
                    )
                }.show(childFragmentManager, null)
        })

        carViewModel.getErrorResult().observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            if(it.isNotEmpty()){
                ProgressDialog.hideProgressDialog()
                SimpleAlertDialog.Builder(requireActivity())
                    .title("Error")
                    .message(it)
                    .primaryButton("OK")
                    .typeDialog(SimpleAlertDialog.ERROR)
                    .create().apply {
                    }.show(childFragmentManager, null)
            }
        })

    }

    companion object {
        fun newInstance(): EnterCarFragment {
            val args = Bundle()
            val fragment = EnterCarFragment()
            fragment.arguments = args
            return fragment
        }
    }
}