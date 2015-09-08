import com.eylen.sensordsl.SensorDSL

SensorDSL.with {
    camera {
        take picture store_in "path" on {
            success "mySuccessCallback"
            cancel "myCancelCallback"
            error "myErrorCallback"
        }
        take video store_in "videoPath" set_properties {
            quality high
            limiting {
                duration to: 10 size to: 20
            }
        }
    }

    accelerometer {
        track always on_change "myAccelerometerChangeMethod"
    }

    gyroscope {
        track always on_change "myGyroscopeChangeMethod"
    }

    location {
        get last_location store_in "myVar"

        track manually calling "myMethod" start_method "myMethod" stop_method "algo"
    }
}

