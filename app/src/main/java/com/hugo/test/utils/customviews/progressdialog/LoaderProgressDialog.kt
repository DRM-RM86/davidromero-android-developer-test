package com.hugo.test.utils.customviews.progressdialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.hugo.test.R
import com.hugo.test.databinding.DialogLoaderProgressBinding

class LoaderProgressDialog : DialogFragment() {

    private var message: String? = ""
    lateinit var binding : DialogLoaderProgressBinding
    companion object {
        private const val MESSAGE = "message"

        fun newInstance() = LoaderProgressDialog()

        fun newInstance(message: String): LoaderProgressDialog {
            val loaderProgressDialog = LoaderProgressDialog()
            val args = Bundle()
            args.putString(MESSAGE, message)
            loaderProgressDialog.arguments = args
            return loaderProgressDialog
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.TransparentDialogTheme)

        isCancelable = false
        arguments?.let {
            message = it.getString(MESSAGE)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_loader_progress, container, false)
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (!message.isNullOrBlank()) {
            binding.txtLoading.text = message
            binding.txtLoading.maxLines = 10
        }
    }

    fun isShowing() = dialog != null && dialog?.isShowing ?: false
}