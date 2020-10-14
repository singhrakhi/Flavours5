package com.flavours5.common

import android.content.Context

object ClsGeneral {
    private var mContext: Context? = null
    fun setPreferences(
        context: Context?,
        key: String?,
        value: String?
    ) {
        mContext = context
        val editor = mContext!!.getSharedPreferences(
            "WED_APP", Context.MODE_PRIVATE
        ).edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun getPreferences(context: Context?, key: String?): String? {
        mContext = context
        val prefs = mContext!!.getSharedPreferences(
            "WED_APP",
            Context.MODE_PRIVATE
        )
        return prefs.getString(key, "")
    }
}