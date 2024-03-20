package com.sd.laborator.pojo

data class Person(
    var id: Int = 0,
    var loginID: String = "",
    var password: String = "",
    var lastName: String = "",
    var firstName: String = "",
    var bani: Double= 0.0,
    var intretinere: Double = 0.0,
    var mancare: Double = 0.0,
    var personale: Double = 0.0
)