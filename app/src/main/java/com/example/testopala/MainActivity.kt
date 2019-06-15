package com.example.testopala

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        handleDataListPad()

        startDefaultFragment()
    }

    fun handleDataListPad(){

    }



    private fun startDefaultFragment() {

        val fragment: Fragment = PadListFragment.newInstance()

        supportFragmentManager.beginTransaction().replace(R.id.frameContent, fragment).commit()
    }

    override fun onCreateOptionsMenu(menu: android.view.Menu?): kotlin.Boolean {
        val inflater = menuInflater

        inflater.inflate(R.menu.menu_toolbar, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: android.view.MenuItem): kotlin.Boolean {
        when (item.itemId){
            R.id.menu_page ->{
                val fragment: Fragment = PadListFragment.newInstance()

                supportFragmentManager.beginTransaction().replace(R.id.frameContent, fragment).commit()
                return true
            }

        }
        return super.onOptionsItemSelected(item)
    }

}
