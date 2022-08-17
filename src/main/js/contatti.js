import React, { useEffect, useState } from "react";
import {View, Text, TouchableOpacity, StyleSheet} from "react-native-web"
import {Fetch} from "./networkUtils";
import {globalStyle} from "./globalStyle.js";

const contactNameAndCF = (contact, i, type) => {
    return (<Text key={i}>
        {type}: {contact.name} {contact.surname} ({contact.cf})
    </Text>);
}

const Genitori = (props) => {
    let genitoriTags = [];
    genitoriTags[0] = contactNameAndCF(props.parent1, 0, "Parent 1");
    if(props.parent2)
        genitoriTags[1] = contactNameAndCF(props.parent2, 1, "Parent 2");
    return (<View style={[globalStyle.container, style.container2]}><Text style={style.subTitle}>Genitori</Text>{genitoriTags}</View>);
};
//TODO aggiungere keys
//aggiungere telefoni genitori
function Medico(props) {
    return (
        <View style={[globalStyle.container, style.container2]}><Text style={style.subTitle}>Doctor</Text>
            {contactNameAndCF(props.doctor, 0, "Doctor")}
        <Text>Telefono: {props.doctor.telephone}</Text>
            <TouchableOpacity onPress={() => {window.open("/visits/book/" + props.doctor.id, "_self")}} style={[globalStyle.button, globalStyle.reminderButton, globalStyle.centerButton, style.bookVisit]}>
                <Text>Prenota Visita (WIP)</Text>
            </TouchableOpacity></View>
    )
}

function Contatti(props) {
    return (
        <View style={[globalStyle.container, style.container2]}><Text style={style.subTitle}>Contacts</Text>{props.contacts.map((c, i) => contactNameAndCF(c, i, "Contacts " + (i+1)))}</View>
    );
}

function Dettagli(props) {
    let bambino = props.child;
    return (
        <View style={[globalStyle.container, style.container2]}><Text style={style.subTitle}>Details</Text><Text>Name: {bambino.name}</Text>
        <Text>Surname: {bambino.surname}</Text><Text>Date of Birth: {bambino.dob}</Text><Text>Address: {bambino.address}</Text><Allergie child={bambino}/></View>
    )
}
//TODO ALLERGIE
const Allergie = (props) => {
    let bambino = props.child;
    return (<Text style={globalStyle.reminder}>INSERIRE ALLERGIE</Text>);
}

const ChildDetails = ({bambino}) => {

    const [contatti, setContatti] = useState([]);
    useEffect( Fetch ('/anagrafica/contacts/' + bambino.id, setContatti), []);

    return (
        <View style={globalStyle.container}>
            <TouchableOpacity onPress={() => {window.open("/anagrafica", "_self")}} style={[globalStyle.button, globalStyle.rightSideButton]}>
                <Text>Go back to Anagrafica</Text>
            </TouchableOpacity>
            <Text style={globalStyle.title}>{bambino.name} {bambino.surname}'s contacts Page</Text>
            <Dettagli child={bambino}/>
            <Genitori parent1={bambino.parent1} parent2={bambino.parent2}/>
            <Contatti contacts={contatti}/>
            <Medico doctor={bambino.specialista}/>
        </View>
    );
};

const style = StyleSheet.create({
    subTitle: {
        fontSize : 25,
        margin : 5
},
    container2: {
       padding: 0,
    },
    bookVisit: {
        margin : 10,
    }
})

export { ChildDetails };