import {StyleSheet, Text, TouchableOpacity, View} from "react-native-web";
import {globalStyle} from "./globalStyle";
import React from "react";

const Parents = (props) => {
    let parentsTags = [];
    parentsTags[0] = contactNameAndCF(props.parent1, 0, "Parent 1");
    if(props.parent2)
        parentsTags[1] = contactNameAndCF(props.parent2, 1, "Parent 2");
    return (<View style={[globalStyle.container, style.container2]}><Text style={globalStyle.subTitle}>Parents</Text>{parentsTags}</View>);
};
//TODO aggiungere telefoni genitori

const Doctor = (props) => {
    let button =[];
    if(props.bookable){
        button = (<TouchableOpacity onPress={() => {window.open("/visits/book/" + props.doctor.id, "_self")}} style={[globalStyle.button, globalStyle.reminderButton, globalStyle.centerButton, style.bookVisit]}>
            <Text>Book Appointment (WIP)</Text>
        </TouchableOpacity>);
    }
    return (
        <View style={[globalStyle.container, style.container2]}><Text style={globalStyle.subTitle}>Doctor</Text>
            <Text>{props.doctor.name} {props.doctor.surname} ({props.doctor.cf})</Text>
            <Text>Phone: {props.doctor.telephone}</Text>
            <Text>Email: {props.doctor.email}</Text>
            <Text>Type: {props.doctor.type}</Text>
            {button}
        </View>
    )
}

const Staff = (props) => {
    return (
        <View style={[globalStyle.container, style.container2]}><Text style={globalStyle.subTitle}>Staff</Text>
            <Text>{props.staff.name} {props.staff.surname} ({props.staff.cf})</Text>
            <Text>Email: {props.staff.email}</Text>
            <Text>Type: {props.staff.type}</Text>
        </View>
    )
}

function Contacts(props) {

    if(props.contacts) {
        return (
            <View style={[globalStyle.container, style.container2]}>
                <Text style={globalStyle.subTitle}>Contacts</Text>
                <Text>{props.contacts.map((element, i) => {
                    return ("Contact " + i + ": " + element.name + " " + element.surname + " ("+ element.cf + ")" + "/n" + "Phone: " + element.telephone);
                })}</Text>
            </View>
        );
    }
}

function Child(props) {
    let bambino = props.child;
    return (
        <View style={[globalStyle.container, style.container2]}><Text style={globalStyle.subTitle}>Child Details</Text>
            <Text>Name: {bambino.name}</Text>
            <Text>Surname: {bambino.surname}</Text>
            <Text>CF: {bambino.cf}</Text>
            <Text>Date of Birth: {bambino.dob}</Text>
            <Text>Address: {bambino.address}</Text>
            <Allergie child={bambino}/></View>
    )
}

const Allergie = (props) => {
    let bambino = props.child;
    return (<Text style={globalStyle.reminder}>INSERIRE ALLERGIE</Text>);
}

const contactNameAndCF = (contact, i, type) => {
    return (<Text key={i}>
        {type}: {contact.name} {contact.surname} ({contact.cf})
    </Text>);
}

const style = StyleSheet.create({
    container2: {
        padding: 0,
    },
    bookVisit: {
        margin : 10,
    }
});

export { Child, Contacts, Doctor, Parents, Staff };