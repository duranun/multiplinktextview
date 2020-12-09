package com.duranun.multilinktextview

import android.text.TextPaint
import android.text.style.ClickableSpan
import android.view.View

open class ClickableSpanText(private val pos: Int, private val listener: OnLinkClickListener?) :
    ClickableSpan() {

    override fun onClick(p0: View) {
        listener?.onLinkClick(p0, pos)
    }

    open var underlineSpan = false

    override fun updateDrawState(ds: TextPaint) {
        super.updateDrawState(ds)
        ds.isUnderlineText = underlineSpan
    }
}