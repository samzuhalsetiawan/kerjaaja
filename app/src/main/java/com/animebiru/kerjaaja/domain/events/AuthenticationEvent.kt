package com.animebiru.kerjaaja.domain.events

sealed class AuthenticationEvent {
    object OnLoading: AuthenticationEvent()
    class OnError(val errorMessage: String): AuthenticationEvent()
    object OnLogin: AuthenticationEvent()
    object OnLogout: AuthenticationEvent()
    class OnRegisterSuccess(val username: String): AuthenticationEvent()

    /**
     * [OnTransactionSuccess] should be triggered after [OnError],[OnLogin],[OnLogout],[OnRegisterSuccess] done with its job
     * this event can be fired using method [AuthenticationViewModel::notifyTransactionSuccess] called
     */
    object OnTransactionSuccess: AuthenticationEvent()
}