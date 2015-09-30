package com.eylen.sensordsl.handlers

import com.eylen.sensordsl.Permission
import com.eylen.sensordsl.SensorDSL
import com.eylen.sensordsl.enums.MediaType
import groovy.time.TimeCategory

class CameraHandler extends AbstractHandler{
    //Silent words
    MediaType picture = MediaType.PICTURE
    MediaType video = MediaType.VIDEO

    String methodName
    MediaType mediaType
    String path

    CameraCallbackHandler callbackHandler
    VideoPropertiesHandler videoProperties

    List<Permission> permissionList

    public CameraHandler(){
        callbackHandler = new CameraCallbackHandler()
        permissionList = new ArrayList<>()
    }

    CameraHandler with_name(String methodName){
        this.methodName = methodName
        this
    }

    CameraHandler take(MediaType mediaType) {
        this.mediaType = mediaType
        if (mediaType == video){
            this.videoProperties = new VideoPropertiesHandler()
            permissionList << Permission.VIDEO
        } else {
            permissionList << Permission.CAMERA
        }
        this
    }

    CameraHandler set_properties (@DelegatesTo(VideoPropertiesHandler) Closure closure){
        if (this.mediaType == video) {
            this.videoProperties = new VideoPropertiesHandler()
            def code = closure.rehydrate(videoProperties, null, null)
            code.resolveStrategy = Closure.DELEGATE_ONLY
            code.call()
        }
        this
    }

    CameraHandler store_in(String path){
        this.path = path
        permissionList << Permission.STORE
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

    class VideoPropertiesHandler {
        //TODO sobrescribir integer para que admita seconds/minutes etc y bytes, kbytes, megabyte
        //Silent words
        String to = "to"
        int high = 1
        int low = 0

        public VideoPropertiesHandler(){
            qualityValue = null
            durationLimit = null
            sizeLimit = null
        }

        Integer qualityValue
        Integer durationLimit
        Integer sizeLimit

        VideoPropertiesHandler quality(int quality){
            this.qualityValue = quality
            this
        }

        VideoPropertiesHandler limiting(@DelegatesTo(VideoLimitHandler) Closure closure){
            VideoLimitHandler handler = new VideoLimitHandler()
            def code = closure.rehydrate(handler, null, null)
            code.resolveStrategy = Closure.DELEGATE_ONLY
            code.call()
            this
        }

        class VideoLimitHandler {
            VideoLimitHandler duration(int seconds){
                durationLimit = seconds
                this
            }

            VideoLimitHandler duration(Map<String, Integer> args){
                if (args.size() > 0) {
                    durationLimit = args[args.keySet()[0]]
                }
                this
            }

            VideoLimitHandler size(int bytes){
                sizeLimit = bytes
                this
            }

            VideoLimitHandler size(Map<String, Integer> args){
                if (args.size() > 0) {
                    sizeLimit = args[args.keySet()[0]]
                }
                this
            }
        }


    }
}