package com.dicoding.picodiploma.mynotesapp.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Created by sidiqpermana on 11/23/16.
 */
@Parcelize
data class Note(
    var id: Int = 0,
    var title: String? = null,
    var description: String? = null,
    var date: String? = null
) : Parcelable
