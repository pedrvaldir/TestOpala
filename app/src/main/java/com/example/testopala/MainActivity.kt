package com.example.testopala

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import com.example.testopala.retrofit.RetrofitInitializer
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var listPads: List<PadEntity>
    private lateinit var colorsList: Array<String?>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        handleDataListPad()

    }

    private fun handleDataListPad() {

        val call = RetrofitInitializer().padService().list(Constants.URL.BASE + Constants.URL.ENDPOINT)

        call.enqueue(object : Callback<List<PadEntity>?> {
            override fun onResponse(
                call: Call<List<PadEntity>?>?,
                response: Response<List<PadEntity>?>?
            ) {
                response?.body()?.let {
                    val pads: List<PadEntity>? = it
                    configureList(pads!!)
                }
            }

            override fun onFailure(
                call: Call<List<PadEntity>?>?,
                t: Throwable?
            ) {
                Log.e("onFailure error", t?.message)
            }
        })

    }

    private fun configureList(padList: List<PadEntity>) {

        listPads = padList
        colorsList = Array<String?>(24) { null }

        for (listPads in listPads) {

            val lisTime = listOf(listPads.time)

            if (listPads.pad != 100 && listPads.pad != 101) {

                colorsList[listPads.pad-1] = listPads.color

                Log.d("Executa:", "--" + listPads.pad.toString() + "stringlist" + colorsList.get(listPads.pad-1) + "<cor")
            }
        }

        val fragment: Fragment = PadListFragment.newInstance(colorsList)

        supportFragmentManager.beginTransaction().replace(R.id.frameContent, fragment).commit()

    }


    private fun startDefaultFragment() {

        val fragment: Fragment = PadListFragment.newInstance(colorsList)

        supportFragmentManager.beginTransaction().replace(R.id.frameContent, fragment).commit()
    }

    override fun onCreateOptionsMenu(menu: android.view.Menu?): kotlin.Boolean {
        val inflater = menuInflater

        inflater.inflate(R.menu.menu_toolbar, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: android.view.MenuItem): kotlin.Boolean {
        when (item.itemId) {
            R.id.menu_page -> {
                val fragment: Fragment = PadListFragment.newInstance(colorsList)

                supportFragmentManager.beginTransaction().replace(R.id.frameContent, fragment).commit()
                return true
            }


        }
        return super.onOptionsItemSelected(item)
    }

}
