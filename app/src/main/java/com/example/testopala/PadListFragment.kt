package com.example.testopala

import android.content.Context
import android.graphics.Point
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.GridView

class PadListFragment : Fragment() {

    private lateinit var mGridViewList: GridView
    private var mPadsFilter: Array<String>? = null
    private var adapter: PadAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mPadsFilter = arguments!!.getStringArray("Side")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_pad_list, container, false)

        //localiza o layout
        mGridViewList = rootView.findViewById(R.id.gridViewList)

        //insertData Gridview mock


        loadColorPads()

        mGridViewList.adapter = adapter

        return rootView
    }

    private fun loadColorPads() {

        val listdfas = mPadsFilter

        val data = arrayListOf<PadEntity>()

        for (item in 0..11) {
            data.add(PadEntity(listdfas!![item], item, 1, 0.0))
        }
        adapter = PadAdapter(data!!, getHeightScreen())
    }

    private fun getHeightScreen(): Int {
        val display = (context?.getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay

        // check display size to figure out what image resolution will be loaded
        val metrics = DisplayMetrics()
        display.getMetrics(metrics)
        val size = Point()
        display.getSize(size)
        var height = size.y

        height -= getStatusBarSize()
        height -= getSizeStatusBar()
        //height -= getPaddingTotal()

        return height
    }

    private fun getStatusBarSize(): Int {
        val tv = TypedValue()
        var actionBarHeight = 0
        if (context?.theme?.resolveAttribute(android.R.attr.actionBarSize, tv, true) == true) {
            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, resources.displayMetrics)
        }

        return actionBarHeight
    }


    private fun getSizeStatusBar(): Int {
        var result = 0
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = resources.getDimensionPixelSize(resourceId)
        }
        return result
    }

    companion object {
        @JvmStatic
        fun newInstance(colorsList: Array<String?>): PadListFragment {
            val args: Bundle = Bundle()
            args.putStringArray("Side", colorsList)

            val fragment = PadListFragment()
            fragment.arguments = args

            return fragment
        }
    }
}