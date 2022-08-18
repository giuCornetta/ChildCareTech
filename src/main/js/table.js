import {Pressable, Text, View} from "react-native-web";
import {globalStyle} from "./globalStyle";
import React from "react";

const TableRow = ({numColumns, object, onPress}) => {
    let columns = [];
    for(let i=0; i<numColumns; i++){
        columns.push(<View key={i} style={globalStyle.cell}><Text>{object[i]}</Text></View>)
    }
    return (
        <View style={globalStyle.row}>
            <Pressable onPress={() => onPress()} style={globalStyle.row}>
                {columns}
            </Pressable>
        </View>
    );
}

const TableHeader = ({nameColumns}) => {
    let columns = [];
    for(let i=0; i<nameColumns.length; i++){
        columns.push(<View key={i} style={[globalStyle.cell, globalStyle.header]}><Text>{nameColumns[i]}</Text></View>)
    }
    return (
        <View style={globalStyle.row}>
            {columns}
        </View>
    )
}

const TableComponent = ({nameColumns, list, propExtractor, onPress}) => {

    let array = Array.from(list);
    let correctList = array.map(propExtractor);


    let rows = [];
    for (let i = 0; i < correctList.length; i++) {
        rows.push(<TableRow key={i} numColumns={nameColumns.length} object={correctList[i]} onPress={() => onPress(list[i])}/>);
    }
    //tableRow(list[i]);
    return (<View style={[globalStyle.table, globalStyle.container]}>
        <TableHeader nameColumns={nameColumns}/>
        {rows}
    </View>)

}

export { TableComponent };