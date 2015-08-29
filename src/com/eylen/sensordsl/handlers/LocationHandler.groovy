package com.eylen.sensordsl.handlers

import com.eylen.sensordsl.enums.TrackType

class LocationHandler {
    //Silent words
    String last_location = "last_location"
    TrackType automatically = TrackType.AUTO
    TrackType manually = TrackType.MANUAL

    LastKnownLocationHandler lastKnownLocationHandler
    LocationTrackingHandler locationTrackingHandler

    LastKnownLocationHandler get(String last_location){
        lastKnownLocationHandler = new LastKnownLocationHandler()
        lastKnownLocationHandler
    }

    class LastKnownLocationHandler {
        String varName

        void store_in(String varName) {
            this.varName = varName
        }
    }

    LocationTrackingHandler track(TrackType trackType){
        locationTrackingHandler = new LocationTrackingHandler(trackType)
        locationTrackingHandler
    }

    class LocationTrackingHandler {
        TrackType trackingType
        String callbackMethod
        String startMethod = "startTrackingLocation"
        String stopMethod = "stopTrackingLocation"

        public LocationTrackingHandler(TrackType trackType){
            this.trackingType = trackType
        }

        LocationTrackingHandler calling(String methodName) {
            this.callbackMethod = methodName
            this
        }

        LocationTrackingHandler start_method(String methodName) {
            this.startMethod = methodName
            this
        }

        LocationTrackingHandler stop_method(String methodName) {
            this.stopMethod = methodName
            this
        }
    }
}
