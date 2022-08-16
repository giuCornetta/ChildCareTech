import React, { useEffect, useState } from "react";
import {View, Text, TouchableOpacity, StyleSheet} from "react-native-web"
import {Fetch} from "./networkUtils";
import {globalStyle} from "./globalStyle.js";

const contactNameAndCF = (contact, i, type) => {
    return (<Text key={i}>
        {type}: {contact.nome} {contact.cognome} ({contact.cf})
    </Text>);
}

const Genitori = (props) => {
    let genitoriTags = [];
    genitoriTags[0] = contactNameAndCF(props.genitore1, 0, "Genitore 1");
    if(props.genitore2)
        genitoriTags[1] = contactNameAndCF(props.genitore2, 1, "Genitore 2");
    return (<View style={[globalStyle.container, style.container2]}><Text style={style.subTitle}>Genitori</Text>{genitoriTags}</View>);
};
//TODO aggiungere keys
//aggiungere telefoni genitori
function Medico(props) {
    return (
        <View style={[globalStyle.container, style.container2]}><Text style={style.subTitle}>Specialista</Text>
            {contactNameAndCF(props.specialista, 0, "Specialista")}
        <Text>Telefono: {props.specialista.telefono}</Text>
            <TouchableOpacity onPress={() => {window.open("/visite/prenota/" + props.specialista.id, "_self")}} style={[globalStyle.button, globalStyle.reminderButton, globalStyle.centerButton, style.bookVisit]}>
                <Text>Prenota Visita (WIP)</Text>
            </TouchableOpacity></View>
    )
}

function Contatti(props) {
    return (
        <View style={[globalStyle.container, style.container2]}><Text style={style.subTitle}>Contatti</Text>{props.contatti.map((contatto, i) => contactNameAndCF(contatto, i, "Contatto " + (i+1)))}</View>
    );
}

function Dettagli(props) {
    let bambino = props.child;
    return (
        <View style={[globalStyle.container, style.container2]}><Text style={style.subTitle}>Dettagli</Text><Text>Nome: {bambino.nome}</Text>
        <Text>Cognome: {bambino.cognome}</Text><Text>Data di Nascita: {bambino.dob}</Text><Text>Indirizzo: {bambino.indirizzo}</Text><Allergie child={bambino}/></View>
    )
}
//TODO ALLERGIE
const Allergie = (props) => {
    let bambino = props.child;
    return (<Text style={globalStyle.reminder}>INSERIRE ALLERGIE</Text>);
}

const ContattiBambino = ({bambino}) => {

    const [contatti, setContatti] = useState([]);
    useEffect( Fetch ('/anagrafica/contacts/' + bambino.codice, setContatti), []);

    return (
        <View style={globalStyle.container}>
            <TouchableOpacity onPress={() => {window.open("/anagrafica", "_self")}} style={[globalStyle.button, globalStyle.rightSideButton]}>
                <Text>Go back to Anagrafica</Text>
            </TouchableOpacity>
            <Text style={globalStyle.title}>{bambino.nome} {bambino.cognome}'s contacts Page</Text>
            <Dettagli child={bambino}/>
            <Genitori genitore1={bambino.genitore1} genitore2={bambino.genitore2}/>
            <Contatti contatti={contatti}/>
            <Medico specialista={bambino.specialista}/>
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

export { ContattiBambino };