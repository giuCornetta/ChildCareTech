import React, {useEffect, useState} from "react";
import {globalStyle} from "./globalStyle";
import {View, Text, TouchableOpacity, Alert, ScrollView, Pressable, Modal, StyleSheet} from "react-native-web";
import { TableComponent } from "./table";
import {Fetch, PostRequest} from "./networkUtils";
import { Doctor, Staff, Child } from "./PeopleComponents";
import Select from "react-select";

const Appointments = () => {
    const [appointments, setAppointments] = useState([]);
    const [csrfToken, setCsrfToken] = useState(null);
    const [selectedAppointment, setSelectedAppointment] = useState([]);
    const [modalVisible, setModalVisible] = useState(false);
    const [refreshAppointments, setRefreshAppointments] = useState(false);

    const [children, setChildren] = useState([]);
    const [staff, setStaff] = useState([]);

    useEffect( Fetch ('/appointments/booked', setAppointments), [refreshAppointments]);
    useEffect(Fetch('/csrf', setCsrfToken), [])
    useEffect( Fetch('/appointments/children', setChildren), [])
    useEffect( Fetch('/appointments/staff', setStaff), []);

    const toggleRefreshAppointments = () => {
        setRefreshAppointments(!refreshAppointments);
    }


    return (<View style={globalStyle.container}>
        <TouchableOpacity onPress={() => {window.open("/", "_self")}} style={[globalStyle.button, globalStyle.rightSideButton]}>
            <Text>Go back to Home</Text>
        </TouchableOpacity>
        <Text style={globalStyle.title}>Here you can see and book appointments</Text>
        <>
            <Text style={globalStyle.subTitle}>Book an Appointment</Text>
            <TouchableOpacity onPress={() => {setModalVisible(true)}} style={[globalStyle.button, globalStyle.centerButton]}>
                <Text>Book a Visit</Text>
            </TouchableOpacity>
            <Modal animationType="slide" transparent={true} visible={modalVisible}
                   onRequestClose={() => {
                    Alert.alert("Modal has been closed.");
                    setModalVisible(!modalVisible);
                }}>
                <BookVisit setModalVisible={setModalVisible} children={children} staff={staff} refresh={toggleRefreshAppointments} csrfToken={csrfToken}/>
            </Modal>
        </>
        <>
            <Text style={globalStyle.subTitle}>Booked Appointments</Text>
            <TableComponent nameColumns={["Child", "Doctor", "Staff"]} list={appointments} propExtractor={printable} onPress={(appointment) => setSelectedAppointment(appointment)}/>
            <AppointmentDetails selectedAppointment={selectedAppointment} />
        </>
    </View>)
}

const BookVisit = ({setModalVisible, children, staff, refresh, csrfToken}) => {
    const [selectedChild, setSelectedChild] = useState([]);
    const [doctor, setDoctor] = useState(null);
    useEffect( () => {if(doctor) {
        Fetch("/appointments/doctor/" + selectedChild.value, setDoctor)();
    }}, [selectedChild]);
    useEffect( () => {
        updateFormData({
        ...formData,
        idDoctor: (doctor)?doctor.id: "",
    })
    }, [doctor]);

    const initialFormData = Object.freeze({
        idChild: "",
        idDoctor: "",
        idStaff: "",
        date: "",
        time: "",
        description: "",
    });

    const [formData, updateFormData] = React.useState(initialFormData);

    const handleChildChange = (e) => {
        setSelectedChild(e);
        updateFormData({
            ...formData,
            idChild: e.value,
        });
    }
    const handleStaffChange = (e) => {
        updateFormData({
            ...formData,
            idStaff: e.value,
        });
    }

    const handleChange = (e) => {
        updateFormData({
            ...formData,
            [e.target.name]: e.target.value
        });
    };

    let doctorTag;
    if(doctor){
        doctorTag = (<Doctor doctor={doctor} key={0} />);
    }

    const handleSubmit = (e) => {
        e.preventDefault()
        console.log(formData);
        PostRequest("/appointments/book", formData, () => {}, csrfToken.token, refresh)();
        document.getElementById("appointment-form").reset();
    };

    return (
            <View style={globalStyle.centeredView}>
                <View style={globalStyle.modalView}>
                    <ScrollView>
                            <form onSubmit={handleSubmit} id="appointment-form">
                                <label>
                                    Child:
                                    <Select options={children} name="child" onChange={handleChildChange}/>
                                </label><br/>
                                {doctorTag}
                                <label>
                                    Date:
                                    <input type="date" name="date" required={true} onChange={handleChange}/>
                                </label><br/>
                                <label>
                                    Time:
                                    <input type="time" name="time" required={true} min={(new Date()).toISOString().substring(0, 10)} onChange={handleChange}/>
                                </label><br/>
                                <label>
                                    Description:
                                    <input type="text" name="description" onChange={handleChange}/>
                                </label><br/>
                                <label>
                                    Staff:<Select
                                    options={staff}
                                    name="staff"
                                    onChange={handleStaffChange}
                                /></label>
                                <input type="submit" value="Submit"/>
                            </form>
                        <Pressable
                            style={[styles.button]}
                            onPress={() => setModalVisible(false)}>
                            <Text style={styles.textStyle}>Hide Modal</Text>
                        </Pressable>
                    </ScrollView>
                </View>
            </View>
    );
}
//FIXME check min date

//FIXME fare in modo che sia un solo staffer a creare la visita

const printable = (appointment) => {
    return [appointment.child.name + " " + appointment.child.surname, appointment.doctor.name + " " + appointment.doctor.surname, appointment.staff.name + " " + appointment.staff.surname];
}


const AppointmentDetails = ({selectedAppointment}) => {
    if(selectedAppointment.length !== 0) {
        return (
            <View style={globalStyle.container}>
                <>
                    <Text style={globalStyle.subTitle}>Visit Details</Text>
                    <Text>Description: {selectedAppointment.description}</Text>
                    <Text>Date: {selectedAppointment.date}</Text>
                    <Text>Time: {selectedAppointment.time}</Text>
                </>
                <Child child={selectedAppointment.child} />
                <Doctor doctor={selectedAppointment.doctor}/>
                <Staff staff={selectedAppointment.staff} />
            </View>
        );
    }
}


const styles = StyleSheet.create({

    select: {
        margin: 4,
    },
    button: {
        borderRadius: 10,
        padding: 10,
        elevation: 2,
        backgroundColor: "#2196F3",
    },
    /*buttonOpen: {
        backgroundColor: "#F194FF",
    },
    buttonClose: {
        backgroundColor: "#2196F3",
    },*/
    textStyle: {
        color: "white",
    },

});

export { Appointments };