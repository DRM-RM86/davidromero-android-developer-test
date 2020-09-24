package com.hugo.test.utils.customviews

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.annotation.StringRes
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.hugo.test.R

class SimpleAlertDialog : DialogFragment() {

    private var onClickListener: OnClickListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.AppTheme_FullScreenDialog)
        isCancelable = arguments?.getBoolean(CANCELABLE) ?: false
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.simple_alert_dialog, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val title: TextView = view.findViewById(R.id.dialogTitle)
        title.text = getOrElse(TITLE) { title.visibility = View.GONE }

        val message: TextView = view.findViewById(R.id.dialogMessage)
        message.text = getOrElse(MESSAGE) { message.visibility = View.GONE }

        val primary: Button = view.findViewById(R.id.primaryButton)
        primary.text = getOrElse(PRIMARY) { primary.visibility = View.GONE }

        val secondary: Button = view.findViewById(R.id.secondaryButton)
        secondary.text = getOrElse(SECONDARY) {
            secondary.visibility = View.GONE
        }





        arrayOf(primary, secondary).forEach { btn -> btn.setOnClickListener { onClick(it.id) } }

        val icon: AppCompatImageView = view.findViewById(R.id.dialogIcon)

        arguments?.getString(TYPE)?.let {
            when (it) {
                SUCCESS -> icon.setImageResource(R.drawable.ic_dialog_success)
                INFO -> icon.setImageResource(R.drawable.ic_dialog_info)
                ALERT -> icon.setImageResource(R.drawable.ic_dialog_alert)
                ERROR -> icon.setImageResource(R.drawable.ic_dialog_error)
                else -> icon.setImageResource(R.drawable.ic_dialog_info)
            }
        } ?: icon.setImageResource(R.drawable.ic_dialog_alert)
    }

    private fun onClick(btnId: Int) {
        dismiss()

        arguments?.let {
            val requestCode = it.getInt(REQUEST_CODE)
            val extras = it.getBundle(EXTRAS)
            onClickListener?.onClickListener(requestCode, btnId, extras)
        }
    }

    override fun onStart() {
        super.onStart()

        val matchParent = ViewGroup.LayoutParams.MATCH_PARENT

        dialog?.window?.apply {
            setLayout(matchParent, matchParent)
            clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (onClickListener == null) {
            val fragment = targetFragment
            if (fragment is OnClickListener) onClickListener = fragment
            else if (context is OnClickListener) onClickListener = context
        }
    }

    private fun getOrElse(key: String, fallback: () -> Unit): CharSequence? {
        val value = arguments?.getCharSequence(key)
        if (value.isNullOrEmpty()) fallback.invoke()
        return value
    }

    fun setOnclickListener(onPrimary: () -> Unit = {}, onSecondary: () -> Unit = {}) {

        onClickListener = object : OnClickListener {
            override fun onClickListener(requestCode: Int, which: Int, extras: Bundle?) {
                when (which) {
                    R.id.primaryButton -> onPrimary()
                    R.id.secondaryButton -> onSecondary()
                }
            }
        }
    }

    class Builder(private val context: Context) {

        private val bundle = bundleOf()

        fun title(@StringRes title: Int): Builder {
            title(context.getString(title))
            return this
        }

        fun title(title: CharSequence): Builder {
            bundle.putCharSequence(TITLE, title)
            return this
        }

        fun message(@StringRes message: Int): Builder {
            message(context.getString(message))
            return this
        }

        fun message(message: CharSequence): Builder {
            bundle.putCharSequence(MESSAGE, message)
            return this
        }

        fun primaryButton(@StringRes primary: Int): Builder {
            primaryButton(context.getString(primary))
            return this
        }

        fun primaryButton(primary: CharSequence): Builder {
            bundle.putCharSequence(PRIMARY, primary)
            return this
        }

        fun secondaryButton(@StringRes secondary: Int): Builder {
            secondaryButton(context.getString(secondary))
            return this
        }

        fun typeDialog(type: CharSequence): Builder {
            bundle.putCharSequence(TYPE, type)
            return this
        }

        fun secondaryButton(secondary: CharSequence): Builder {
            bundle.putCharSequence(SECONDARY, secondary)
            return this
        }

        fun extras(extras: Bundle): Builder {
            bundle.putBundle(EXTRAS, extras)
            return this
        }

        fun cancelable(cancelable: Boolean = false): Builder {
            bundle.putBoolean(CANCELABLE, cancelable)
            return this
        }

        fun create(fragment: Fragment? = null, requestCode: Int = 0): SimpleAlertDialog {
            bundle.putInt(REQUEST_CODE, requestCode)
            return SimpleAlertDialog().apply {
                arguments = bundle
                if (fragment != null) setTargetFragment(fragment, requestCode)
            }
        }

    }

    interface OnClickListener {
        fun onClickListener(requestCode: Int, which: Int, extras: Bundle?)
    }

    companion object {
        private const val TITLE = "title"
        private const val MESSAGE = "message"
        private const val PRIMARY = "primary"
        private const val SECONDARY = "secondary"
        private const val REQUEST_CODE = "requestCode"
        private const val EXTRAS = "extras"
        private const val TYPE = "type"
        private const val CANCELABLE = "cancelable"
        const val SUCCESS = "success"
        const val INFO = "info"
        const val ALERT = "alert"
        const val ERROR = "error"

    }

}