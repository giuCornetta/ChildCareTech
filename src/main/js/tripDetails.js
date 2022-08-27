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
    const [missingRegistration, setMissingRegistration] = useState(null);
    const [refreshChildren, setRefreshChildren] = useState(false);
    const [buses, setBuses] = useState(null);
    const [csrfToken, setCsrfToken] = useState([]);

    const [tripSupervisors, setTripSupervisors] = useState();

    useEffect(Fetch('/csrf', setCsrfToken), []);
    useEffect( Fetch('/trips/stops/' + trip.id, setTripStops), []);
    useEffect( Fetch('/trips/registration/children/' + trip.id, setRegisteredChildren), [refreshChildren]);
    useEffect( Fetch('/trips/registration/staff/' + trip.id, setRegisteredStaff), []);
    useEffect( Fetch('/trips/registration/' + trip.id + "/missing", setMissingRegistration), [refreshChildren]);
    useEffect(Fetch('/trips/buses/' + trip.id, setBuses), []);

    const toggleRefreshChildren = () => {
        setRefreshChildren(!refreshChildren);
    }

    let childRegistrationTag = [], today=new Date();
    if(trip.departureDate < today){
        childRegistrationTag.push(<AddChildRegistration tripId={trip.id} notRegistered={missingRegistration} bus={buses} refresh={toggleRefreshChildren} token={csrfToken.token} key={0}/>)
    }

    return (<View style={globalStyle.container}>
            <TouchableOpacity onPress={() => {window.open("/trips", "_self")}} style={[globalStyle.button, globalStyle.rightSideButton]}>
                <Text>Go back to Trips</Text>
            </TouchableOpacity>
            <Text style={globalStyle.title}>Information about the trip</Text>
            <Details trip={trip} />
            <Stops stops={tripStops}/>
            {childRegistrationTag}
            <Summary children={registeredChildren} staff={registeredStaff} buses={buses} />
        </View>
    );
}

//<AddChildRegistration tripId={trip.id} notRegistered={missingRegistration} bus={buses} refresh={toggleRefreshChildren} token={csrfToken.token}/>

const Details = ({trip}) => {

    return (<View style={globalStyle.container}><Text style={globalStyle.subTitle}>Trip Details</Text>
            <Text>Destination: {trip.arrivalCity}</Text>
            <Text>Duration: {trip.departureDate} - {trip.returnDate}</Text>
            <Text>Departing from: {trip.departureCity}</Text>
        </View>
    );
}

const stopsPropExtractor = (stop) => {
    return [stop.stopName, stop.arrivalTime, stop.departingTime];
}

const Stops = ({stops}) => {
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

            return (<View style={globalStyle.container}>
                    <Text style={globalStyle.subTitle}>Stops</Text>
                    <Text style={style.subSubTitle}>On the way there</Text>
                    <TableComponent nameColumns={["Stop", "Arrival", "Departure"]} list={departureStops}
                                    propExtractor={stopsPropExtractor} onPress={null}/>
                    <Text style={style.subSubTitle}>On the way back</Text>
                    <TableComponent nameColumns={["Stop", "Arrival", "Departure"]} list={returnStops}
                                    propExtractor={stopsPropExtractor} onPress={null}/>
                </View>
            )
        }
    } else {
        return (<View style={globalStyle.container}>
            <Text style={globalStyle.subTitle}>Stops</Text>
            <Text>This trip does not have any stops</Text>
        </View>)
    }
}

const ChildRegistered = ({registeredChildren}) => {
    if(registeredChildren) {
        if(registeredChildren.length > 0) {
            return (<View style={globalStyle.container}>
                <Text style={globalStyle.subTitle}>Child Registered for this Field Trip</Text>
                <TableComponent nameColumns={["Child", "Gruppo", "Bus"]} list={registeredChildren} propExtractor={registeredPropExtractor}/>
            </View>)
        }
    } else {
        return (<View style={globalStyle.container}><Text style={globalStyle.subTitle}>There are no registrations</Text>
            <Text>No children have been registered for this trip!</Text>
        </View>)
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

const registeredPropExtractor = (registration) => {
    return [registration.child.name + " " + registration.child.surname, registration.group, registration.bus];
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
        PostRequest("/trips/register", formData, () => {}, token, refresh)();
        console.log(formData);
        setSelectedChild([]);
        setSelectedGroup([]);
        setSelectedBus([]);
        updateFormData(initialFormData);
    }

    return (<View>
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


//TODO when pressing a single stop a modal will appear with the stops attendances??

const style = StyleSheet.create({
    subSubTitle: {
        fontSize: 20,
    }
})

export {TripDetails};