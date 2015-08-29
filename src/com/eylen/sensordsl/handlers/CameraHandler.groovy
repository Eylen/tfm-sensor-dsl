package com.eylen.sensordsl.handlers

import com.eylen.sensordsl.enums.MediaType

class CameraHandler {
    //Silent words
    MediaType picture = MediaType.PICTURE
    MediaType video = MediaType.VIDEO

    String methodName = "cameraDSLHandler"
    String mediaType
    String path

    CameraCallbackHandler callbackHandler

    public CameraHandler(){
        callbackHandler = new CameraCallbackHandler()
    }

    /*def take(String mediaType){
        [store_in: {path->
            this.path = path
            [on: { Closure closure->
                def code = closure.rehydrate(callbackHandler, null, null)
                code.resolveStrategy = Closure.DELEGATE_ONLY
                code.call()
            }]
        }]
    }*/

    CameraHandler with_name(String methodName){
        this.methodName = methodName
        this
    }

    CameraHandler take(String mediaType) {
        if (!MediaType.values().collect{it.toString()}.contains(mediaType.toUpperCase())){
            throw new Exception("Only PICTURE or VIDEO can be taken")
        }
        this.mediaType = mediaType
        this
    }

    CameraHandler store_in(String path){
        this.path = path
        this
    }

    void on(@DelegatesTo(CameraCallbackHandler) Closure closure){
        def code = closure.rehydrate(callbackHandler, null, null)
        code.resolveStrategy = Closure.DELEGATE_ONLY
        code.call()
    }

    class CameraCallbackHandler {
        String successCallback
        String errorCallback
        String cancelCallback

        CameraCallbackHandler success(String methodName){
            this.successCallback = methodName
            this
        }

        CameraCallbackHandler cancel(String methodName){
            this.cancelCallback = methodName
            this
        }

        CameraCallbackHandler error(String methodName){
            this.errorCallback = methodName
            this
        }
    }
}