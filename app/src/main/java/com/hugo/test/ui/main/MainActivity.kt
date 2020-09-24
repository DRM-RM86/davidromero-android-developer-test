package com.hugo.test.ui.main

import android.os.Bundle
import com.hugo.test.R
import com.hugo.test.bases.BaseActivity
import com.hugo.test.ui.main.fragment.MainFragment

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}