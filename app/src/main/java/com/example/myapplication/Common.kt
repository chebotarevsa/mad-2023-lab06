package com.example.myapplication

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher

sealed class Status(var isProcessed: Boolean = false)
class Success() : Status()
class Failed(val message: String) : Status()


interface ActionInterface {
    fun onItemClick(cardId: Int)
    fun onDeleteCard(cardId: Int)
}

fun Uri?.bitmap(context: Context): Bitmap? {
    return this?.let {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            ImageDecoder.decodeBitmap(ImageDecoder.createSource(context.contentResolver, it))
        } else {
            MediaStore.Images.Media.getBitmap(context.contentResolver, it)
        }
    }
}