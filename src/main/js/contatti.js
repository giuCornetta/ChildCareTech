import React, { useEffect, useState } from "react";
import {View, Text, TouchableOpacity, Alert, Pressable, Modal, StyleSheet} from "react-native-web"
import {Fetch, PostRequest} from "./networkUtils";
import {globalStyle} from "./globalStyle.js";
import {Parents, Doctor, Contacts, Child, Allergies} from "./PeopleComponents";
import Select from "react-select";
import QRCode from "react-qr-code";


//TODO ALLERGIE


const ChildDetails = ({child}) => {
    const [allergens, setAllergens] = useState({})
    const [modalVisible, setModalVisible] = useState(false);
    const [contacts, setContacts] = useState([]);
    const [parent1Phones, setParent1Phones] = useState([]);
    const [parent2Phones, setParent2Phones] = useState(null);
    const [csrfToken, setCsrfToken] = useState([]);
    const [refreshAllergies, setRefreshAllergies] = useState(false);
    const [contactRefresh, setContactRefresh] = useState(false);

    useEffect( Fetch ('/details/contacts/' + child.id, setContacts), [contactRefresh]);
    useEffect( Fetch('/telephoneNumbers/' + child.parent1.id, setParent1Phones), []);
    useEffect( () => {
        if (child.parent2)
            Fetch('/telephoneNumbers/' + child.parent2.id, setParent2Phones)();
    }, []);
    useEffect(Fetch('/csrf', setCsrfToken), [])
    useEffect( Fetch("/allergens/" + child.id, setAllergens), [refreshAllergies]);

    const toggleRefreshAllergies = () => {
        setRefreshAllergies(!refreshAllergies);
    }

    const toggleContactRefresh = () => {
        setContactRefresh(!contactRefresh);
    }


    return (
        <View style={globalStyle.container}>
            <TouchableOpacity onPress={() => {window.open("/details", "_self")}} style={[globalStyle.button, globalStyle.rightSideButton]}>
                <Text>Go back to Anagrafica</Text>
            </TouchableOpacity>
            <Text style={globalStyle.title}>{child.name} {child.surname}'s contacts Page</Text>
            <Pressable
                style={[styles.button, globalStyle.centerButton]}
                onPress={() => setModalVisible(true)}>
                <Text style={styles.textStyle}>Show QRCode</Text>
            </Pressable>
            <Child child={child}/>
            <Allergies child={child.id} refresh={refreshAllergies}/>
            <AllergiesForm childId={child.id} allergens={allergens} csrfToken={csrfToken} refresh={toggleRefreshAllergies}/>
            <Parents parent1={child.parent1} parent2={child.parent2} phone={true} parent1Numbers={parent1Phones} parent2Numbers={parent2Phones}/>
            <Contacts contacts={contacts}/>
            <AddContact childId={child.id} csrfToken={csrfToken} refresh={toggleContactRefresh}/>
            <Doctor doctor={child.doctor}/>

            <Modal
                propagateSwipe={true}
                animationType="slide"
                transparent={true}
                visible={modalVisible}
                onRequestClose={() => {
                    Alert.alert("Modal has been closed.");
                    setModalVisible(!modalVisible);
                }}>
                <View style={globalStyle.centeredView}>
                    <View style={globalStyle.modalView}>
                        <>
                        <QRCode value={child.id + ";" + child.cf + ";" + child.name + ";" + child.surname} />
                            </>
                            <Pressable
                                style={[styles.button]}
                                onPress={() => setModalVisible(!modalVisible)}>
                                <Text style={styles.textStyle}>Hide Modal</Text>
                            </Pressable>
                    </View>
                </View>
            </Modal>

        </View>
    );
};

const AllergiesForm = ({allergens, childId, csrfToken, refresh}) => {
    const initialFormData = Object.freeze({
        primarykey: {
            idChild: "",
            idAllergen: "",
        }
    });

    const [formData, updateFormData] = useState(initialFormData)
    const [allergenInput, setAllergenInput] = useState(null);


    const handleChange = (e) => {
        setAllergenInput(e);
        updateFormData({
            primarykey: {
                idChild: childId,
                idAllergen: e.value,
            }
        })
    }

    const handleSubmit = (e) => {
        e.preventDefault();
        console.log(formData);
        PostRequest("/allergies", formData, ()=>{}, csrfToken.token, refresh)();
        setAllergenInput([]);

    }

    return (
        <form onSubmit={handleSubmit} id="allergies-form">
            <label>
                Add an allergen:<Select
                options={allergens}
                name="allergen"
                onChange={handleChange}
                value={allergenInput}
            /></label>
            <input type="submit" value="Add"/>
        </form>
    );
}

const AddContact = ({csrfToken, refresh, childId}) => {
    const token = csrfToken.token;
    const initialFormData = Object.freeze({
        cf: "",
        idChild: childId,
        name: "",
        surname: "",
        telephone: ""
    });

    const [formData, updateFormData] = useState(initialFormData);

    const handleChange = (e) => {
        updateFormData({
            ...formData,
            [e.target.name]: e.target.value
        });
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        console.log(formData);
        PostRequest("/details/contacts/add", formData, () => {}, token, refresh)();
        document.getElementById("contact-form").reset();
        updateFormData(initialFormData);
    };

    return (<View>
        <Text style={globalStyle.subTitle}>Add another Contact</Text>
        <form onSubmit={handleSubmit} id="contact-form">
            <label>
                First Name:
                <input type="text" name="name" required={true} onChange={handleChange}/>
            </label><br/>
            <label>
                Surname:
                <input type="text" name="surname" required={true} onChange={handleChange}/>
            </label><br/>
            <label>
                CF:
                <input type="text" name="cf" required={true} onChange={handleChange}/>
            </label><br/>
            <label>
                Telephone:
                <input type="tel" pattern="\(?[\d +\/()–-]{6,}\)?[ .\/–-]?\d+" name="telephone" required={true}
                       onChange={handleChange}/>
            </label><br/><br/>
            <input type="submit" value="Submit"/>
        </form>

    </View>)
}

const styles = StyleSheet.create({
    button: {
        borderRadius: 10,
        padding: 10,
        elevation: 2,
        backgroundColor: "#2196F3",
    },
    textStyle: {
        color: "white",
    },

});

export { ChildDetails };