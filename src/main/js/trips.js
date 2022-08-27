import React from "react";
import { TableComponent } from "./table";
import {useEffect, useState} from "react";
import {Fetch} from "./networkUtils";
import {Text, TouchableOpacity, View} from "react-native-web";
import {globalStyle} from "./globalStyle";
import {root} from "./entryPoint";
import {TripDetails} from "./tripDetails";

const Trips = () => {
    const [trips, setTrips] = useState({});

    useEffect(Fetch('/trips/list', setTrips), []);

    return (
        <View style={globalStyle.container}>
            <TouchableOpacity onPress={() => {window.open("/", "_self")}} style={[globalStyle.button, globalStyle.rightSideButton]}>
                <Text>Go back to Home</Text>
            </TouchableOpacity>
            <Text style={globalStyle.title}>Trips</Text>
            <TableComponent nameColumns={["DepartureDate", "ReturnDate", "DepartureCity", "ArrivalCity"]} list={trips} propExtractor={propExtractor} onPress={pressedTrip}/>
        </View>
    );

}


const pressedTrip = (trip) => {
    root.render(<TripDetails trip={trip}/>);
}

const propExtractor = (trip) => {
    return [trip.departureDate, trip.returnDate, trip.departureCity, trip.arrivalCity];
}


export { Trips };