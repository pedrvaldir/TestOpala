package com.example.testopala

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import com.example.testopala.retrofit.RetrofitInitializer
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(), PadAdapter.selectedPadItem {

    private lateinit var mfragment: PadListFragment
    private lateinit var listPads: List<PadEntity>
    private lateinit var listTime: ArrayList<Double>
    private var initTime: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        title = getString(R.string.side_a)

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
                    setColorsGrid(pads!!)
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

    private fun setColorsGrid(padList: List<PadEntity>) {

        listPads = padList
        listTime = ArrayList()
        colorsList = Array(24) { null }

        for (listPads in padList) {

            if (listPads.pad != 100 && listPads.pad != 101) {
                colorsList[listPads.pad - 1] = listPads.color
            }
        }

        mfragment = PadListFragment.newInstance(Constants.PADFILTER.SIDEA)
        mfragment.mListener = this

        supportFragmentManager.beginTransaction().replace(R.id.frameContent, mfragment).commit()

    }

    override fun onPadClick() {

        if (!initTime) {

            initTime = true

            var time = 0L
            var sizeList = 0

            Thread(Runnable {

                do {

                    for (listPads in listPads) {
                        if (time >= listPads.time && time <= listPads.time + 1) {

                            if (listPads.pad != 100 && listPads.pad != 101) {
                                mfragment.itemSelected(listPads.pad - 1)
                                sizeList++
                            } else if (listPads.pad == 101) {

                                runOnUiThread {
                                    title = getString(R.string.side_b)
                                }

                                mfragment = PadListFragment.newInstance(Constants.PADFILTER.SIDEB)
                                mfragment.mListener = this
                                supportFragmentManager.beginTransaction().replace(R.id.frameContent, mfragment).commit()
                            }
                        }
                    }

                    Thread.sleep(1000)
                    time += 1L

                } while (sizeList <= listPads.size - 1)
            }).start()
        }
    }

    override fun onCreateOptionsMenu(menu: android.view.Menu?): kotlin.Boolean {
        val inflater = menuInflater

        inflater.inflate(R.menu.menu_toolbar, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: android.view.MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_page -> {
                val fragment: Fragment = PadListFragment.newInstance(Constants.PADFILTER.SIDEA)

                supportFragmentManager.beginTransaction().replace(R.id.frameContent, fragment).commit()
                return true
            }


        }
        return super.onOptionsItemSelected(item)

    }

    companion object colorsListPads {
        lateinit var colorsList: Array<String?>
    }
}
