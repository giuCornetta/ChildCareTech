import React, { useEffect, useState } from "react";
import {View, Text, TouchableOpacity} from "react-native-web"
import {Fetch} from "./networkUtils";
import {globalStyle} from "./globalStyle.js";
import {Parents, Doctor, Contacts, Child} from "./PeopleComponents";


//TODO ALLERGIE


const ChildDetails = ({child}) => {

    const [contacts, setContacts] = useState([]);
    const [parent1Phones, setParent1Phones] = useState([]);
    const [parent2Phones, setParent2Phones] = useState(null);
    useEffect( Fetch ('/details/contacts/' + child.id, setContacts), []);
    useEffect( Fetch('/telephoneNumbers/' + child.parent1.id, setParent1Phones));
    useEffect( () => {
        if (child.parent2)
            Fetch('/telephoneNumbers/' + child.parent2.id, setParent2Phones)();
    });

    return (
        <View style={globalStyle.container}>
            <TouchableOpacity onPress={() => {window.open("/details", "_self")}} style={[globalStyle.button, globalStyle.rightSideButton]}>
                <Text>Go back to Anagrafica</Text>
            </TouchableOpacity>
            <Text style={globalStyle.title}>{child.name} {child.surname}'s contacts Page</Text>
            <Child child={child}/>
            <Parents parent1={child.parent1} parent2={child.parent2} phone={true} parent1Numbers={parent1Phones} parent2Numbers={parent2Phones}/>
            <Contacts contacts={contacts}/>
            <Doctor doctor={child.doctor} bookable={true}/>
        </View>
    );
};


export { ChildDetails };