package com.example.testopala

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startDefaultFragment()
    }

    private fun startDefaultFragment() {

        val fragment : Fragment = PadListFragment.newInstance()

        supportFragmentManager.beginTransaction().replace(R.id.frameContent, fragment).commit()
    }
}
