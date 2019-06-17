package com.example.testopala

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.graphics.drawable.StateListDrawable
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewCompat
import kotlinx.android.synthetic.main.pad_entry.view.*

class PadAdapter(val padList: List<PadEntity>, val heightGridView: Int) : BaseAdapter() {

    private var mContext: Context? = null

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val pad = this.padList[position]

        val context = parent?.context
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.pad_entry, parent, false)

        val ivPad = view.findViewById<ImageView>(R.id.id_pad)
        ivPad.layoutParams.height = heightGridView / 4

        view.setOnClickListener {
            view.elevation = 10F
            startAnimationSelector(ivPad, pad, view)
        }

        return view
    }

    private fun startAnimationSelector(ivPad: ImageView, pad: PadEntity,  view: View) {
        Thread(Runnable {
            if (!ivPad.isSelected) {
                ivPad.isSelected = true
                Thread.sleep(500)
                view.elevation = 0F
            }

            ivPad.isSelected = false

        }).start()
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