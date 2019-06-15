package com.example.testopala

import android.support.v4.content.ContextCompat
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import kotlinx.android.synthetic.main.pad_entry.view.*

class PadAdapter(val padList: List<PadEntity>) : BaseAdapter() {

    private var mColorResourceId: Int = 0

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val pad = this.padList[position]

        val context = parent?.context
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.pad_entry, parent, false)

        val textContainer: View = view.findViewById(R.id.id_pad)

        var color = ContextCompat.getColor(context!!, pad.color)

        textContainer.setBackgroundColor(color)

        return view
    }

    override fun getItem(position: Int): Any {
        return padList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return padList.size
    }
}