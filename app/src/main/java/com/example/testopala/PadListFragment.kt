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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {

        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_pad_list, container, false)

        //localiza o layout
        mGridViewList = rootView.findViewById(R.id.gridViewList)

        //insertData Gridview mock
        var adapter: PadAdapter? = null

        val data = listOf(
            PadEntity(R.color.pad_pink, 100, 1, 6.183232125011273),
            PadEntity(R.color.pad_blue, 100, 1, 6.183232125011273),
            PadEntity(R.color.pad_blue, 100, 1, 6.183232125011273),
            PadEntity(R.color.pad_green, 100, 1, 6.183232125011273),
            PadEntity(R.color.pad_pink, 100, 1, 6.183232125011273),
            PadEntity(R.color.pad_pink, 100, 1, 6.183232125011273),
            PadEntity(R.color.pad_pink, 100, 1, 6.183232125011273),
            PadEntity(R.color.pad_blue, 100, 1, 6.183232125011273),
            PadEntity(R.color.pad_blue, 100, 1, 6.183232125011273),
            PadEntity(R.color.pad_green, 100, 1, 6.183232125011273),
            PadEntity(R.color.pad_pink, 100, 1, 6.183232125011273),
            PadEntity(R.color.pad_pink, 100, 1, 6.183232125011273)
        )

        adapter = PadAdapter(data, getHeightScreen())

        mGridViewList.adapter = adapter

        return rootView
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
        fun newInstance(): PadListFragment {
            val args: Bundle = Bundle()

            val fragment = PadListFragment()
            fragment.arguments = args

            return fragment
        }
    }
}