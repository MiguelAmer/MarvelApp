package com.miguelamer.marvelapp.data.model

data class Image(val path: String, val extension: String) {

    fun getUrl() = "$path.$extension".replace("http", "https")

}
