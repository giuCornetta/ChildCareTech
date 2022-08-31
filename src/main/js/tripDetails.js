import React, {useEffect, useState} from "react";
import {Text, TouchableOpacity, View, StyleSheet} from "react-native-web";
import {globalStyle} from "./globalStyle";
import {TableComponent} from "./table";
import {Fetch, PostRequest} from "./networkUtils";
import Select from "react-select";

const TripDetails = ({trip}) => {
    const [tripStops, setTripStops] = useState(null);
    const [registeredChildren, setRegisteredChildren] = useState(null);
    const [registeredStaff, setRegisteredStaff] = useState(null);
    const [childrenMissingRegistration, setChildrenMissingRegistration] = useState(null);
    const [staffMissingRegistration, setStaffMissingRegistration] = useState(null);

    const [refreshBus, setRefreshBus] = useState(false);
    const [refreshChildren, setRefreshChildren] = useState(false);
    const [refreshStaff, setRefreshStaff] = useState(false);
    const [refreshStops, setRefreshStops] = useState(false);

    const [buses, setBuses] = useState(null);
    const [csrfToken, setCsrfToken] = useState([]);
    const [busFormVisible, setBusFormVisible] = useState(false);


    //const [tripSupervisors, setTripSupervisors] = useState();

    useEffect(Fetch('/csrf', setCsrfToken), []);
    useEffect( Fetch('/trips/stops/' + trip.id, setTripStops), [refreshStops]);
    useEffect( Fetch('/trips/registration/children/' + trip.id, setRegisteredChildren), [refreshChildren]);
    useEffect( Fetch('/trips/registration/staff/' + trip.id, setRegisteredStaff), [refreshStaff]);
    useEffect( Fetch('/trips/registration/children/' + trip.id + "/missing", setChildrenMissingRegistration), [refreshChildren]);
    useEffect( Fetch('/trips/registration/staff/' + trip.id + "/missing", setStaffMissingRegistration), [refreshStaff]);
    useEffect(Fetch('/trips/buses/' + trip.id, setBuses), [refreshBus]);

    const toggleRefreshChildren = () => {
        setRefreshChildren(!refreshChildren);
    }

    const toggleRefreshStaff = () => {
        setRefreshStaff(!refreshStaff);
    }

    const toggleRefreshStops = () => {
        setRefreshStops(!refreshStops);
    }

    const [day, month, year] = trip.departureDate.split('/');
    const departureDate = new Date(+year, month - 1, +day);
    let registrationTags = [], today=new Date(), i = 0;
    if(buses) {
        if (today < departureDate && buses.length > 0) {
            registrationTags.push(<AddStaffRegistration tripId={trip.id} notRegistered={staffMissingRegistration}
                                                        bus={buses} refresh={toggleRefreshStaff}
                                                        token={csrfToken.token} key={i++}/>)
            registrationTags.push(<AddChildRegistration tripId={trip.id} notRegistered={childrenMissingRegistration}
                                                        bus={buses} refresh={toggleRefreshChildren}
                                                        token={csrfToken.token} key={i++}/>)
        }

    } else {
        if(!busFormVisible) {
            setBusFormVisible(true);
        }
    }

    return (<View style={globalStyle.container}>
            <TouchableOpacity onPress={() => {window.open("/trips", "_self")}} style={[globalStyle.button, globalStyle.rightSideButton]}>
                <Text>Go back to Trips</Text>
            </TouchableOpacity>
            <Text style={globalStyle.title}>Information about the trip</Text>
            <Details trip={trip} />
            <AddBusForm visible={busFormVisible} tripId={trip.id} csrfToken={csrfToken} refresh={() => setRefreshBus(!refreshBus)} setVisible={setBusFormVisible}/>
            <Stops stops={tripStops} tripId={trip.id} csrfToken={csrfToken} refresh={toggleRefreshStops}/>
            {registrationTags}
            <Summary children={registeredChildren} staff={registeredStaff} buses={buses} />
        </View>
    );
}

const Details = ({trip}) => {

    return (<View style={globalStyle.container}><Text style={globalStyle.subTitle}>Trip Details</Text>
            <Text>Destination: {trip.arrivalCity}</Text>
            <Text>Duration: {trip.departureDate} - {trip.returnDate}</Text>
            <Text>Departing from: {trip.departureCity}</Text>
        </View>
    );
}

const AddBusForm = ({visible, tripId, csrfToken, refresh, setVisible}) => {
    if(visible) {
        const [formData, updateFormData] = useState({})

        const handleChange = (e) =>{
            updateFormData({
                primarykey: {
                    idTrip: tripId,
                    licensePlate: e.target.value,
                }
            })
        }

        const handleSubmit = (e) => {
            e.preventDefault()
            console.log(formData);
            PostRequest("/trips/buses/add", formData, () => {}, csrfToken.token, refresh)();
            document.getElementById("bus-form").reset();
            if(visible) {
                setVisible(false);
            }
            updateFormData({});
        }


        return (<View style={globalStyle.container}>
            <Text style={globalStyle.subTitle}>Add a bus to the Trip</Text>
            <form onSubmit={handleSubmit} id="bus-form">
                <label>
                    License Plate:<input name="licensePlate" type="text" onChange={handleChange}/>
                </label>
                <input type="submit" value="Submit"/>
            </form>
            <TouchableOpacity onPress={() => setVisible(false)} style={[globalStyle.button, globalStyle.centerButton]}>
                <Text>Hide form</Text>
            </TouchableOpacity>
        </View>);
    } else {
        return (<View style={globalStyle.container}>
            <TouchableOpacity onPress={() => setVisible(true)} style={[globalStyle.button, globalStyle.centerButton]}>
                <Text>Add a bus to the Trip</Text>
            </TouchableOpacity>
        </View>)
    }
}

const stopsPropExtractor = (stop) => {
    return [stop.stopName, stop.arrivalTime, stop.departingTime];
}

const Stops = ({tripId, stops, refresh, csrfToken}) => {
    const [stopFormVisible, setStopFormVisible] = useState(false);

    if(stops) {
        if(stops.length > 0){
            let returnStops = [], departureStops = [];
            for(let i=0; i<stops.length; i++){
                if(stops[i].wayBack){
                    returnStops.push(stops[i]);
                } else {
                    departureStops.push(stops[i]);
                }
            }
            let returnStopsTag = [], departureStopsTag = [];
            if(returnStops.length > 0)
                returnStopsTag.push(<TableComponent nameColumns={["Stop", "Arrival", "Departure"]} list={returnStops}
                                                    propExtractor={stopsPropExtractor} onPress={null} key={0}/>);
            else
                returnStopsTag.push(<Text key={0}>There are no stops for now</Text>);


            if(departureStops.length > 0)
                departureStopsTag.push(<TableComponent nameColumns={["Stop", "Arrival", "Departure"]} list={departureStops}
                                                    propExtractor={stopsPropExtractor} onPress={null} key={1}/>);
            else
                departureStopsTag.push(<Text key={1}>There are not stops for now</Text>);

            return (<View style={globalStyle.container}>
                    <Text style={globalStyle.subTitle}>Stops</Text>
                    <Text style={style.subSubTitle}>On the way there</Text>
                    {departureStopsTag}
                    <Text style={style.subSubTitle}>On the way back</Text>
                    {returnStopsTag}
                    <TouchableOpacity onPress={() => {setStopFormVisible(true)}} style={[globalStyle.button, globalStyle.centerButton]}>
                        <Text>Add Stops</Text>
                    </TouchableOpacity>
                    <StopsForm tripId={tripId} visible={stopFormVisible} setVisible={setStopFormVisible} refresh={refresh} token={csrfToken.token}/>
                </View>
            )
        } else {
            return (<View style={globalStyle.container}>
                <Text style={globalStyle.subTitle}>Stops</Text>
                <Text style={{color: "gray"}}>This trip does not have any stops</Text>
                <TouchableOpacity onPress={() => {setStopFormVisible(true)}} style={[globalStyle.button, globalStyle.centerButton]}>
                    <Text>Add Stops</Text>
                </TouchableOpacity>
                <StopsForm tripId={tripId} visible={stopFormVisible} setVisible={setStopFormVisible} refresh={refresh} token={csrfToken.token}/>
            </View>)
        }
    }
} //FIXME make workking add stops  setStopsFormVisible(true)

const StopsForm = ({tripId, visible, refresh, token, setVisible}) => {
    const initialFormData = Object.freeze({
        idTrip: tripId,
        stopName: "",
        arrivalTime: "",
        departingTime: "",
        wayBack: "",
    })
    const [selectedWay, setSelectedWay] = useState({});
    const [formData, updateFormData] = useState(initialFormData);
    if(visible){

        let tripType = [
            {
                label: "Way Back",
                value: true
            },
            {
                label: "Way There",
                value: false
            }
        ]   ;

        const handleChange = (e) => {
            updateFormData({
                ...formData,
                [e.target.name]: e.target.value,
            });
        }

        const handleSelectChange = (e) => {
            setSelectedWay(e);
            updateFormData({
                ...formData,
                wayBack: e.value,
            })
        }

        const handleSubmit = (e) => {
            e.preventDefault();
            PostRequest("/trips/stops/add", formData, () => {}, token, refresh)();
            document.getElementById("stops-form").reset();
            console.log(formData);
            setSelectedWay([]);
            setVisible(false);
            updateFormData(initialFormData);
        }

        return (<form onSubmit={handleSubmit} id="stops-form">
            <label>
                Name:<input type="text" name="stopName" onChange={handleChange}/>
            </label> <br/>
            <label>
                Arrival Time:<input type="time" name="arrivalTime" onChange={handleChange}/>
            </label><br />
            <label>
                Departing Time:<input type="time" name="departingTime" onChange={handleChange}/>
            </label>
            <label>
                <Select
                    options={tripType}
                    name="selectedWay"
                    onChange={handleSelectChange}
                    value ={selectedWay}
                    menuPortalTarget={document.querySelector('body')}
                />
            </label>
            <input type="submit" value="Submit" />
        </form>);
    }
}

const Summary = ({children, staff, buses}) => {
    if(buses) {
        if(buses.length > 0) {
            let busTags = [], k = 0;
            for (let i = 0; i < buses.length; i++) {
                busTags[i] = [];
                busTags[i].push(<Text style={style.subSubTitle} key={k++}>Bus N.{(i + 1)}: {buses[i].value}</Text>);
                if (staff) {
                    if (staff[i]) {
                        busTags[i].push(<Text key={k++}><b>Supervisors:</b></Text>);
                        for (let j = 0; j < staff[i].length; j++) {
                            busTags[i].push(<Text key={k++}>{staff[i][j].staff.name} {staff[i][j].staff.surname},
                                Group: {staff[i][j].primarykey.group}</Text>)
                        }
                    }
                }
                if (children) {
                    if (children[i]) {
                        busTags[i].push(<Text key={k++}><b>Children:</b></Text>);
                        for (let j = 0; j < children[i].length; j++) {
                            busTags[i].push(<Text key={k++}>{children[i][j].child.name} {children[i][j].child.surname},
                                Group: {children[i][j].group}</Text>)
                        }
                    }
                }
            }

            let busTagContainers = [];
            for (let i = 0; i < busTags.length; i++) {
                busTagContainers.push(<View style={globalStyle.container} key={k++}>{busTags[i]}</View>);
            }

            if(busTagContainers.length < 0){
                busTagContainers.push(<Text key={k++}>There are no buses assigned to this trip</Text>)
            }


            return (<View style={globalStyle.container}>
                <Text style={globalStyle.subTitle}>Summary</Text>
                {busTagContainers}
            </View>);
        } return (<View></View>)
    }
}

const AddChildRegistration = ({tripId, notRegistered, bus, refresh, token}) => {
    const initialFormData = Object.freeze({
        primarykey: {
            idTrip: tripId,
            idChild: "",
        },
        bus: "",
        group: "",
    })
    const [formData, updateFormData] = useState(initialFormData);
    const [selectedChild, setSelectedChild] = useState([]);
    const [selectedBus, setSelectedBus] = useState([]);
    const [selectedGroup, setSelectedGroup] = useState([]);

    const handleChildChange = (e) => {
        setSelectedChild(e);
        updateFormData({
            ...formData,
            primarykey: {
                idTrip: tripId,
                idChild: e.value,
            }
        })

    }

    const handleGroupChange = (e) => {
        setSelectedGroup(e);

        updateFormData({
            ...formData,
            group: e.value,
        })
    }

    const handleBusChange = (e) => {
        setSelectedBus(e);
        updateFormData({
            ...formData,
            bus: e.value,
        })
    }

    let groups = [];
    if(bus){
        for(let i=0; i<bus.length; i++){
            groups[i] = {value: (i+1), label: "Group " + (i+1)};
        }
    }

    const handleSubmit = () => {
        PostRequest("/trips/register/child", formData, () => {}, token, refresh)();
        console.log(formData);
        setSelectedChild([]);
        setSelectedGroup([]);
        setSelectedBus([]);
        updateFormData(initialFormData);
    }

    return (<View style={globalStyle.container}>
        <Text style={globalStyle.subTitle}>Register a Child</Text>
        <label>
            Choose a Child:<Select
            options={notRegistered}
            name="child"
            onChange={handleChildChange}
            value ={selectedChild}
            style={style.select}
            menuPortalTarget={document.querySelector('body')}
        /></label>
        <label>
            Choose a Bus:<Select
            options={bus}
            name="bus"
            onChange={handleBusChange}
            value ={selectedBus}
            style={style.select}
            menuPortalTarget={document.querySelector('body')}
        /></label>
        <label>
            Choose a Group:<Select
            options={groups}
            name="group"
            onChange={handleGroupChange}
            value ={selectedGroup}
            style={style.select}
            menuPortalTarget={document.querySelector('body')}
        /></label>
        <TouchableOpacity onPress={handleSubmit} style={[globalStyle.button, globalStyle.centerButton]}>
            <Text>Submit</Text>
        </TouchableOpacity>
    </View>)


}

const AddStaffRegistration = ({tripId, notRegistered, bus, refresh, token}) => {
    const initialFormData = Object.freeze({
        primarykey: {
            idTrip: tripId,
            group: "",
            idSupervisor: "",
        },
        bus: "",
    })
    const [formData, updateFormData] = useState(initialFormData);
    const [selectedStaff, setSelectedStaff] = useState([]);
    const [selectedBus, setSelectedBus] = useState([]);
    const [selectedGroup, setSelectedGroup] = useState([]);

    const handleStaffChange = (e) => {
        setSelectedStaff(e);
        updateFormData({
            ...formData,
            primarykey: {
                idTrip: tripId,
                group: selectedGroup.value,
                idSupervisor: e.value,
            }
        })

    }

    const handleGroupChange = (e) => {
        setSelectedGroup(e);

        updateFormData({
            ...formData,
            primarykey: {
                idTrip: tripId,
                group: e.value,
                idSupervisor: selectedStaff.value,
            }
        })
    }

    const handleBusChange = (e) => {
        setSelectedBus(e);

        updateFormData({
            ...formData,
            bus: e.value,
        })
    }

    let groups = [];
    if(bus){
        for(let i=0; i<bus.length; i++){
            groups[i] = {value: (i+1), label: "Group " + (i+1)};
        }
    }

    const handleSubmit = () => {
        PostRequest("/trips/register/staff", formData, () => {}, token, refresh)();
        console.log(formData);
        setSelectedStaff([]);
        setSelectedGroup([]);
        setSelectedBus([]);
        updateFormData(initialFormData);
    }

    return (<View style={globalStyle.container}>
        <Text style={globalStyle.subTitle}>Register a Supervisor</Text>
        <label>
            Choose a Staff Member:<Select
            options={notRegistered}
            name="child"
            onChange={handleStaffChange}
            value ={selectedStaff}
            menuPortalTarget={document.querySelector('body')}
        /></label>
        <label>
            Choose a Bus:<Select
            options={bus}
            name="bus"
            onChange={handleBusChange}
            value ={selectedBus}
            menuPortalTarget={document.querySelector('body')}
        /></label>
        <label>
            Choose a Group:<Select
            options={groups}
            name="group"
            onChange={handleGroupChange}
            value ={selectedGroup}
            menuPortalTarget={document.querySelector('body')}
        /></label>
        <TouchableOpacity onPress={handleSubmit} style={[globalStyle.button, globalStyle.centerButton]}>
            <Text>Submit</Text>
        </TouchableOpacity>
    </View>)


}

//TODO when pressing a single stop a modal will appear with the stops attendances??

const style = StyleSheet.create({
    subSubTitle: {
        fontSize: 20,
    }
})

export {TripDetails};