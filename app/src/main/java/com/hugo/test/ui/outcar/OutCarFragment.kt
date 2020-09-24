package com.hugo.test.ui.outcar

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.hugo.test.R
import com.hugo.test.bases.BaseFragment
import com.hugo.test.databinding.FragmentOutCarBinding
import com.hugo.test.storage.ParkingStorage
import com.hugo.test.ui.detailexit.DetailOutActivity
import com.hugo.test.ui.entercar.domain.CarRepository
import com.hugo.test.ui.entercar.repository.CarRepositoryImp
import com.hugo.test.ui.entercar.viewModel.CarViewModel
import com.hugo.test.ui.outcar.adapter.AdapterCars
import com.hugo.test.utils.customviews.SimpleAlertDialog
import com.hugo.test.utils.customviews.progressdialog.ProgressDialog
import com.hugo.test.utils.getParkingStorage
import com.hugo.test.utils.getViewModel


class OutCarFragment : BaseFragment() {

    lateinit var binding : FragmentOutCarBinding
    private val parkingStorage: ParkingStorage by lazy {
        getParkingStorage()
    }
    private val carRepository: CarRepository by lazy {
        CarRepositoryImp(parkingStorage)
    }
    private val carViewModel: CarViewModel by lazy {
        getViewModel {
            CarViewModel(carRepository)
        }
    }


    private lateinit var carsAdapter:AdapterCars


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        ProgressDialog.showProgressDialog(childFragmentManager)
        carViewModel.getAllCars()
        setAdapter()
        setRecyclerView()
        setObserver()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_out_car, container, false)
        return binding.root

    }


    private fun setObserver(){
        setViewModel(carViewModel)
        carViewModel.getListCars().observe(viewLifecycleOwner, Observer {
            ProgressDialog.hideProgressDialog()
            if(it.isEmpty()){
                SimpleAlertDialog.Builder(requireActivity())
                    .message("No se encontraron registros")
                    .primaryButton("OK")
                    .typeDialog(SimpleAlertDialog.INFO)
                    .create().apply {
                        setOnclickListener(onPrimary = {
                            dismiss()
                            requireActivity().onBackPressed()
                        }
                        )
                    }.show(childFragmentManager, null)
            }else{
                carsAdapter.setData(requireContext(),it)
                carsAdapter.notifyDataSetChanged()
            }
        })



    }


    private fun setAdapter(){
        carsAdapter = AdapterCars {
            val intent = Intent(activity, DetailOutActivity::class.java)
            intent.putExtra("car",it)
            activity?.startActivity(intent)
            activity?.onBackPressed()
        }
    }

    private fun setRecyclerView() {

        binding.rvCars.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = carsAdapter
        }
    }

    companion object {

        fun newInstance(): OutCarFragment {

            val args = Bundle()
            val fragment = OutCarFragment()
            fragment.arguments = args
            return fragment
        }
    }
}