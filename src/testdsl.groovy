import com.eylen.sensordsl.SensorDSL

SensorDSL.with {
    camera {
        take picture store_in "path" on {
            success "mySuccessCallback"
            cancel "myCancelCallback"
            error "myErrorCallback"
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

