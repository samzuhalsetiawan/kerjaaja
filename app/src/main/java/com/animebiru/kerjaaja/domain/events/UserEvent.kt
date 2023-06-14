package com.animebiru.kerjaaja.domain.events

import java.io.File

sealed class UserEvent {
    object OnLoading: UserEvent()
    class OnError(val errorMessage: String): UserEvent()
    object OnLogin: UserEvent()
    object OnLogout: UserEvent()
    class OnRegisterSuccess(val username: String): UserEvent()
    object OnIdle: UserEvent()
    class OnChangePhotoProfileSuccess(val photo: File): UserEvent()
}