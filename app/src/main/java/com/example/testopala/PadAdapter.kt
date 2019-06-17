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

        mContext = parent?.context
        val inflater = LayoutInflater.from(mContext)
        val view = inflater.inflate(R.layout.pad_entry, parent, false)

        val ivPad = view.findViewById<ImageView>(R.id.id_pad)
        ivPad.layoutParams.height = heightGridView / 4

        view.setOnClickListener {
            view.elevation = 10F
            startAnimationSelector(ivPad, pad, view)
        }

        customSelectorImageView(ivPad, pad)

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

    private fun customSelectorImageView(ivPad: ImageView, pad: PadEntity) {
        val layerDrawable =  ContextCompat.getDrawable(mContext!!, R.drawable.bg_pad_selected) as (LayerDrawable)
        val selectedCustom = layerDrawable.findDrawableByLayerId(R.id.layerItem)
        (selectedCustom as GradientDrawable).setColor(getColorWithAlpha(ContextCompat.getColor(mContext!!, pad.color), 0.5F))


        val layerDrawable2 =  ContextCompat.getDrawable(mContext!!, R.drawable.bg_pad_selected) as (LayerDrawable)
        val selectedCustom2 = layerDrawable2.findDrawableByLayerId(R.id.layerItem2)
        (selectedCustom2 as GradientDrawable).setColor(getColorWithAlpha(ContextCompat.getColor(mContext!!, pad.color), 0.2F))

        val stateListDrawable = StateListDrawable()
        stateListDrawable.addState(intArrayOf(android.R.attr.state_selected), layerDrawable)
        stateListDrawable.addState(intArrayOf(-android.R.attr.state_selected),
            ContextCompat.getDrawable(mContext!!, R.drawable.bg_pad))

        ViewCompat.setBackground(ivPad, stateListDrawable)
    }

    private fun getColorWithAlpha(color: Int, ratio: Float): Int {
        return Color.argb(Math.round(Color.alpha(color) * ratio), Color.red(color), Color.green(color), Color.blue(color))
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