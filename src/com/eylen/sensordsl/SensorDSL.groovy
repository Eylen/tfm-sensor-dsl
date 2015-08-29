package com.eylen.sensordsl

import com.eylen.sensordsl.enums.SensorType

import com.eylen.sensordsl.handlers.CameraHandler
import com.eylen.sensordsl.handlers.LocationHandler
import com.eylen.sensordsl.handlers.MovementSensorHandler

class SensorDSL {
    def static camera(@DelegatesTo(CameraHandler) Closure closure){
        CameraHandler delegate = new CameraHandler()
        def code = closure.rehydrate(delegate, null, null)
        code.resolveStrategy = Closure.DELEGATE_ONLY
        code()
    }

    def static accelerometer(@DelegatesTo(MovementSensorHandler) Closure closure){
        MovementSensorHandler delegate = new MovementSensorHandler(SensorType.ACCELEROMETER)
        def code = closure.rehydrate(delegate, null, null)
        code.resolveStrategy = Closure.DELEGATE_ONLY
        code()
    }

    def static gyroscope(@DelegatesTo(MovementSensorHandler) Closure closure){
        MovementSensorHandler delegate = new MovementSensorHandler(SensorType.GYROSCOPE)
        def code = closure.rehydrate(delegate, null, null)
        code.resolveStrategy = Closure.DELEGATE_ONLY
        code()
    }

    def static location(@DelegatesTo(LocationHandler) Closure closure){
        LocationHandler delegate = new LocationHandler()
        def code = closure.rehydrate(delegate, null, null)
        code.resolveStrategy = Closure.DELEGATE_ONLY
        code()
    }
}
