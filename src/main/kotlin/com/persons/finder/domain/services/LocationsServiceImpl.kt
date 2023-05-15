package com.persons.finder.domain.services

import com.persons.finder.data.Location
import org.springframework.stereotype.Service

@Service
class LocationsServiceImpl : LocationsService {

    private val locations = mutableListOf<Location>()

    override fun addLocation(location: Location) {
        locations.add(location)
    }

    override fun removeLocation(locationReferenceId: Long) {
        locations.removeIf { it.referenceId == locationReferenceId }
    }

    override fun findAround(latitude: Double, longitude: Double, radiusInKm: Double): List<Location> {
        return locations.filter {
            val distance = distanceBetween(latitude, longitude, it.latitude, it.longitude)
            distance <= radiusInKm
        }
    }

    private fun distanceBetween(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
        val earthRadius = 6371 // km
        val dLat = Math.toRadians(lat2 - lat1)
        val dLon = Math.toRadians(lon2 - lon1)
        val a = (Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(dLon / 2) * Math.sin(dLon / 2))
        val c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))
        return earthRadius * c
    }
}