package com.martitech.multilinktextview

import android.view.View

interface OnLinkClickListener {
    fun onLinkClick(view: View, linkPosition: Int)
}