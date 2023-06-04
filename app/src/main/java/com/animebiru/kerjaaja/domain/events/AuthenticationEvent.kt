package com.animebiru.kerjaaja.domain.events

sealed class AuthenticationEvent {
    object OnLoading: AuthenticationEvent()
    class OnError(val errorMessage: String): AuthenticationEvent()
    object OnLogin: AuthenticationEvent()
    object OnLogout: AuthenticationEvent()
    class OnRegisterSuccess(val username: String): AuthenticationEvent()
}