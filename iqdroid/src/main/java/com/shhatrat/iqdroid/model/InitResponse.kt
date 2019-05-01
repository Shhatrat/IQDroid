package com.shhatrat.iqdroid.model

sealed class InitResponse{
    object OnSdkReady: InitResponse()

    sealed class OnInitializeError: InitResponse() {
        object OnSdkShutDown: OnInitializeError()
        object GCM_NOT_INSTALLED : OnInitializeError()
        object GCM_UPGRADE_NEEDED : OnInitializeError()
        object SERVICE_ERROR : OnInitializeError()
        object OnApplicationNotInstalled: OnInitializeError()
    }

    override fun toString(): String {
        return when (this) {
            OnSdkReady -> "OnSdkReady"
            OnInitializeError.GCM_NOT_INSTALLED -> "GCM_NOT_INSTALLED"
            OnInitializeError.GCM_UPGRADE_NEEDED -> "GCM_UPGRADE_NEEDED"
            OnInitializeError.SERVICE_ERROR -> "SERVICE_ERROR"
            OnInitializeError.OnApplicationNotInstalled -> "OnApplicationNotInstalled"
            else -> super.toString()
        }
    }
}

class IQError(val error: InitResponse.OnInitializeError): Error()