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
    private var mAdapter: PadAdapter? = null
    lateinit var mListener: PadAdapter.selectedPadItem
    lateinit private var mSide: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (arguments != null) {
            mSide = arguments!!.getString(Constants.PADFILTER.KEY)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_pad_list, container, false)

        mGridViewList = rootView.findViewById(R.id.gridViewList)

        loadColorPads()

        mGridViewList.adapter = mAdapter

        return rootView
    }


    private fun loadColorPads() {

        val listdfas = MainActivity.colorsList

        val data = arrayListOf<PadEntity>()

        if (mSide == Constants.PADFILTER.SIDEA) {
            for (item in 0..11)
                data.add(PadEntity(listdfas!![item], item, 1, 0.0))
        } else {
            for (item in 12..23)
                data.add(PadEntity(listdfas!![item], item, 1, 0.0))
        }

        mAdapter = PadAdapter(data!!, getHeightScreen(), mListener)
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

    fun itemSelected(pad: Int) {
        mAdapter?.seekPad(pad)
    }

    companion object {
        @JvmStatic
        fun newInstance(side: String): PadListFragment {
            val args: Bundle = Bundle()
            args.putString(Constants.PADFILTER.KEY, side)

            val fragment = PadListFragment()
            fragment.arguments = args

            return fragment
        }
    }
}