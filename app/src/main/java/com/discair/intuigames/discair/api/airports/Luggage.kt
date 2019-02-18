package com.discair.intuigames.discair.api.airports

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Luggage {
    @SerializedName("_id")
    @Expose
    var id: String? = null

    @SerializedName("number")
    @Expose
    var number: String? = null

}