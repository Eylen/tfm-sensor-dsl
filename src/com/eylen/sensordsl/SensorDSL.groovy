package com.eylen.sensordsl

import com.eylen.sensordsl.enums.SensorType

import com.eylen.sensordsl.handlers.CameraHandler
import com.eylen.sensordsl.handlers.LocationHandler
import com.eylen.sensordsl.handlers.MotionSensorHandler

class SensorDSL {
    List<CameraHandler> cameraHandlers
    List<MotionSensorHandler> motionSensorHandlers
    List<LocationHandler> locationHandlers

    String scriptFile
    String codeFile
    String packageName

    Set<Permission> permissionList

    public SensorDSL(){
        cameraHandlers = new ArrayList<>()
        motionSensorHandlers = new ArrayList<>()
        locationHandlers = new ArrayList<>()
        findCodeFile()
        this.permissionList = new ArrayList<>()
    }


    private void findCodeFile(){
        extractPackageName()
    }

    private void extractPackageName(){

    }

    public static SensorDSL with(@DelegatesTo(SensorDSL) Closure closure){
        def code = closure.rehydrate(this, null, null)
        code.resolveStrategy = Closure.DELEGATE_ONLY
        code()
        this
    }

    def camera(@DelegatesTo(CameraHandler) Closure closure){
        CameraHandler delegate = new CameraHandler()
        def code = closure.rehydrate(delegate, null, null)
        code.resolveStrategy = Closure.DELEGATE_ONLY
        code()
        permissionList.addAll delegate.permissionList
        cameraHandlers << delegate
        this
    }

    def accelerometer(@DelegatesTo(MotionSensorHandler) Closure closure){
        MotionSensorHandler delegate = new MotionSensorHandler(SensorType.ACCELEROMETER)
        def code = closure.rehydrate(delegate, null, null)
        code.resolveStrategy = Closure.DELEGATE_ONLY
        code()
        motionSensorHandlers << delegate
        this
    }

    def gyroscope(@DelegatesTo(MotionSensorHandler) Closure closure){
        MotionSensorHandler delegate = new MotionSensorHandler(SensorType.GYROSCOPE)
        def code = closure.rehydrate(delegate, null, null)
        code.resolveStrategy = Closure.DELEGATE_ONLY
        code()
        motionSensorHandlers << delegate
        this
    }

    def location(@DelegatesTo(LocationHandler) Closure closure){
        LocationHandler delegate = new LocationHandler()
        def code = closure.rehydrate(delegate, null, null)
        code.resolveStrategy = Closure.DELEGATE_ONLY
        code()
        permissionList.addAll delegate.permissionList
        locationHandlers << delegate
        this
    }
}
