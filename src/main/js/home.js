import React, {useEffect, useState} from "react";
import { View, Text, FlatList, StyleSheet, TouchableOpacity } from "react-native-web";
import {globalStyle} from "./globalStyle.js";
import {Fetch, PostRequest} from "./networkUtils";

//TODO delete implemented
const DATA = [
    {
        title: "Attendance",
        link: "/attendance",
        implemented: true
    },
    {
        title: "Details",
        link: "/details", implemented: true
    },
    {
        title: "Trips",
        link: "/trips", implemented: false
    },
    {
        title: "Appointments",
        link: "/appointments", implemented: false
    },
    {
        title: "Cafeteria",
        link: "/cafeteria",
        implemented: true
    }
];

const Item = ({ item, onPress}) => {
    let bgColor = item.implemented ? 'rgba(107, 207, 162, 0.8)' : '#f9c2ff';
    return(
        <TouchableOpacity onPress={onPress} style={[styles.item, {backgroundColor: bgColor}]}>
            <Text style={globalStyle.title}>{item.title}</Text>
        </TouchableOpacity>);
};

const Home = () => {

    const [csrfToken, setCsrfToken] = useState([]);
    useEffect(Fetch('/csrf', setCsrfToken), [])

    const renderItem = ({ item }) => { //prende in input un item e lo restituisce modificato
            return (
                <Item
                    item={item}
                    onPress={() => window.open(item.link, "_self")}
                />
            );
    };

    const submit = (e) => {
        e.preventDefault();
        PostRequest(PostRequest("/logout", null, () => {}, csrfToken.token, () => {}))();
    }

    return (
        <View style={globalStyle.container}>
            <TouchableOpacity onPress={PostRequest("/logout", null, () => {}, csrfToken.token, () => {})} style={[globalStyle.button, globalStyle.rightSideButton]}>
                <Text>Logout</Text>
            </TouchableOpacity>
            <form onSubmit={submit}>
                <input type="submit" value="LOGOUT"/>
            </form>
            <Text style={globalStyle.title}>Opzioni disponibili:</Text>
            <FlatList
                data={DATA}
                renderItem={renderItem}
            />
        </View>
    );
};

const styles = StyleSheet.create({
    item: {
        flex: '1',
        padding: 30,
        marginVertical: 8,
        marginHorizontal: 16,
        justifyContent: 'center',
        alignItems: 'center',

    },
});

//backgroundColor: '#f9c2ff',

export { Home };