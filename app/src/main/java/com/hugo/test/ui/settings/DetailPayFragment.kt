package com.hugo.test.ui.settings

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.hugo.test.R
import com.hugo.test.bases.BaseFragment
import com.hugo.test.databinding.FragmentDetailPayBinding
import com.hugo.test.storage.ParkingStorage
import com.hugo.test.ui.detailexit.DetailOutActivity
import com.hugo.test.ui.detailexit.domain.RegisterExitRepository
import com.hugo.test.ui.detailexit.repository.RegisterExitRepositoryImp
import com.hugo.test.ui.detailexit.viewModel.RegisterExitViewModel
import com.hugo.test.ui.outcar.adapter.AdapterCars
import com.hugo.test.ui.residents.domain.ResidentRepository
import com.hugo.test.ui.residents.reposotory.ResidentRepositoryImp
import com.hugo.test.ui.residents.viewModel.ResidentViewModel
import com.hugo.test.ui.settings.adapter.DetailPayAdapter
import com.hugo.test.utils.customviews.SimpleAlertDialog
import com.hugo.test.utils.customviews.progressdialog.ProgressDialog
import com.hugo.test.utils.getParkingStorage
import com.hugo.test.utils.getViewModel

class DetailPayFragment : BaseFragment() {

    lateinit var binding: FragmentDetailPayBinding

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

    private val registerRepository: RegisterExitRepository by lazy {
        RegisterExitRepositoryImp(parkingStorage)
    }
    private val registerExitViewModel: RegisterExitViewModel by lazy {
        getViewModel {
            RegisterExitViewModel(registerRepository)
        }
    }


    lateinit var adapterDetail:DetailPayAdapter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setAdapter()
        setRecyclerView()
        setObservers()
        residentViewModel.getAllResidents()
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail_pay, container, false)
        return binding.root
    }


    private fun setObservers(){
        setViewModel(residentViewModel)
        residentViewModel.getListResidents().observe(viewLifecycleOwner, Observer {
            adapterDetail.setData(requireContext(),it)
            adapterDetail.notifyDataSetChanged()

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

    private fun setAdapter(){
        adapterDetail = DetailPayAdapter {
        }
    }

    private fun setRecyclerView() {
        binding.rvDetail.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = adapterDetail
        }
    }

}