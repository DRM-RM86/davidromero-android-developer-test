package com.hugo.test.ui.main.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.hugo.test.R
import com.hugo.test.databinding.FragmentMainBinding


class MainFragment : Fragment() {

   lateinit var binding : FragmentMainBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)
        return binding.root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.cvInCar.setOnClickListener {
            findNavController().navigate(R.id.action_nav_main_to_nav_enter_car)
        }
        binding.cvOutCar.setOnClickListener {
            findNavController().navigate(R.id.action_nav_main_to_out_car)
        }

        binding.cvResidents.setOnClickListener {
            findNavController().navigate(R.id.nav_main_to_nav_residents)
        }
        binding.cvReports.setOnClickListener {
            findNavController().navigate(R.id.action_nav_main_to_nav_settings)
        }
    }

    companion object {

        fun newInstance(): MainFragment {

            val args = Bundle()
            val fragment = MainFragment()
            fragment.arguments = args
            return fragment
        }
    }

}