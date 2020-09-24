package com.hugo.test.bases

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel

open class BaseFragment:Fragment() {
    private lateinit var viewModel: ViewModel
    fun setViewModel(viewModel: ViewModel) {
        this.viewModel = viewModel
    }

}