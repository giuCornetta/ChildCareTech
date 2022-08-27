import React from "react";
import { TableComponent } from "./table";
import {useEffect, useState} from "react";
import {Fetch, PostRequest} from "./networkUtils";
import {Alert, Modal, Pressable, ScrollView, StyleSheet, Text, TouchableOpacity, View} from "react-native-web";
import {globalStyle} from "./globalStyle";
import {root} from "./entryPoint";
import {TripDetails} from "./tripDetails";

const Trips = () => {
    const [trips, setTrips] = useState({});
    const [modalVisible, setModalVisible] = useState(false);
    const [refresh, setRefresh] = useState(false);
    const [csrfToken, setCsrfToken] = useState({});

    useEffect(Fetch('/trips/list', setTrips), [refresh]);
    useEffect(Fetch('/csrf', setCsrfToken), []);

    const toggleRefresh= () => {
        setRefresh(!refresh);
    }

    return (
        <View style={globalStyle.container}>
            <TouchableOpacity onPress={() => {window.open("/", "_self")}} style={[globalStyle.button, globalStyle.rightSideButton]}>
                <Text>Go back to Home</Text>
            </TouchableOpacity>
            <Text style={globalStyle.title}>Trips</Text>
            <TableComponent nameColumns={["DepartureDate", "ReturnDate", "DepartureCity", "ArrivalCity"]} list={trips} propExtractor={propExtractor} onPress={pressedTrip}/>
            <TouchableOpacity onPress={() => setModalVisible(true)} style={[globalStyle.button, globalStyle.centerButton]}>
                <Text>Create new Trip</Text>
            </TouchableOpacity>
            <Modal
                propagateSwipe={true}
                animationType="slide"
                transparent={true}
                visible={modalVisible}
                onRequestClose={() => {
                    Alert.alert("Modal has been closed.");
                    setModalVisible(!modalVisible);
                }}
            >
                <View style={globalStyle.centeredView}>
                    <View style={globalStyle.modalView}>
                        <ScrollView>
                            <AddTripForm csrfToken={csrfToken} refresh={toggleRefresh}/>
                            <Pressable
                                style={[styles.button]}
                                onPress={() => setModalVisible(!modalVisible)}>
                                <Text style={styles.textStyle}>Hide Modal</Text>
                            </Pressable>
                        </ScrollView>
                    </View>
                </View>
            </Modal>
        </View>
    );

}

const AddTripForm = ({csrfToken, refresh}) => {
    let initialFormData = Object.freeze({
        departureDate: "",
        returnDate: "",
        departureCity: "Milano, Via Leonardo Da Vinci, 32",
        arrivalCity:"",
    })
    let today = new Date();
    let minDate = new Date(today.getDate() + 1);
    const [formData, updateFormData] = useState(initialFormData);


    const handleChange = (e) => {
        updateFormData({
            ...formData,
            [e.target.name]: e.target.value
        });
    }

    const handleDateChange = (e) => {
        let date = new Date(e.target.value);
        let dd=date.getDate(), mm=date.getMonth()+1;
        if (dd < 10) dd = '0' + dd;
        if (mm < 10) mm = '0' + mm;
        updateFormData({
            ...formData,
            [e.target.name]: dd+ '/' + mm + '/' + date.getFullYear(),
        })
    }

    const handleSubmit = (e) => {
        e.preventDefault()
        console.log(formData);
        PostRequest("/trips/create", formData, () => {}, csrfToken.token, refresh)();
        document.getElementById("trip-form").reset();
        updateFormData(initialFormData);
    };

    return (<View style={globalStyle.container}>
        <form onSubmit={handleSubmit} id="trip-form">
            <label>
                From:
                <input name="departureDate" type="date" min={minDate.toISOString().substring(0, 10)} required={true} onChange={handleDateChange}/>
            </label>
            <label>
                To:
                <input name="returnDate" type="date" min={minDate.toISOString().substring(0, 10)} required={true} onChange={handleDateChange}/>
            </label><br/>
            <label>
                Departure City:
                <input name="departureCity" type="text" value="Milano, Via Leonardo Da Vinci, 32" required={true} onChange={handleChange}/>
            </label>
            <label>
                Arrival City:
                <input name="arrivalCity" type="text" required={true} onChange={handleChange}/>
            </label><br />
            <input type="submit" value="Submit"/>

        </form>
    </View>)
}


const pressedTrip = (trip) => {
    root.render(<TripDetails trip={trip}/>);
}

const propExtractor = (trip) => {
    return [trip.departureDate, trip.returnDate, trip.departureCity, trip.arrivalCity];
}

const styles = StyleSheet.create({
    button: {
        borderRadius: 10,
        padding: 10,
        elevation: 2,
        backgroundColor: "#2196F3",
    },
    textStyle: {
        color: "white",
    },

});


export { Trips };