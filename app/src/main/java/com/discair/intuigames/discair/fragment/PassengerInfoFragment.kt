package com.discair.intuigames.discair

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import com.discair.intuigames.discair.api.RetrofitClient
import com.discair.intuigames.discair.api.StackServiceInterface
import com.discair.intuigames.discair.api.airports.Airport
import com.discair.intuigames.discair.api.airports.Incident
import com.discair.intuigames.discair.api.airports.Passenger
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * @author SLI
 */
class PassengerInfoFragment : Fragment() {
    /**
     * Variables
     */
    private lateinit var fragment: View
    private var referenceNumber: Int=0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        fragment = inflater.inflate(R.layout.fragment_personal_info, container, false)

        //Get intent extra
        val bundle = this.arguments
        if(bundle != null){
            referenceNumber = bundle.getInt("referenceNumber")
            this.getPassengeInfoByRefNumber(referenceNumber)
        }

        return fragment
    }

    /**
     * Récupère la liste des passagers grâce au numéro de vol
     * Affiche les passagers dans la vue
     */
    fun getPassengeInfoByRefNumber(referenceNum: Int){
        val mService = RetrofitClient.getConnection()!!.create(StackServiceInterface::class.java)
        mService.getPassengerByReferenceNumber(referenceNum).enqueue(object : Callback<List<Airport>> {
            override fun onResponse(call: Call<List<Airport>>, response: Response<List<Airport>>) {
                if (response.isSuccessful()) {
                    if (response.body()!!.size == 1){
                        val airport = response.body()!![0]
                        val flight = airport.flight
                        if(flight!!.passenger!!.size >= 1){
                            for(pass in flight!!.passenger as List<Passenger>){
                                if(pass.referenceNumber!!.equals(referenceNum)){
                                    val passengerInfoLayout = fragment.findViewById(R.id.PassengerInfo) as TableLayout
                                    val layoutParams = TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT)
                                    /*if(pass.luggage != null){
                                        val luggage = pass.luggage
                                    }
                                    if(pass.incidents != null){
                                        val incidents = pass.incidents
                                    }*/


                                }
                            }

                        }
                    }
                }
            }

            override fun onFailure(call: Call<List<Airport>>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }
}