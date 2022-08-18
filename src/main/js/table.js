import {Pressable, Text, View} from "react-native-web";
import {globalStyle} from "./globalStyle";
import React, {useState} from "react";

const TableRow = ({numColumns, object, onPress, selectedRow, index}) => {
    let bgColor = selectedRow === index ? 'rgba(61, 143, 118, 0.73)' : 'transparent';
    let buttonBGColor = selectedRow === index ? 'rgba(43, 100, 83, 0.73)' : '#DDDDDD';
    let buttonTextColor = selectedRow === index ? '#DDDDDD' : 'blue';

    let columns = [];
    let i;
    for(i=0; i<numColumns; i++){
        columns.push(<View key={i} style={globalStyle.cell}><Text style={globalStyle.insideCell}>{object[i]}</Text></View>)
    }
    columns.push(<View key={i} style={globalStyle.cell}><Pressable onPress={() => {onPress()}} style={[globalStyle.insideCell, {backgroundColor: buttonBGColor}]}><Text style={[globalStyle.tableButton, {color: buttonTextColor}]}>Show more...</Text></Pressable></View>)
    return (
        <View style={[globalStyle.row, {backgroundColor: bgColor}]}>
            {columns}
        </View>
    );
}

const TableHeader = ({nameColumns}) => {
    let i;
    let columns = [];
    for(i=0; i<nameColumns.length; i++){
        columns.push(<View key={i} style={[globalStyle.cell, globalStyle.header]}><Text>{nameColumns[i]}</Text></View>)
    }
    columns.push(<View key={nameColumns.length} style={[globalStyle.cell, globalStyle.header]}><Text>Details</Text></View>)
    return (
        <View style={globalStyle.row}>
            {columns}
        </View>
    )
}

const TableComponent = ({nameColumns, list, propExtractor, onPress}) => {
    const [selectedRow, setSelectedRow] = useState([]);

    let array = Array.from(list);
    let correctList = array.map(propExtractor);


    let rows = [];
    for (let i = 0; i < correctList.length; i++) {
        rows.push(<TableRow key={i} numColumns={nameColumns.length} object={correctList[i]} onPress={() => {onPress(list[i]); setSelectedRow(i)}} selectedRow={selectedRow} index={i}/>);
    }
    //tableRow(list[i]);
    return (<View style={[globalStyle.table, globalStyle.container]}>
        <TableHeader nameColumns={nameColumns}/>
        {rows}
    </View>)

}

export { TableComponent };