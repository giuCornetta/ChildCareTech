import React, {useEffect, useState} from "react";
import {Alert, Modal, Pressable, StyleSheet, Text, View} from "react-native-web";
import {globalStyle} from "./globalStyle";
import Select from 'react-select'
import {Fetch, PostRequest} from "./networkUtils";


const AddChild = () => {
    const [parents, setParents] = useState([]);
    const [doctors, setDoctors] = useState([]);
    const [parentModalVisible, setParentModalVisible] = useState(false);
    const [doctorModalVisible, setDoctorModalVisible] = useState(false);
    const [csrfToken, setCsrfToken] = useState(null);

    useEffect( Fetch ('/createChild/parents', setParents), []);
    useEffect( Fetch ('/createChild/doctors', setDoctors), []);
    useEffect( Fetch('/csrf', setCsrfToken) , [])

    return (
        <View>
            <AddChildForm parentOptions={parents} doctorOptions={doctors} setParentModalVisible={setParentModalVisible} setDoctorModalVisible={setDoctorModalVisible} /*csfrToken={csfrToken}*//>
            <Modal
                animationType="slide"
                transparent={true}
                visible={parentModalVisible}
                onRequestClose={() => {
                    Alert.alert("Modal has been closed.");
                    setParentModalVisible(!parentModalVisible);
                }}
            >
                <View style={globalStyle.centeredView}>
                    <View style={globalStyle.modalView}>
                        <Text style={globalStyle.modalText}>Hello World!</Text>
                        <AddParentForm /*csfrToken={csfrToken}*//>
                        <Pressable
                            style={[styles.button]}
                            onPress={() => setParentModalVisible(!parentModalVisible)}>
                            <Text style={styles.textStyle}>Hide Modal</Text>
                        </Pressable>
                    </View>
                </View>
            </Modal>
            <Modal
                animationType="slide"
                transparent={true}
                visible={doctorModalVisible}
                onRequestClose={() => {
                    Alert.alert("Modal has been closed.");
                    setDoctorModalVisible(!doctorModalVisible);
                }}
            >
                <View style={globalStyle.centeredView}>
                    <View style={globalStyle.modalView}>
                        <Text style={globalStyle.modalText}>Insert the Doctor's information</Text>
                        <AddDoctorForm csrfToken={csrfToken}/>
                        <Pressable
                            style={[styles.button]}
                            onPress={() => setDoctorModalVisible(!doctorModalVisible)}>
                            <Text style={styles.textStyle}>Hide Modal</Text>
                        </Pressable>
                    </View>
                </View>
            </Modal>
        </View>
    );
}
/*const initialDoctorFormData = Object.freeze({
    name: "",
    surname: "",
    cf: "",
    type: "",
    email: "",
    telephone: "",
    _csrf: "",
});*/

const AddDoctorForm = ({csrfToken}) => {
    /*const [name, setName] = useState("");
    const [surname, setSurname] = useState("");
    const [cf, setCF] = useState("");
    const [type, setType] = useState("");
    const [email, setEmail] = useState("");
    const [telephone, setTelephone] = useState("");*/
    const token = csrfToken.token;
    const initialDoctorFormData = Object.freeze({
        name: "",
        surname: "",
        cf: "",
        type: "",
        email: "",
        telephone: "",
        //_csrf: {token},
    });

    const [formData, updateFormData] = React.useState(initialDoctorFormData);

    const handleChange = (e) => {
        updateFormData({
            ...formData,

            // Trimming any whitespace
            [e.target.name]: e.target.value
        });
    };

    const handleSubmit = (e) => {
        e.preventDefault()
        console.log(formData);
        PostRequest("/createChild/createDoctor", formData , token)();
        console.log('posted')

        // ... submit to API or something
    };

    //<input type="hidden" name="_csrf" value={csrfToken.token} />
    return (<View>
        <Text>This is the DOCTOR modal</Text>
        <form onSubmit={handleSubmit}>

            <label>
                First Name:
                <input type="text" name="name" required={true} onChange={handleChange}/>
            </label><br />
            <label>
                Surname:
                <input type="text" name="surname" required={true} onChange={handleChange}/>
            </label><br />
            <label>
                CF:
                <input type="text" name="cf" required={true} onChange={handleChange}/>
            </label><br />
            <label>
                Type:
                <input type="text" name="type" required={true} onChange={handleChange}/>
            </label><br />
            <label>
                Email:
                <input type="email" name="email" pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}$" required={true} onChange={handleChange} />
            </label><br />
            <label>
                Telephone:
                <input type="tel" pattern="\(?[\d +\/()–-]{6,}\)?[ .\/–-]?\d+" name="telephone" required={true} onChange={handleChange}/>
            </label><br />
            <input type="submit" value="Submit" />
        </form>
    </View>)
}
//TODO address
//<input type="hidden" name="_csfr" value={props.csfrToken} />

const AddParentForm = () => {
    return (<View>
        <Text>This is the PARENT modal</Text>
        <form onSubmit={handleSubmit}>
            <label>
                First Name:
                <input type="text" name="first-name" required={true}/>
            </label><br />
            <label>
                Surname:
                <input type="text" name="surname" required={true}/>
            </label><br />
            <label>
                CF:
                <input type="text" name="cf" required={true}/>
            </label><br />
            <label>
                Email:
                <input type="email" name="email" pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}$" required={true}/>
            </label><br />

            <input type="submit" value="Submit" />
        </form>
    </View>)
}
//FIXME if form submit is successful add telephone number


const AddChildForm = ({parentOptions, doctorOptions, setParentModalVisible, setDoctorModalVisible}) => {
    //const [parent2, setParent2] = useState({});
    /*setParent2(prevState => {
        // Object.assign would also work
        return {...prevState, ...updatedValues};
    });*/

    /*const [firstName, setFirstName] = useState("")
    const [Surname, setSurname] = useState("")
    const [dateBirth, setDateBirth] = useState("")*/

    const [state, setState] = useState({
        name: null,
        surname: null,
        dob: null,
        parent1 : {
            name: null,
            surname: null,
            email: null,
        },
        parent2: null,

    });
    const [selectedParent2, setSelectedParent2] = useState([]);

    const handleClear = () => {
        //set((currentOptions) => currentOptions.filter((currentOption) => !selectedParent2.includes(currentOption)));
        setSelectedParent2([]);
    };


    /*const handleClick = () => {
        document.getElementById('parent2').querySelector('.css-qc6sy-singleValue').innerHTML = "";
        document.getElementById('parent2').querySelector('input[name="parent2"]').value = null;
    };*/

    const handleSubmit = () => {
        console.log('submit');
    }

    return (
        <View style={globalStyle.container}>
            <form onSubmit={handleSubmit}>
                <label>
                    First Name:
                    <input type="text" name="first-name" required={true}/>
                </label><br />
                <label>
                    Surname:
                    <input type="text" name="surname" required={true}/>
                </label><br />
                <label>
                    CF:
                    <input type="text" name="cf" required={true}/>
                </label><br />
                <label>
                    Date of Birth:
                    <input type="date" name="date-birth" required={true}/>
                </label><br />
                <label>
                    Address:
                    <input type="text" name="address" required={true}/>
                </label><br />
                <label>
                    Parent1:
                <Select options={parentOptions} name="parent1" id="parent1"/>
                </label><br />
                <label>
                    Parent 2:<Select
                    options={parentOptions}
                    value={selectedParent2}
                    name="parent2"
                    id="parent2"
                    onChange={setSelectedParent2}
                /></label><br />
                <button type="button" onClick={handleClear}>
                    Reset value parent 2
                </button><br /><br />
                <Pressable
                    style={[styles.button]}
                    onPress={() => setParentModalVisible(true)}>
                    <Text style={styles.textStyle}>Add a parent</Text>
                </Pressable> <br /><br />
            <label>
                Doctor:
            <Select options={doctorOptions} name="doctor"/>
            </label>

                <Pressable
                    style={[styles.button]}
                    onPress={() => setDoctorModalVisible(true)}>
                    <Text style={styles.textStyle}>Add a doctor</Text>
                </Pressable>
                <input type="submit" value="Submit" />
            </form>
        </View>

    );

}
//value = {parent2.value}
//onChange={handleChange}

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
    select: {
        margin: 4,
    }

});



//value={this.state.value} onChange={this.handleChange}
export { AddChild };