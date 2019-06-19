package com.example.testopala

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.ActionBar
import android.util.Log
import android.widget.Toast
import com.example.testopala.retrofit.RetrofitInitializer
import kotlinx.android.synthetic.main.action_bar.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(), PadAdapter.selectedPadItem {

    private lateinit var mfragment: PadListFragment
    private lateinit var mListPads: List<PadEntity>
    private var initTime: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM;
        supportActionBar?.setCustomView(R.layout.action_bar)

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

        mListPads = padList
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

                    for (listPads in mListPads) {
                        if (time >= listPads.time && time <= listPads.time + 1) {

                            if (listPads.pad != 100 && listPads.pad != 101) {
                                mfragment.itemSelected(listPads.pad - 1)
                                sizeList++
                            } else if (listPads.pad == 101) {

                                runOnUiThread {
                                    action_bar_title.text = getText(R.string.side_b)
                                }

                                mfragment = PadListFragment.newInstance(Constants.PADFILTER.SIDEB)
                                mfragment.mListener = this
                                supportFragmentManager.beginTransaction().replace(R.id.frameContent, mfragment).commit()
                            }
                        }
                    }

                    Thread.sleep(1000)
                    time += 1L



                } while (sizeList <= mListPads.size - 1)



            }).start()
        }
    }

    companion object colorsListPads {
        lateinit var colorsList: Array<String?>
    }
}