package com.example.gifimagegallery.entity

data class GifItemView(
    var id: String?,
    var name: String?,
    var url: String?
) {
    override fun equals(other: Any?): Boolean {
        if (javaClass != other?.javaClass) {
            return false
        }
        other as GifItemView
        if (id != other.id) {
            return false
        }
        if (name != other.name) {
            return false
        }
        if (url != other.url) {
            return false
        }
        return true
    }
}