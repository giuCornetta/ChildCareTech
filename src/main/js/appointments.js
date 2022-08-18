import React, {useEffect, useState} from "react";
import {globalStyle} from "./globalStyle";
import {View, Text, TouchableOpacity} from "react-native-web";
import { TableComponent } from "./table";
import {Fetch} from "./networkUtils";
import { Doctor, Staff, Child } from "./PeopleComponents";

const Appointments = () => {
    const [appointments, setAppointments] = useState([]);
    useEffect( Fetch ('/appointments/booked', setAppointments), []);

    const [selectedAppointment, setSelectedAppointment] = useState([]);


    return (<View style={globalStyle.container}>
        <TouchableOpacity onPress={() => {window.open("/", "_self")}} style={[globalStyle.button, globalStyle.rightSideButton]}>
            <Text>Go back to Home</Text>
        </TouchableOpacity>
        <Text style={globalStyle.title}>Hello!</Text>
        <TableComponent nameColumns={["Child", "Doctor", "Staff"]} list={appointments} propExtractor={printable} onPress={(appointment) => setSelectedAppointment(appointment)}/>
        <AppointmentDetails selectedAppointment={selectedAppointment} />
    </View>)
}

const printable = (appointment) => {
    return [appointment.child.name + " " + appointment.child.surname, appointment.doctor.name + " " + appointment.doctor.surname, appointment.staff.name + " " + appointment.staff.surname];
}


const AppointmentDetails = ({selectedAppointment}) => {
    if(selectedAppointment.length !== 0) {
        return (
            <View style={globalStyle.container}>
                <>
                <Text style={globalStyle.subTitle}>Details</Text>
                <Text>Description: {selectedAppointment.description}</Text>
                <Text>Date: {selectedAppointment.date}</Text>
                <Text>Time: {selectedAppointment.time}</Text>
                    </>
                <Child child={selectedAppointment.child} />
                <Doctor doctor={selectedAppointment.doctor} bookable={false} />
                <Staff staff={selectedAppointment.staff} />
            </View>
        );
    }
}

export { Appointments };