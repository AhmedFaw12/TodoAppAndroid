package com.faw.todo.base

import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment

open class BaseFragment:Fragment() {
    var progressDialog: ProgressDialog?= null

    fun showLoadingDialog(){
        progressDialog = ProgressDialog(requireContext())
        progressDialog?.setMessage("Loading...")
        progressDialog?.show()
    }


    fun hideLoadingDialog(){
        progressDialog?.dismiss()
    }

    fun showToast(context: Context, message:String){
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    var alertDialog: AlertDialog?= null

    fun showMessage(
        message:String,
        positiveActionTitle:String ?=null,
        positiveAction : DialogInterface.OnClickListener ?= null,
        negativeActionTitle :String ?= null,
        negativeAction : DialogInterface.OnClickListener ?= null,
        cancelable:Boolean = true
    ){
        var messageDialogBuilder = AlertDialog.Builder(requireContext())
        messageDialogBuilder.setMessage(message)

        if(positiveActionTitle != null){
            messageDialogBuilder.setPositiveButton(
                positiveActionTitle,
                positiveAction ?: DialogInterface.OnClickListener{
                        dialog, btnId -> dialog.dismiss()
                }
            )
        }

        if(negativeActionTitle != null){
            messageDialogBuilder.setNegativeButton(
                negativeActionTitle,
                negativeAction ?: DialogInterface.OnClickListener{
                        dialog, btnId -> dialog.dismiss()
                }
            )
        }

        messageDialogBuilder.setCancelable(cancelable)
        alertDialog = messageDialogBuilder.show()
    }

}