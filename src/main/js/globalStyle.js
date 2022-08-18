import {StatusBar, StyleSheet} from "react-native-web";

const globalStyle = StyleSheet.create({

    row: {
        flex: 1,
        alignSelf: 'stretch',
        flexDirection: 'row'
    },
    cell: {
        flex: 1,
        alignSelf: 'stretch',
        borderBottomColor: "black",
        borderBottomWidth: StyleSheet.hairlineWidth,
        borderLeftColor : "black",
        borderLeftWidth : StyleSheet.hairlineWidth,
        borderRightColor: "black",
        borderRightWidth: StyleSheet.hairlineWidth,

    },
    insideCell: {
        margin: 10,
        paddingVertical: 10,
    },
    header: {
        backgroundColor :  '#E0FFFF',
        borderTopColor: "black",
        borderTopWidth: StyleSheet.hairlineWidth
    },
    container: {
        flex: 1,
        marginTop: StatusBar.currentHeight || 0,
        textAlign: 'center',
        padding: 20, //20
        marginVertical: 8, //8
        marginHorizontal: 16, //16
        justifyContent: 'center',
        alignItems: 'center'
    },
    title: {
        fontSize: 32
    },
    subTitle: {
        fontSize : 25,
        margin : 5
    },
    table: {
        width: '90%',
        justifyContent: 'center',
        alignItems: 'center'
    },
    button: {
        backgroundColor: "#DDDDDD",
        padding: 10,
    },
    tableButton: {
        textDecorationLine: 'underline',
    },
    rightSideButton: {
        alignSelf: 'flex-end',
        marginTop: -5,
        position: 'above',
    },
    centerButton: {
        alignSelf: 'center',
    },
    reminder: {
        color: 'red',
    },
    reminderButton: {
        backgroundColor: '#f5938c',
    }
});

export { globalStyle };