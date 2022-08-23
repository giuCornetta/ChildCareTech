import React, { useEffect, useState } from "react";
import {View, Text, TouchableOpacity, StyleSheet} from "react-native-web"
import { Fetch } from './networkUtils.js'
import { ChildDetails } from "./contatti.js";
import { root } from "./entryPoint.js";
import { globalStyle } from "./globalStyle.js";
import { TableComponent } from "./table";

const Details = () => {

    const [children, setChildren] = useState([]);
    const [modalVisible, setModalVisible] = useState(false);

    useEffect( Fetch ('/details/children', setChildren), []);

    let textButton = [];
    if(!modalVisible)
        textButton.push(<Text key={0}>Add a child(WIP)</Text>);
    else
        textButton.push(<Text key={0}>Close Form</Text>);

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

            //<View>
            // <Modal
            //                     animationType="slide"
            //                     transparent={true}
            //                     visible={modalVisible}
            //                     onRequestClose={() => {
            //                         Alert.alert("Modal has been closed.");
            //                         setModalVisible(!modalVisible);
            //                     }}
            //                 >
            //
            //                     <View style={globalStyle.centeredView}>
            //                         <View style={globalStyle.modalView}>
            //                             <Text style={globalStyle.modalText}>Hello World!</Text>
            //                             <AddChildForm />
            //                             <Pressable
            //                                 style={[styles.button]}
            //                                 onPress={() => setModalVisible(!modalVisible)}>
            //                                 <Text style={styles.textStyle}>Hide Modal</Text>
            //                             </Pressable>
            //                         </View>
            //                     </View>
            //                 </Modal>
            //
            //             </View>
            //

//<Image source={require('./img/red_cross.png')} />

/*<Pressable
                    style={[styles.button, styles.buttonOpen]}
                    onPress={() => setModalVisible(true)}
                >
                    <Text style={styles.textStyle}>Show Modal</Text>
                </Pressable>
                */

//<AddChild openForm={openForm}/>


//TODO add Aggiunta Iscritti -> mettere link giusto
//<Button title="Go back to Home" onPress={ () => {window.open("/", "_self")}}/>

const pressedChild = (child) => {
    root.render(<ChildDetails child={child}/>);
}

const print = (child) => {
    return [child.name, child.surname, child.dob];
}

 //FIXME capire come prendere parametri da form

/*<TextInput placeholder="Enter Email" />
            <TextInput
                secureTextEntry={true}
                placeholder="Enter Password"
            />*/


const styles = StyleSheet.create({

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


export { Details };