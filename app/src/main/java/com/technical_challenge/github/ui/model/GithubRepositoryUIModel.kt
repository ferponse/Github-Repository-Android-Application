package com.technical_challenge.github.ui.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GithubRepositoryUIModel(
    val id: Long,
    val name: String,
    val stars: Int
) : Parcelable