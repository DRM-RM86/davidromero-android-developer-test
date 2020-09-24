package com.hugo.test.utils.customviews.progressdialog

import android.util.Log
import androidx.fragment.app.FragmentManager
import java.lang.IllegalStateException

object ProgressDialog {

    private lateinit var loaderProgressDialog: LoaderProgressDialog
    private val TAG = "ProgressDialog"

    fun showProgressDialog(fragmentManager: FragmentManager, message: String = "") {

        val fragmentByTag = fragmentManager.findFragmentByTag(TAG)
        if (fragmentByTag is LoaderProgressDialog) {
            loaderProgressDialog = fragmentByTag
        }
        if (this::loaderProgressDialog.isInitialized && loaderProgressDialog.isShowing()) {
            return
        }
        loaderProgressDialog = if (message.isNotBlank())
            LoaderProgressDialog.newInstance(message) else LoaderProgressDialog.newInstance()

        try {
            loaderProgressDialog.show(fragmentManager, TAG)
        } catch (ex: IllegalStateException) {
            Log.e("Error",ex.toString())
        }
    }

    fun hideProgressDialog() {

        if (!this::loaderProgressDialog.isInitialized) {
            return
        }

        loaderProgressDialog.let {
            it.fragmentManager?.let {
                try {
                    loaderProgressDialog.dismissAllowingStateLoss()
                } catch (ex: NullPointerException) {

                }
            }
        }
    }
}