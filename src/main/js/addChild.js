import React, {useEffect, useState} from "react";
import {Alert, Modal, Pressable, ScrollView, StyleSheet, Text, TouchableOpacity, View} from "react-native-web";
import {globalStyle} from "./globalStyle";
import Select from 'react-select'
import {Fetch, PostRequest} from "./networkUtils";
import {Child, Doctor, ParentPhones, Parents} from "./PeopleComponents";
import {formatDate} from "./utils";

const AddChild = () => {
    const [parents, setParents] = useState([]);
    const [doctors, setDoctors] = useState([]);
    const [parentModalVisible, setParentModalVisible] = useState(false);
    const [doctorModalVisible, setDoctorModalVisible] = useState(false);
    const [csrfToken, setCsrfToken] = useState([]);
    const [refreshParents, setRefreshParents] = useState(false)
    const [refreshDoctors, setRefreshDoctors] = useState(false);

    useEffect(Fetch('/createChild/parents', setParents), [refreshParents]);
    useEffect(Fetch('/createChild/doctors', setDoctors), [refreshDoctors]);
    useEffect(Fetch('/csrf', setCsrfToken), [])

    const toggleRefreshParents = () => {
        setRefreshParents(!refreshParents);
    }

    const toggleRefreshDoctors = () => {
        setRefreshDoctors(!refreshDoctors);
    }

    return (
        <View style={globalStyle.container}>
            <TouchableOpacity onPress={() => {window.open("/", "_self")}} style={[globalStyle.button, globalStyle.rightSideButton]}>
                <Text>Go back to Home</Text>
            </TouchableOpacity>
            <AddChildForm parentOptions={parents} doctorOptions={doctors} setParentModalVisible={setParentModalVisible}
                          setDoctorModalVisible={setDoctorModalVisible} csrfToken={csrfToken}/>
            <Modal
                propagateSwipe={true}
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
                        <ScrollView>
                        <AddParentForm csrfToken={csrfToken} refresh={toggleRefreshParents}/>
                        <Pressable
                            style={[styles.button]}
                            onPress={() => setParentModalVisible(!parentModalVisible)}>
                            <Text style={styles.textStyle}>Hide Modal</Text>
                        </Pressable>
                        </ScrollView>
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
                        <ScrollView>
                        <Text style={globalStyle.modalText}>Insert the Doctor's information</Text>
                        <AddDoctorForm csrfToken={csrfToken} refresh={toggleRefreshDoctors}/>
                        <Pressable
                            style={[styles.button]}
                            onPress={() => setDoctorModalVisible(!doctorModalVisible)}>
                            <Text style={styles.textStyle}>Hide Modal</Text>
                        </Pressable>
                        </ScrollView>
                    </View>
                </View>
            </Modal>
        </View>
    );
}


const AddDoctorForm = ({csrfToken, refresh}) => {

    const token = csrfToken.token;
    const initialDoctorFormData = Object.freeze({
        name: "",
        surname: "",
        cf: "",
        type: "",
        email: "",
        telephone: "",
        address: ""
    });

    const [formData, updateFormData] = React.useState(initialDoctorFormData);
    const [doctorInfoVisible, setDoctorInfoVisible] = useState(false);
    const [createdDoctor, setCreatedDoctor] = useState(null);

    const handleChange = (e) => {
        updateFormData({
            ...formData,
            [e.target.name]: e.target.value
        });
    };

    const handleSubmit = (e) => {
        e.preventDefault()
        console.log(formData);
        PostRequest("/createChild/createDoctor", formData, setCreatedDoctor, token, refresh)();
        console.log("createdDoctor: " + createdDoctor);
        document.getElementById("doctor-form").reset();
        setDoctorInfoVisible(true);
    };

    let doctorInfo = [];
    if (doctorInfoVisible && createdDoctor) {
        doctorInfo.push(<Doctor doctor={createdDoctor} key={0}/>)
    }

    return (<View>
        <Text>This is the DOCTOR modal</Text>
        <form onSubmit={handleSubmit} id="doctor-form">

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
                Type:
                <input type="text" name="type" required={true} onChange={handleChange}/>
            </label><br/>
            <label>
                Email:
                <input type="email" name="email" pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}$" required={true}
                       onChange={handleChange}/>
            </label><br/>
            <label>
                Telephone:
                <input type="tel" pattern="\(?[\d +\/()???-]{6,}\)?[ .\/???-]?\d+" name="telephone" required={true}
                       onChange={handleChange}/>
            </label><br/>
            <label>
                Address:
                <input type="text" name="address"/>
            </label><br/>
            <input type="submit" value="Submit"/>
        </form>
        <br/><br/>
        {doctorInfo}
    </View>)
}


const AddParentForm = ({csrfToken, refresh}) => {
    const token = csrfToken.token;
    const initialFormData = Object.freeze({
        name: "",
        surname: "",
        cf: "",
        email: ""
    });

    const [parentFormData, updateParentFormData] = React.useState(initialFormData);
    const [parentInfoVisible, setParentInfoVisible] = useState(false);
    const [phoneFormVisible, setPhoneFormVisible] = useState(false);
    const [createdParent, setCreatedParent] = useState(null);

    const handleParentFormChange = (e) => {
        updateParentFormData({
            ...parentFormData,
            [e.target.name]: e.target.value
        });
    };

    const handleParentFormSubmit = (e) => {
        e.preventDefault();
        console.log(parentFormData);
        PostRequest("/createChild/createParent", parentFormData, setCreatedParent, token, refresh)();
        console.log("createdParent: " + createdParent);
        if (!parentInfoVisible)
            setParentInfoVisible(true);
        document.getElementById("parent-form").reset();
        updateParentFormData(initialFormData);
    };

    let parentInfo = [];
    if (parentInfoVisible && createdParent) {
        parentInfo.push(<Text style={globalStyle.subTitle} key={0}>Parent</Text>);
        parentInfo.push(<Text key={1}>{createdParent.name} {createdParent.surname} ({createdParent.cf})</Text>);
        parentInfo.push(<Text key={2}>email: {createdParent.email}</Text>);
        if (!phoneFormVisible)
            setPhoneFormVisible(true);
    }

    return (<View>
        <Text>This is the PARENT modal</Text>
        <form onSubmit={handleParentFormSubmit} id="parent-form">
            <label>
                First Name:
                <input type="text" name="name" required={true} onChange={handleParentFormChange}/>
            </label><br/>
            <label>
                Surname:
                <input type="text" name="surname" required={true} onChange={handleParentFormChange}/>
            </label><br/>
            <label>
                CF:
                <input type="text" name="cf" required={true} onChange={handleParentFormChange}/>
            </label><br/>
            <label>
                Email:
                <input type="email" name="email" pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}$"
                       onChange={handleParentFormChange}/>
            </label><br/>
            <input type="submit" value="Submit"/>
        </form>
        <View style={globalStyle.container}>
            {parentInfo}
            <PhoneForm visible={phoneFormVisible} parent={createdParent} csrfToken={csrfToken}/>
        </View>

    </View>)
}

const PhoneForm = ({visible, parent, csrfToken}) => {
    if (visible && parent) {
        const initialFormData = Object.freeze({
            primarykey: {
                idParent: parent.id,
                telephone: "",
            },
            description: "",
        });

        const token = csrfToken.token;
        const [numbers, setNumbers] = useState(null);
        const [refreshNumbers, setRefreshNumbers] = useState(false);

        const toggleRefresh = () => setRefreshNumbers(!refreshNumbers);

        const [telephoneFormData, updateTelephoneFormData] = React.useState(initialFormData);

        useEffect(Fetch("/telephoneNumbers/" + parent.id, setNumbers), [refreshNumbers]);

        const handleTelephoneFormSubmit = (e) => {
            e.preventDefault()
            console.log(telephoneFormData);
            PostRequest("/telephone", telephoneFormData, () => {}, token, toggleRefresh)();
            document.getElementById("telephone-form").reset();
        };


        const handleDescriptionChange = (e) => {
            updateTelephoneFormData({
                ...telephoneFormData,
                description: e.target.value
            });
        };

        const handleTelephoneChange = (e) => {
            updateTelephoneFormData({
                ...telephoneFormData,

                primarykey: {
                    idParent : parent.id,
                    telephone: e.target.value
                }
            })
        }

        /*let numbersTag = [];
        if (numbers)
            for (let i = 0; i < numbers.length; i++) {
                numbersTag.push(<Text key={i}>Telefono {i + 1}: {numbers[i].primarykey.telephone} {(numbers[i].description)? numbers[i].description: "" })</Text>);
            }*/

        return (<View>
                <ParentPhones numbers={numbers} />
                {"\n"}
                <View><Text>Add a telephone number to this parent:</Text>
                    <form onSubmit={handleTelephoneFormSubmit} id="telephone-form">
                        <label>
                            Telephone Number:
                            <input type="tel" pattern="\(?[\d +\/()???-]{6,}\)?[ .\/???-]?\d+" name="telephone"
                                   required={true}
                                   onChange={handleTelephoneChange}/>
                        </label><br/>
                        <label>
                            Description:
                            <input type="text" name="description" onChange={handleDescriptionChange}/>
                        </label><br/>
                        <input type="submit" value="Add Telephone Number"/>
                    </form>
                </View>
            </View>
        );
    }
}


const AddChildForm = ({parentOptions, doctorOptions, setParentModalVisible, setDoctorModalVisible, csrfToken}) => {
    const token = csrfToken.token;
    const initialFormData = Object.freeze({
        name: "",
        surname: "",
        dob: "",
        parent1: "",
        parent2: null,
        doctor: "",
    });
    const [selectedParent2, setSelectedParent2] = useState([]);

    const handleClear = () => {
        setSelectedParent2([]);
        updateFormData({
            ...formData,
            parent2: null
        })
    };

    const [formData, updateFormData] = React.useState(initialFormData);
    const [childInfoVisible, setChildInfoVisible] = useState(false);
    const [createdChild, setCreatedChild] = useState(null);

    const handleChange = (e) => {
        updateFormData({
            ...formData,
            [e.target.name]: e.target.value
        });
    };

    const handleDateChange = (e) => {
        let string = e.target.value;
        updateFormData({
            ...formData,
            dob: formatDate(string)
        });
    }

    const handleSelectParent1Change = (e) => {
        updateFormData({
            ...formData,
            parent1: e.value,
        });
    }

    const handleSelectParent2Change = (e) => {
        setSelectedParent2(e);
        updateFormData({
            ...formData,
            parent2: e.value,
        });
    }

    const handleSelectDoctorChange = (e) => {
        updateFormData({
            ...formData,
            doctor: e.value,
        });
    }

    const handleSubmit = (e) => {
        e.preventDefault()
        console.log(formData);
        PostRequest("/createChild/create", formData, setCreatedChild, token, () => {
        })();
        document.getElementById("child-form").reset();
        setChildInfoVisible(true);
    };

    let childInfo = [];
    if (childInfoVisible && createdChild) {
        childInfo.push(<Child child={createdChild} key={0}/>);
        if (createdChild.parent1)
            childInfo.push(<Parents parent1={createdChild.parent1} parent2={createdChild.parent2} key={1}/>);
        if (createdChild.doctor)
            childInfo.push(<Doctor doctor={createdChild.doctor} key={2}/>)
    }

    return (
        <View style={globalStyle.container}>
            <form onSubmit={handleSubmit} id="child-form">
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
                    Date of Birth:
                    <input type="date" name="dob" required={true} onChange={handleDateChange}/>
                </label><br/>
                <label>
                    Address:
                    <input type="text" name="address" required={true} onChange={handleChange}/>
                </label><br/>
                <label>
                    Parent1:
                    <Select options={parentOptions} name="parent1" id="parent1" onChange={handleSelectParent1Change}/>
                </label><br/>
                <label>
                    Parent 2:<Select
                    options={parentOptions}
                    value={selectedParent2}
                    name="parent2"
                    id="parent2"
                    onChange={handleSelectParent2Change}
                /></label><br/>
                <button type="button" onClick={handleClear}>
                    Reset value parent 2
                </button>
                <br/><br/>
                <Pressable
                    style={[styles.button]}
                    onPress={() => setParentModalVisible(true)}>
                    <Text style={styles.textStyle}>Add a parent</Text>
                </Pressable> <br/><br/>
                <label>
                    Doctor:
                    <Select options={doctorOptions} name="doctor" onChange={handleSelectDoctorChange}/>
                </label>
                <Pressable
                    style={[styles.button]}
                    onPress={() => setDoctorModalVisible(true)}>
                    <Text style={styles.textStyle}>Add a doctor</Text>
                </Pressable>
                <input type="submit" value="Submit"/>
            </form>
            {childInfo}
        </View>

    );

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


export {AddChild};