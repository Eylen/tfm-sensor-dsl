package com.eylen.sensordsl

import com.eylen.sensordsl.handlers.CameraHandler
import com.eylen.sensordsl.handlers.LocationHandler
import com.eylen.sensordsl.handlers.MotionSensorHandler

abstract class SensorDSLScript extends Script{

    private SensorDSL sensorDSL

    abstract void scriptBody()

    def run(){
        this.sensorDSL = this.binding.sensorDSL
        String dirPath = new File(getClass().protectionDomain.codeSource.location.path).parent
        println "dirPath = $dirPath"
        sensorDSL.scriptFile = getClass().protectionDomain.codeSource.location.path
        String fileName = sensorDSL.scriptFile - new File(getClass().protectionDomain.codeSource.location.path).parent
        fileName = fileName.substring(0, fileName.indexOf(".dsl.groovy"))
        def codeFiles = new FileNameFinder().getFileNames(dirPath, fileName+".*", fileName)
        File codeFile = null
        if (codeFiles.size() > 0){
            codeFile = new File(codeFiles[0])
        }
        println codeFile
        scriptBody()
    }

    def camera(@DelegatesTo(CameraHandler) Closure closure){
        sensorDSL.camera(closure)
    }

    def accelerometer(@DelegatesTo(MotionSensorHandler) Closure closure){
        sensorDSL.accelerometer(closure)
    }

    def gyroscope(@DelegatesTo(MotionSensorHandler) Closure closure){
        sensorDSL.gyroscope(closure)
    }

    def location(@DelegatesTo(LocationHandler) Closure closure){
        sensorDSL.location(closure)
    }
}
