package com.eylen.sensordsl

import com.eylen.sensordsl.enums.SensorType

import com.eylen.sensordsl.handlers.CameraHandler
import com.eylen.sensordsl.handlers.LocationHandler
import com.eylen.sensordsl.handlers.MotionSensorHandler

class SensorDSL {
    List<CameraHandler> cameraHandlers
    List<MotionSensorHandler> motionSensorHandlers
    List<LocationHandler> locationHandlers

    public SensorDSL(){
        cameraHandlers = new ArrayList<>()
        motionSensorHandlers = new ArrayList<>()
        locationHandlers = new ArrayList<>()
    }

    public static SensorDSL with(@DelegatesTo(SensorDSL) Closure closure){
        SensorDSL sensorDSL = new SensorDSL()
        def code = closure.rehydrate(sensorDSL, null, null)
        code.resolveStrategy = Closure.DELEGATE_ONLY
        code()
        sensorDSL
    }

    def camera(@DelegatesTo(CameraHandler) Closure closure){
        CameraHandler delegate = new CameraHandler()
        def code = closure.rehydrate(delegate, null, null)
        code.resolveStrategy = Closure.DELEGATE_ONLY
        code()
        cameraHandlers << delegate
    }

    def accelerometer(@DelegatesTo(MotionSensorHandler) Closure closure){
        MotionSensorHandler delegate = new MotionSensorHandler(SensorType.ACCELEROMETER)
        def code = closure.rehydrate(delegate, null, null)
        code.resolveStrategy = Closure.DELEGATE_ONLY
        code()
        motionSensorHandlers << delegate
    }

    def gyroscope(@DelegatesTo(MotionSensorHandler) Closure closure){
        MotionSensorHandler delegate = new MotionSensorHandler(SensorType.GYROSCOPE)
        def code = closure.rehydrate(delegate, null, null)
        code.resolveStrategy = Closure.DELEGATE_ONLY
        code()
        motionSensorHandlers << delegate
    }

    def location(@DelegatesTo(LocationHandler) Closure closure){
        LocationHandler delegate = new LocationHandler()
        def code = closure.rehydrate(delegate, null, null)
        code.resolveStrategy = Closure.DELEGATE_ONLY
        code()
        locationHandlers << delegate
    }
}
