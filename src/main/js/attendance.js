import React, {useEffect, useState} from "react";
import {Fetch} from "./networkUtils";
import { View, Text} from "react-native-web";

const Attendance = () => {
    const [attendance, setAttendance] = useState([]);
    useEffect( Fetch ('/attendance/children', setAttendance), []);

    return (
        <View><Text>Hi, you are getting the child attendances</Text></View>
    );
}

export {Attendance };