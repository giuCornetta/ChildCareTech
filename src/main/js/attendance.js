import React, {useEffect, useState} from "react";
import {Fetch} from "./networkUtils";
import {View, Text, StyleSheet, TouchableOpacity} from "react-native-web";
import DatePicker from "react-datepicker";
import 'react-datepicker/dist/react-datepicker.css';
import {globalStyle} from "./globalStyle";
import {TableComponent} from "./table"; //FIXME for mobile use react-native-datepicker


const Attendance = () => {
    let today = new Date();
    const [date, setDate] = useState(today);
    const [attendance, setAttendance] = useState(today);
    useEffect( Fetch ('/attendance/children/' + date.toISOString().substring(0, 10), setAttendance), [date]);
    //Fetch ('/attendance/children/' + date.toISOString().substring(0, 10), setAttendance);


    return (
        <View style={globalStyle.container}>
            <TouchableOpacity onPress={() => {window.open("/", "_self")}} style={[globalStyle.button, globalStyle.rightSideButton]}>
                <Text>Go back to Home</Text>
            </TouchableOpacity>
            <Text style={globalStyle.title}>Attendances</Text>
            <View style={globalStyle.container}>
                <DatePicker

                    style={ style.leftSide}
                    selected={date}
                    onChange={(date) => {
                        setDate(date);
                        //Fetch ('/attendance/children/' + date.toISOString().substring(0, 10), setAttendance);
                    }}
                    minDate={new Date("08-19-2022")}
                    maxDate={today}
                    popperPlacement="bottom"
                />
            </View>
            <TableComponent style={style.table} nameColumns={["Name", "Surname", "Entrance Time", "Exit Time"]} list={attendance} propExtractor={propExtractor} onPress={null}/>
        </View>
    );
}


const propExtractor = (attendance) => {
    let name, surname, entranceTime, exitTime;
    if(attendance){
        name = attendance.child.name;
        surname = attendance.child.surname;
        entranceTime = attendance.entranceTime;
        exitTime = attendance.exitTime;
    } else {
        name = "";
        surname = "";
        entranceTime = "";
        exitTime = "";
    }

    return [name, surname, entranceTime, exitTime];
}
const style = StyleSheet.create({
    leftSide: {
            alignSelf: 'flex-end',
            //marginTop: -5,
            position: 'absolute',
        },
    table: {
        position: 'relative',
    }
});



/*
* <DatePicker

            date={today} // Initial date from state
            mode="date" // The enum of date, datetime and time
            placeholder="select date"
            format="DD-MM-YYYY"
            minDate="19-08-2022"
            maxDate={today}
            confirmBtnText="Confirm"
            cancelBtnText="Cancel"
            customStyles={{
                dateIcon: {
                    //display: 'none',
                    position: 'absolute',
                    left: 0,
                    top: 4,
                    marginLeft: 0,
                },
                dateInput: {
                    marginLeft: 36,
                },
            }}
            onDateChange={(date) => {
                setDate(date);
                useEffect( Fetch ('/attendance/children', setAttendance), []);
            }}
        /> */

//style={styles.datePickerStyle}

export {Attendance };