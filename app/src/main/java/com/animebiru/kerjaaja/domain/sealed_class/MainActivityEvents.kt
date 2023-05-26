package com.animebiru.kerjaaja.domain.sealed_class

sealed class MainActivityEvents {
    object ShowLoading: MainActivityEvents()
    object OnLogout: MainActivityEvents()
    class OnSuccess(val data: Any): MainActivityEvents()
    class OnError(val throwable: Throwable): MainActivityEvents()
}
