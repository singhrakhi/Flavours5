package com.flavours5.common

import android.app.Dialog
import android.content.Context
import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.Build
import android.view.LayoutInflater
import androidx.annotation.NonNull
import androidx.core.content.res.ResourcesCompat
import com.flavours5.R
import kotlinx.android.synthetic.main.activity_progres.view.*

class Progress{
    lateinit var dialog: Dialog

    fun show(context: Context): Dialog {
        return show(context, null)
    }

    fun show(context: Context, title:CharSequence?): Dialog {
        val inflator = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflator.inflate(R.layout.activity_progres, null)
//        if (title != null) {
//            view.cp_title.text = title
//        }
        view.cp_bg_view.setBackgroundColor(Color.parseColor("#60000000")) //Background Color
        setColorFilter(view.cp_pbar.indeterminateDrawable,
            ResourcesCompat.getColor(context.resources, R.color.colorAccent, null)) //Progress Bar Color
//        view.cp_title.setTextColor(Color.BLACK) //Text Color

        try{
            dialog = Dialog(context, R.style.CustomProgressBarTheme)
            dialog.setContentView(view)
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);
            dialog.show()
        } catch (e: Exception){

        }

        return dialog
    }

    fun setColorFilter(@NonNull drawable: Drawable, color:Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            drawable.colorFilter = BlendModeColorFilter(color, BlendMode.SRC_ATOP)
        } else {
            @Suppress("DEPRECATION")
            drawable.setColorFilter(color, PorterDuff.Mode.SRC_ATOP)
        }
    }

}