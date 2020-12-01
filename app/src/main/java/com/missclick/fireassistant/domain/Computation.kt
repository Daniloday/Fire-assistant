package com.missclick.fireassistant.domain

import com.missclick.fireassistant.data.models.FireModel
import com.missclick.fireassistant.data.models.FireReportModel
import kotlinx.coroutines.flow.flow
import kotlin.math.sqrt
import kotlin.math.tan

class Computation() {

    fun getFires(reportList: List<FireReportModel>, fireRadius : Double, searchRadius : Double, myCoordinate: Coordinate) = flow{ // list -> something else
        val fires = mutableListOf<FireModel>()
        val firesAll = mutableListOf<MutableList<FireModel>>()
        val newReportList = mutableListOf<FireReportModel>()
        for (report in reportList){
            if(getRadius(Coordinate(report.latitude,report.longitude),myCoordinate) < searchRadius) {
                newReportList.add(report)
            }
        }
        val points = getAllIntersectionPoints(reportList = newReportList)
        loop@ for (point in points) {
            for (fire in firesAll) {
                if(getRadius(point.coordinate,fire[0].coordinate) < fireRadius) {
                    fire.add(point)
                    continue@loop
                }
            }
            firesAll.add(mutableListOf(point))
        }
        for(fire in firesAll){
            fires.add(getCentre(fire))
            emit(getCentre(fire))
        }
    }

    private fun getAllIntersectionPoints(reportList: List<FireReportModel>) : MutableList<FireModel>{
        val points = mutableListOf<FireModel>()
        for (first in reportList){
            for (second in reportList){
                if (second == first) continue
                getIntersectionPoint(
                        firstCoordinate = Coordinate(x = first.latitude, y = first.longitude),
                        secondCoordinate = Coordinate(x = second.latitude, y = second.longitude),
                        firstAzimuth = first.azimuth,
                        secondAzimuth = second.azimuth
                )?.let { points.add(FireModel(coordinate = it, reports = listOf(first,second)))}
            }
        }
        return points
    }

    private fun getIntersectionPoint(firstCoordinate : Coordinate, secondCoordinate: Coordinate, firstAzimuth : Float, secondAzimuth : Float) : Coordinate? {
        val firstTan = tan(firstAzimuth.toDouble())
        val secondTan = tan(secondAzimuth.toDouble())
        val firstB = firstCoordinate.y - firstTan * firstCoordinate.x
        val secondB = secondCoordinate.y - secondTan * secondCoordinate.x
        val x = (firstB - secondB)/(firstTan - secondTan)
        val y = firstTan * firstCoordinate.x + firstB
        if (firstAzimuth > 0 && firstAzimuth < 90) if (x < firstCoordinate.x || y < firstCoordinate.y) return null
        if (firstAzimuth > 90 && firstAzimuth < 180) if (x < firstCoordinate.x || y > firstCoordinate.y) return null
        if (firstAzimuth < 0 && firstAzimuth > -90) if (x > firstCoordinate.x || y < firstCoordinate.y) return null
        if (firstAzimuth < -90 && firstAzimuth > -180) if (x > firstCoordinate.x || y > firstCoordinate.y) return null
        if (secondAzimuth > 0 && secondAzimuth < 90) if (x < secondCoordinate.x || y < secondCoordinate.y) return null
        if (secondAzimuth > 90 && secondAzimuth < 180) if (x < secondCoordinate.x || y > secondCoordinate.y) return null
        if (secondAzimuth < 0 && secondAzimuth > -90) if (x > secondCoordinate.x || y < secondCoordinate.y) return null
        if (secondAzimuth < -90 && secondAzimuth > -180) if (x > secondCoordinate.x || y > secondCoordinate.y) return null
        return Coordinate(x = x, y = y)
    }

    private fun getRadius(firstCoordinate: Coordinate, secondCoordinate: Coordinate) : Double{
        val deltaX = firstCoordinate.x - secondCoordinate.x
        val deltaY = firstCoordinate.y - secondCoordinate.y
        return sqrt(((deltaX * deltaX) + (deltaY * deltaY)))
    }

    private fun getCentre(coordinates: List<FireModel>) : FireModel{
        val reports = mutableListOf<FireReportModel>()
        var sumX = 0.0
        var sumY = 0.0
        for(coordinate in coordinates){
            sumX += coordinate.coordinate.x
            sumY += coordinate.coordinate.y
        }
        sumX /= coordinates.size
        sumY /= coordinates.size
        for (report in coordinates)
            reports.add(report.reports[0])
        return FireModel(coordinate = Coordinate(x = sumX,y = sumY),reports = reports)
    }

}