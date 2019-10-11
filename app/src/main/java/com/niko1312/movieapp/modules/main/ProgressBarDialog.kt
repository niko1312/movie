package com.niko1312.movieapp.modules.main

import android.app.Dialog
import android.content.Context
import android.view.Window
import com.niko1312.movieapp.R

class ProgressBarDialog(context: Context) : Dialog(context) {

    init {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.progress_bar)
        setCancelable(false)
        setCanceledOnTouchOutside(false)
    }

    fun showProgress() {
        if (!isShowing) {
            this.show()
        }
    }

    fun dismissProgress() {
        if (isShowing) {
            this.dismiss()
        }
    }

}
