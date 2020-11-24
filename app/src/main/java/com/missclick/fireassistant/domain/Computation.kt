package com.missclick.fireassistant.domain

import com.missclick.fireassistant.data.models.FireReportModel
import kotlin.math.sqrt
import kotlin.math.tan

class Computation() {

    fun getFires(reportList: List<FireReportModel>) { // list -> something else
        val points = getAllIntersectionPoints(reportList = reportList)
    }

    private fun getAllIntersectionPoints(reportList: List<FireReportModel>) : MutableList<Coordinate>{
        val points = mutableListOf<Coordinate>()
        for (first in reportList){
            for (second in reportList){
                if (second == first) continue
                getIntersectionPoint(
                        firstCoordinate = Coordinate(x = first.latitude, y = first.longitude),
                        secondCoordinate = Coordinate(x = second.latitude, y = second.longitude),
                        firstAzimuth = first.azimuth,
                        secondAzimuth = second.azimuth
                )
            }
        }
        return points
    }

    private fun getIntersectionPoint(firstCoordinate : Coordinate, secondCoordinate: Coordinate, firstAzimuth : Float, secondAzimuth : Float) : Coordinate{
        val firstTan = tan(firstAzimuth.toDouble())
        val secondTan = tan(secondAzimuth.toDouble())
        val firstB = firstCoordinate.y - firstTan * firstCoordinate.x
        val secondB = secondCoordinate.y - secondTan * secondCoordinate.x
        val x = (firstB - secondB)/(firstTan - secondTan)
        val y = firstTan * firstCoordinate.x + firstB
        return Coordinate(x = x, y = y)
    }

    private fun getRadius(firstCoordinate: Coordinate, secondCoordinate: Coordinate) : Double{
        val deltaX = firstCoordinate.x - secondCoordinate.x
        val deltaY = firstCoordinate.y - secondCoordinate.y
        return sqrt(((deltaX * deltaX) + (deltaY * deltaY)))
    }

}