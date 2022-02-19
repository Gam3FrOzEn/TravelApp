package org.techive.travelapp.models

class Landmark {
    var id = 0
    var name: String? = null
    var description: String? = null
    var city: String? = null

    constructor() {}
    constructor(name: String?, description: String?, city: String?) {
        this.name = name
        this.description = description
        this.city = city
    }

    constructor(id: Int, name: String?, description: String?, city: String?) {
        this.id = id
        this.name = name
        this.description = description
        this.city = city
    }
}