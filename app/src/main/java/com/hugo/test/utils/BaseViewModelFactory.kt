package com.hugo.test.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * The [BaseViewModelFactory] class is used to create a [ViewModel]
 * instance with parameters using [creator].
 */

@Suppress("UNCHECKED_CAST")
class BaseViewModelFactory<T>(val creator: () -> T) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return creator() as T
    }
}