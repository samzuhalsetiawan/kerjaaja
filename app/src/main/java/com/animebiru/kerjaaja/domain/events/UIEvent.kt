package com.animebiru.kerjaaja.domain.events

sealed class UIEvent private constructor() {
    object OnLoading: UIEvent()
    object OnComplete: UIEvent()
    class OnError(val errorMessage: String): UIEvent()
}
