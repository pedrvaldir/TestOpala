package com.example.testopala

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import com.example.testopala.retrofit.RetrofitInitializer
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.concurrent.fixedRateTimer

class MainActivity : AppCompatActivity(), PadAdapter.selectedPadItem {


    private lateinit var mfragment: PadListFragment
    private lateinit var listPads: List<PadEntity>
    private lateinit var colorsList: Array<String?>
    private lateinit var listTime:  ArrayList<Double>

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

                colorsList[listPads.pad-1] = listPads.color

            }
        }

        mfragment = PadListFragment.newInstance(colorsList)
        mfragment.mListener = this

        supportFragmentManager.beginTransaction().replace(R.id.frameContent, mfragment).commit()

    }

    override fun onPadClick() {

        var time = 0L

        Thread(Runnable {

            var sizeList=0
            do {

                for (listPads in listPads) {
                    if (time>=listPads.time && time<=listPads.time+1){

                        if(listPads.pad!=100 && listPads.pad!=101){
                            mfragment.itemSelected(listPads.pad-1)
                            sizeList++
                            Log.i("threaddispada", "pad"+listPads.time)
                            listPads.pad
                        }else if(listPads.pad==100){
                            sizeList++
                            Log.i("threadthread", "inflar layout 2"+listPads.pad)
                            listPads.pad
                        }else if(listPads.pad==101){
                            sizeList++
                            Log.i("threadthread", "inflar layout 2"+listPads.pad)
                            listPads.pad
                        }
                    }
                }

                Thread.sleep(1000)
                time+=1L
                Log.i("size", "sizeList"+sizeList+"listpadsize"+listPads.size)

            }while(sizeList<listPads.size)



        }).start()

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

}}
