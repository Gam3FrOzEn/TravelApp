package org.techive.travelapp.models

import java.io.Serializable

class City:Serializable {
    var id = 0
    var name: String? = null
    var description: String? = null

    constructor() {}
    constructor(name: String?, description: String?) {
        this.name = name
        this.description = description
    }

    constructor(id: Int, name: String?, description: String?) {
        this.id = id
        this.name = name
        this.description = description
    }
}