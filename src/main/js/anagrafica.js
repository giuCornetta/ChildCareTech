import React, { useEffect, useState } from "react";
import {View, Text, TouchableOpacity, Pressable} from "react-native-web"
import { Fetch } from './networkUtils.js'
import { ChildDetails } from "./contatti.js";
import { root } from "./entryPoint.js";
import { globalStyle } from "./globalStyle.js";

const Anagrafica = () => {

    const [children, setChildren] = useState([]);
    useEffect( Fetch ('/anagrafica/children', setChildren), []);

    return (
        <View style={globalStyle.container}>
            <TouchableOpacity onPress={() => {window.open("/", "_self")}} style={[globalStyle.button, globalStyle.rightSideButton]}>
                <Text>Go back to Home</Text>
            </TouchableOpacity>
            <Text style={globalStyle.title}>Dettaglio Iscritti</Text>
            <TableComponent list={children}/>
            <TouchableOpacity onPress={() => {window.open("/", "_self")}} style={[globalStyle.button, globalStyle.reminderButton, globalStyle.centerButton]}>
                <Text>Aggiungere iscritto (WIP)</Text>
            </TouchableOpacity>
        </View>

    );
};
//TODO add Aggiunta Iscritti -> mettere link giusto
//<Button title="Go back to Home" onPress={ () => {window.open("/", "_self")}}/>

const pressedChild = (child) => {
    root.render(<ChildDetails child={child} />);
}

const TableRow = ({child}) => {
    return (
        <View style={globalStyle.row}>
            <Pressable onPress={() => pressedChild(child)} style={globalStyle.row}>
                <View style={globalStyle.cell}><Text>{child.name}</Text></View>
                <View style={globalStyle.cell}><Text>{child.surname}</Text></View>
                <View style={globalStyle.cell}><Text>{child.dob}</Text></View>
            </Pressable>
        </View>
    );
}

const TableHeader = () => {
    return (
        <View style={globalStyle.row}>
            <View style={[globalStyle.cell, globalStyle.header]}><Text>Name</Text></View>
            <View style={[globalStyle.cell, globalStyle.header]}><Text>Surname</Text></View>
            <View style={[globalStyle.cell, globalStyle.header]}><Text>dob</Text></View>
        </View>
    )
}

const TableComponent = ({list}) => {

    var rows = [];
    for (var i = 0; i < list.length; i++) {
        rows.push(<TableRow key={i} child={list[i]}/>);
    }
    return (<View style={[globalStyle.table, globalStyle.container]}>
        <TableHeader />
        {rows}
    </View>)

}

//children.map( (object, i ) => {
//        console.log(i)
//    <TableRow key={i} child={object} />
// })

export { Anagrafica };