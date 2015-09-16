package com.eylen.sensordsl.handlers

import com.eylen.sensordsl.Permission
import com.eylen.sensordsl.enums.LocationPriorityType
import com.eylen.sensordsl.enums.TrackType

class LocationHandler  extends AbstractHandler{
    //Silent words
    String last_location = "last_location"
    TrackType automatically = TrackType.AUTO
    TrackType manually = TrackType.MANUAL

    LastKnownLocationHandler lastKnownLocationHandler
    LocationTrackingHandler locationTrackingHandler
    List<Permission> permissionList

    public LocationHandler(){
        super()
        this.permissionList = new ArrayList<>()
        permissionList << Permission.COARSE_LOCATION
    }

    LastKnownLocationHandler get(String last_location){
        lastKnownLocationHandler = new LastKnownLocationHandler()
        lastKnownLocationHandler
    }

    class LastKnownLocationHandler {
        String callbackMethod

        void call(String callbackMethod) {
            this.callbackMethod= callbackMethod
        }
    }

    LocationTrackingHandler track(TrackType trackType){
        locationTrackingHandler = new LocationTrackingHandler(trackType)
        locationTrackingHandler
    }

    class LocationTrackingHandler {
        //Silent words
        LocationPriorityType balanced = LocationPriorityType.BALANCED
        LocationPriorityType accurate = LocationPriorityType.HIGH_ACCURACY
        LocationPriorityType low_power = LocationPriorityType.LOW_POWER
        LocationPriorityType power_safe = LocationPriorityType.NO_POWER

        TrackType trackingType
        String callbackMethod
        String startMethod
        String stopMethod
        LocationTrackingProperties locationTrackingProperties

        public LocationTrackingHandler(TrackType trackType){
            this.trackingType = trackType
        }

        LocationTrackingHandler with(@DelegatesTo(LocationTrackingProperties)Closure closure){
            locationTrackingProperties = new LocationTrackingProperties()
            def code = closure.rehydrate(locationTrackingProperties, null, null)
            code.resolveStrategy = Closure.DELEGATE_ONLY
            code.call()
            this
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

    class LocationTrackingProperties {
        //TODO añadir seconds/minutes etc
        int interval
        int fastestInterval
        LocationPriorityType priorityType

        LocationTrackingProperties each(int interval){
            this.interval = interval
            this
        }

        LocationTrackingProperties minimum(int fastestInterval){
            this.fastestInterval = fastestInterval
            this
        }

        LocationTrackingProperties accuracy(LocationPriorityType accuracy){
            this.priorityType = accuracy
            if (accuracy == LocationPriorityType.HIGH_ACCURACY)
                permissionList[0] = Permission.FINE_LOCATION
            else
                permissionList[0] = Permission.COARSE_LOCATION
            this
        }

    }
}
