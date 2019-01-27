package com.shhatrat.bandapp.iqdroid.enums

sealed class InitResponse{
    object OnSdkReady: InitResponse()

    sealed class OnInitializeError: InitResponse() {
        object OnSdkShutDown: OnInitializeError()
        object GCM_NOT_INSTALLED : OnInitializeError()
        object GCM_UPGRADE_NEEDED : OnInitializeError()
        object SERVICE_ERROR : OnInitializeError()
    }

}

class IQError(val error: InitResponse.OnInitializeError): Error()