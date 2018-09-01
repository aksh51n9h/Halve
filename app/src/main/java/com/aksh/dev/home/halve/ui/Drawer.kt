package com.aksh.dev.home.halve.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aksh.dev.home.halve.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class Drawer : BottomSheetDialogFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.drawer_layout, container, false)
    }

    companion object {
        fun newInstance(): Drawer = Drawer()
    }
}
