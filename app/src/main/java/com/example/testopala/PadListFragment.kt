package com.example.testopala

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

        adapter = PadAdapter(data)

        mGridViewList.adapter = adapter

        return rootView
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