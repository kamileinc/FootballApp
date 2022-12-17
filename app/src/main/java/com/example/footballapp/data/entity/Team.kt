package com.example.footballapp.data.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "favourites")
data class Team(
    val coach: String,
    val emblem: String,
    @PrimaryKey val id: String,
    val name: String,
    val stadium: String,
    var favorite: Boolean?
) : Parcelable
