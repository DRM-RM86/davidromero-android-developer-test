package com.hugo.test.ui.residents

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.hugo.test.R
import com.hugo.test.bases.BaseFragment
import com.hugo.test.databinding.FragmentResidentsBinding
import com.hugo.test.storage.ParkingStorage
import com.hugo.test.ui.entercar.domain.CarRepository
import com.hugo.test.ui.entercar.repository.CarRepositoryImp
import com.hugo.test.ui.entercar.viewModel.CarViewModel
import com.hugo.test.ui.residents.domain.ResidentRepository
import com.hugo.test.ui.residents.reposotory.ResidentRepositoryImp
import com.hugo.test.ui.residents.viewModel.ResidentViewModel
import com.hugo.test.utils.customviews.SimpleAlertDialog
import com.hugo.test.utils.customviews.progressdialog.ProgressDialog
import com.hugo.test.utils.getParkingStorage
import com.hugo.test.utils.getViewModel

class ResidentsFragment : BaseFragment() {

    lateinit var binding : FragmentResidentsBinding
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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.btAddResident.setOnClickListener {
            ProgressDialog.showProgressDialog(childFragmentManager)
            residentViewModel.addResident(binding.edPlaca.text.toString(), binding.edModel.text.toString(),binding.edOwner.text.toString())
        }
        setObservers()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_residents, container, false)
        return binding.root


    }


    private fun setObservers(){
        setViewModel(residentViewModel)

        residentViewModel.getAddResidentResult().observe(viewLifecycleOwner, Observer {
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

        residentViewModel.getListResidents().observe(viewLifecycleOwner, Observer {

        })

        residentViewModel.getErrorResult().observe(viewLifecycleOwner, androidx.lifecycle.Observer {
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
        fun newInstance(): ResidentsFragment {
            val args = Bundle()
            val fragment = ResidentsFragment()
            fragment.arguments = args
            return fragment
        }
    }
}