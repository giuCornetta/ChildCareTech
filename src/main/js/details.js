import React, { useEffect, useState } from "react";
import {View, Text, TouchableOpacity} from "react-native-web"
import { Fetch } from './networkUtils.js'
import { ChildDetails } from "./contatti.js";
import { root } from "./entryPoint.js";
import { globalStyle } from "./globalStyle.js";
import { TableComponent } from "./table";

const Details = () => {

    const [children, setChildren] = useState([]);

    useEffect( Fetch ('/details/children', setChildren), []);


    return (
        <View style={globalStyle.container}>
            <TouchableOpacity onPress={() => {window.open("/", "_self")}} style={[globalStyle.button, globalStyle.rightSideButton]}>
                <Text>Go back to Home</Text>
            </TouchableOpacity>
            <Text style={globalStyle.title}>Dettaglio Iscritti</Text>
            <TableComponent nameColumns={["Name", "Surname", "DOB"]} list={children} propExtractor={print} onPress={pressedChild}/>
            <TouchableOpacity onPress={() => {window.open("/createChild", "_self")}} style={[globalStyle.button, globalStyle.reminderButton, globalStyle.centerButton]}>
                <Text>Aggiungere iscritto</Text>
            </TouchableOpacity>
        </View>
    );
};


const pressedChild = (child) => {
    root.render(<ChildDetails child={child}/>);
}

const print = (child) => {
    return [child.name, child.surname, child.dob];
}




export { Details };