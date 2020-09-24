package com.hugo.test.utils

import android.app.Activity
import android.content.Intent
import com.hugo.test.ui.main.MainActivity

class Navigation {

    companion object{

        fun goToHome(activity: Activity?){
            val intent = Intent(activity, MainActivity::class.java)
            activity?.startActivity(intent)
        }
    }


}