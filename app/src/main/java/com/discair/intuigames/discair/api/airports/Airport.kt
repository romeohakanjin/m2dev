package com.discair.intuigames.discair.api.airports

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Airport {
    @SerializedName("_id")
    @Expose
    var id: String? = null

    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("terminals")
    @Expose
    var terminals: List<Terminal>? = null

    @SerializedName("flight")
    @Expose
    var flight: Flight? = null
}
