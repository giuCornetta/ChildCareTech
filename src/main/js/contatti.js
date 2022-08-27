import React, { useEffect, useState } from "react";
import {View, Text, TouchableOpacity} from "react-native-web"
import {Fetch, PostRequest} from "./networkUtils";
import {globalStyle} from "./globalStyle.js";
import {Parents, Doctor, Contacts, Child, Allergies} from "./PeopleComponents";
import Select from "react-select";


//TODO ALLERGIE


const ChildDetails = ({child}) => {
    const [allergens, setAllergens] = useState({})

    const [contacts, setContacts] = useState([]);
    const [parent1Phones, setParent1Phones] = useState([]);
    const [parent2Phones, setParent2Phones] = useState(null);
    const [csrfToken, setCsrfToken] = useState([]);
    const [refresh, setRefresh] = useState(false);

    useEffect( Fetch ('/details/contacts/' + child.id, setContacts), []);
    useEffect( Fetch('/telephoneNumbers/' + child.parent1.id, setParent1Phones), []);
    useEffect( () => {
        if (child.parent2)
            Fetch('/telephoneNumbers/' + child.parent2.id, setParent2Phones)();
    }, []);
    useEffect(Fetch('/csrf', setCsrfToken), [])
    //useEffect( Fetch("/allergens", ()=>{}), [refresh]);
    useEffect( Fetch("/allergens/" + child.id, setAllergens), [refresh]);

    const toggleRefesh = () => {
        setRefresh(!refresh);
    }


    return (
        <View style={globalStyle.container}>
            <TouchableOpacity onPress={() => {window.open("/details", "_self")}} style={[globalStyle.button, globalStyle.rightSideButton]}>
                <Text>Go back to Anagrafica</Text>
            </TouchableOpacity>
            <Text style={globalStyle.title}>{child.name} {child.surname}'s contacts Page</Text>
            <Child child={child}/>
            <Allergies child={child.id} refresh={refresh}/>
            <AllergiesForm childId={child.id} allergens={allergens} csrfToken={csrfToken} refresh={toggleRefesh}/>
            <Parents parent1={child.parent1} parent2={child.parent2} phone={true} parent1Numbers={parent1Phones} parent2Numbers={parent2Phones}/>
            <Contacts contacts={contacts}/>
            <Doctor doctor={child.doctor}/>
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



export { ChildDetails };