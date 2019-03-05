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
        fragment = inflater.inflate(R.layout.fragment_passenger_info, container, false)

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
                                if(pass.referenceNumber!!.toInt().equals(referenceNum)){
                                    val passengerInfoLayout = fragment.findViewById(R.id.PassengerInfo) as TableLayout
                                    val layoutParams = TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT)

                                    if(pass.luggage!!.number != null){
                                        val luggageRow = TableRow(fragment.context)
                                        luggageRow.id = TableRow.generateViewId()
                                        luggageRow.layoutParams = layoutParams

                                        val luggageTitle = TextView(fragment.context)
                                        luggageTitle.text = "Baggages : "
                                        luggageTitle.gravity = Gravity.CENTER;
                                        luggageRow.addView(luggageTitle)

                                        val luggageNumber = TextView(fragment.context)
                                        luggageNumber.tag = "luggageNumber"
                                        luggageNumber.text = pass.luggage!!.number.toString()
                                        luggageNumber.gravity = Gravity.CENTER;
                                        luggageRow.addView(luggageNumber)
                                    }
                                    if(pass.incident != null){

                                        val luggageRow = TableRow(fragment.context)
                                        luggageRow.id = TableRow.generateViewId()
                                        luggageRow.layoutParams = layoutParams

                                        val luggageTitle = TextView(fragment.context)
                                        luggageTitle.text = "Incident(s) : "
                                        luggageTitle.gravity = Gravity.CENTER;
                                        luggageRow.addView(luggageTitle)

                                        for(inc in pass.incident as List<Incident>){
                                            System.out.println(inc.type)
                                            System.out.println(inc.description)
                                            val luggageTypeTitle = TextView(fragment.context)
                                            luggageTypeTitle.text = "Type de l'incident : "
                                            luggageTypeTitle.gravity = Gravity.CENTER;
                                            luggageRow.addView(luggageTypeTitle)

                                            val incidentType = TextView(fragment.context)
                                            incidentType.tag = "incidentType"
                                            incidentType.text = inc.type
                                            incidentType.gravity = Gravity.CENTER;
                                            luggageRow.addView(incidentType)

                                            val luggageDescriptionTitle = TextView(fragment.context)
                                            luggageDescriptionTitle.text = "Type de l'incident : "
                                            luggageDescriptionTitle.gravity = Gravity.CENTER;
                                            luggageRow.addView(luggageDescriptionTitle)

                                            val incidentDescription = TextView(fragment.context)
                                            incidentDescription.tag = "incidentDescription"
                                            incidentDescription.text = inc.description
                                            incidentDescription.gravity = Gravity.CENTER;
                                            luggageRow.addView(incidentDescription)
                                        }
                                    }
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