package com.sample.clean.ui.event

sealed class ClickEvent {

    data class LikeClicked(val checked: Boolean, val pos: Int): ClickEvent()

}
