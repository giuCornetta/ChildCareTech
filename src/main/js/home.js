import React from "react";
import { View, Text, FlatList, StyleSheet, TouchableOpacity } from "react-native-web";
import {globalStyle} from "./globalStyle.js";

const DATA = [
    {
        title: "Attendance",
        link: "/attendance"
    },
    {
        title: "Details",
        link: "/details"
    },
    {
        title: "Gite",
        link: "/gite"
    },
    {
        title: "Appointments",
        link: "/appointments"
    }
];

const Item = ({ item, onPress}) => (
    <TouchableOpacity onPress={onPress} style={styles.item}>
        <Text style={globalStyle.title}>{item.title}</Text>
    </TouchableOpacity>
);

const Home = () => {

    const renderItem = ({ item }) => { //prende in input un item e lo restituisce modificato
        return (
            <Item
                item={item}
                onPress={() => window.open(item.link, "_self")}
            />
        );
    };

    return (
        <View style={globalStyle.container}>
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
        backgroundColor: '#f9c2ff',
    },
});

export { Home };