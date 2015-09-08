package com.eylen.sensordsl.handlers

import com.eylen.sensordsl.enums.SensorType

class MotionSensorHandler extends AbstractHandler{
    //Silent words
    boolean always = true
    boolean foreground = false

    SensorType sensorType
    boolean trackBackground = false
    String callbackName

    public MotionSensorHandler(SensorType sensorType){
        this.sensorType = sensorType
    }

    MotionSensorHandler track(boolean whenTrack) {
        this.trackBackground = whenTrack
        this
    }

    def on_change(String methodName){
        this.callbackName = methodName
    }
}
