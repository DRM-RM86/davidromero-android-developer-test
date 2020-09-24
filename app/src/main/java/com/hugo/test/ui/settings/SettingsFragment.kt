package com.hugo.test.ui.settings

import android.content.Context
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.hugo.test.R
import com.hugo.test.databinding.FragmentSettingsBinding
import com.hugo.test.utils.customviews.progressdialog.ProgressDialog
import java.io.File
import java.io.FileWriter
import java.io.IOException


class SettingsFragment : Fragment() {

    lateinit var binding : FragmentSettingsBinding

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.cvReport.setOnClickListener {

            findNavController().navigate(R.id.action_nav_settings_to_nav_detail_pay)


           /* ProgressDialog.showProgressDialog(childFragmentManager,"Generando reporte")
            Handler(Looper.getMainLooper()).postDelayed({
                generateNoteOnSD(requireContext(), "Núm de placa     Tiempo estacionado(min)     Cantidad a Pagar")
            }, 2000)

*/


        }
        binding.cvInitMonth.setOnClickListener {



          /*  SimpleAlertDialog.Builder(requireActivity())
                .title("Reiniciar mes")
                .message("¿Estas seguro que deseas reiniciar el mes?")
                .primaryButton("Si")
                .secondaryButton("No")
                .typeDialog(SimpleAlertDialog.INFO)
                .create().apply {
                    setOnclickListener(onPrimary = {
                        dismiss()
                        generateNoteOnSD(requireContext(),"text.txt","Hola mundo")
                    }
                    )
                }.show(childFragmentManager, null)*/
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_settings, container, false)
        return binding.root

    }


    companion object {
        fun newInstance(): SettingsFragment {
            val args = Bundle()
            val fragment = SettingsFragment()
            fragment.arguments = args
            return fragment
        }
    }

    fun generateNoteOnSD(context: Context?, sBody: String?) {
        try {
            val root = File(Environment.getExternalStorageDirectory(), "test")
            if (!root.exists()) {
                root.mkdirs()
            }
            val gpxfile = File(root, "report.txt")
            val writer = FileWriter(gpxfile)
            writer.append(sBody)
            writer.flush()
            writer.close()
            ProgressDialog.hideProgressDialog()
            Toast.makeText(context, "Su archivo se ha guardado con éxito.", Toast.LENGTH_SHORT).show()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}