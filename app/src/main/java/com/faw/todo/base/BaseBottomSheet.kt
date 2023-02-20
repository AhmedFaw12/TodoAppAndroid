package com.faw.todo.base

import android.app.ProgressDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

open class BaseBottomSheet:BottomSheetDialogFragment() {
    var progressDialog: ProgressDialog?= null

    fun showLoadingDialog(){
        progressDialog = ProgressDialog(requireContext())
        progressDialog?.setMessage("Loading...")
        progressDialog?.show()
    }


    fun hideLoadingDialog(){
        progressDialog?.dismiss()
    }

}