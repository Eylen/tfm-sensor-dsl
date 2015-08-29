package com.eylen.sensordsl.handlers

import com.eylen.sensordsl.enums.SensorType

/**
 * Created by Saioa on 29/08/2015.
 */
class MovementSensorHandler {
    //Silent words
    boolean always = true
    boolean foreground = false

    SensorType sensorType
    boolean trackBackground = false
    String callbackName

    public MovementSensorHandler(SensorType sensorType){
        this.sensorType = sensorType
    }

    MovementSensorHandler track(boolean whenTrack) {
        this.trackBackground = whenTrack
        this
    }

    def on_change(String methodName){
        this.callbackName = methodName
    }
}
