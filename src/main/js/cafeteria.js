import {globalStyle} from "./globalStyle";
import {Pressable, Text, View, StyleSheet, TouchableOpacity} from "react-native-web";
import React, {useEffect, useState} from "react";
import {Fetch, PostRequest} from "./networkUtils";
import Select from "react-select";

const Cafeteria = () => {
    let today = new Date();
    const [menu, setMenu] = useState(null);
    const [csrfToken, setCsrfToken] = useState([]);
    const [date, setDate] = useState(new Date());
    const [orders, setOrders] = useState(null);
    const [missingOrders, setMissingOrders] = useState(null);
    const [childrenFirstCourse, setChildrenFirstCourse] = useState({});
    const [childrenSecondCourse, setChildrenSecondCourse] = useState({});
    const [childrenSideDish, setChildrenSideDish] = useState({});
    const [childrenDessert, setChildrenDessert] = useState({});
    const [childrenFruit, setChildrenFruit] = useState({});
    const [refresh, setRefresh] = useState(false);

    useEffect( Fetch('/cafeteria/menu/' + date.toISOString().substring(0, 10), setMenu), [date]);
    useEffect(Fetch('/csrf', setCsrfToken), []);
    useEffect( Fetch('/cafeteria/history/orders/' + date.toISOString().substring(0, 10), setOrders), [date, refresh]);
    useEffect( Fetch('/cafeteria/history/orders/missing/' + date.toISOString().substring(0, 10), setMissingOrders), [date, refresh]); //This way when an order is made the list is refreshed

    useEffect( () => {
        if(menu){
            Fetch("/cafeteria/order/" + menu.firstCourse.id + "/" + date.toISOString().substring(0, 10), setChildrenFirstCourse)()
        }
    }, [refresh, menu]);
    useEffect( () => {
        if(menu)
            Fetch("/cafeteria/order/" + menu.secondCourse.id + "/" + date.toISOString().substring(0, 10), setChildrenSecondCourse)()
    }, [refresh, menu]);
    useEffect( () => {
        if(menu)
            Fetch("/cafeteria/order/" + menu.sideDish.id + "/" + date.toISOString().substring(0, 10), setChildrenSideDish)()
    }, [refresh, menu]);
    useEffect( () => {
        if(menu)
            Fetch("/cafeteria/order/" + menu.dessert.id + "/" + date.toISOString().substring(0, 10), setChildrenDessert)()
    }, [refresh, menu]);
    useEffect( () => {
        if(menu)
            Fetch("/cafeteria/order/" + menu.fruit.id + "/" + date.toISOString().substring(0, 10), setChildrenFruit)()
    }, [refresh, menu]);


    const toggleRefresh = () => {
        setRefresh(!refresh);
    }


    let listOfChildren, dishes, ordersTag = [];
    if(menu){
        listOfChildren = [childrenFirstCourse, childrenSecondCourse, childrenSideDish, childrenDessert, childrenFruit];
        dishes = [menu.firstCourse, menu.secondCourse, menu.sideDish, menu.dessert, menu.fruit];
        if(today < date || (today.getFullYear() === date.getFullYear() && today.getMonth() === date.getMonth() && today.getDate() === date.getDate())){
            ordersTag.push(<TableComponent menu={dishes} children={listOfChildren} date={date} refresh={toggleRefresh} csrfToken={csrfToken} key={0}/>);
        }
    } else {
        ordersTag.push(<Text key={0}>The menu for this day is not ready yet!</Text>)
    }

    let maxDay = new Date();
    maxDay.setDate(maxDay.getDate() + 7);

    const handleChange = (e) => {
        let date = new Date(e.target.value);
        let minDate = new Date("2022-08-25");
        if(!(date < minDate || maxDay < date)){
            setDate(date);
        }
    }


    return (<View style={globalStyle.container}>
        <TouchableOpacity onPress={() => {window.open("/", "_self")}} style={[globalStyle.button, globalStyle.rightSideButton]}>
            <Text>Go back to Home</Text>
        </TouchableOpacity>
        <form>
            <input type="date" value={date.toISOString().substring(0, 10)} min="2022-08-25" max={maxDay.toISOString().substring(0, 10)} onChange={handleChange}/>
        </form>
        {ordersTag}
        <Ordered orders={orders}/>
        <MissingOrders children={missingOrders}/>
    </View>);
}


const Ordered = ({orders}) => {
    if(orders && orders.length> 0) {
        let ordersTag = [];
        let idChild = Infinity;
        let j = 0;
        for (let i = 0; i < orders.length; i++) {
            if (orders[i].child.id !== idChild) {
                idChild = orders[i].child.id;
                ordersTag.push(<Text key={j} style={globalStyle.subTitle}>Ordine
                    di {orders[i].child.name} {orders[i].child.surname}</Text>);
                j++;
                ordersTag.push(<Text
                    key={j}><b>{orders[i].dish.course}:</b> {orders[i].dish.name}{(orders[i].dish.description) ? (", " + orders[i].dish.description) : ""}
                </Text>);
            } else {
                ordersTag.push(<Text
                    key={j}><b>{orders[i].dish.course}:</b> {orders[i].dish.name}{(orders[i].dish.description) ? (", " + orders[i].dish.description) : ""}
                </Text>);
            }
            j++;
        }

        return (<View style={globalStyle.container}>
            <Text style={globalStyle.title}>Orders made for the Day</Text>
            {ordersTag}
        </View>);
    }


}

const MissingOrders = ({children}) => {
    if(children && children.length>0) {
        let missing = [];
        for (let i = 0; i < children.length; i++) {
            missing.push(<Text key={i}><b>{children[i].name} {children[i].surname}</b></Text>)
        }

        return (<View style={globalStyle.container}>
            <Text style={globalStyle.title}>Children Who have not yet Ordered</Text>
            {missing}
        </View>)
    }
}

const TableRow = ({dish, children, csrfToken, refresh, date}) => { //children : i bambini che possono mangiare quel piatto

    const initialFormData = {
        primarykey: {
            idChild: "",
            idDish: dish.id,
            date: date,
        }
    }
    const [formData, updateFormData] = useState(initialFormData);
    const [selectedChild, setSelectedChild] = useState([]);

    const handleChange = (e) => {
        setSelectedChild(e);
        updateFormData({
            primarykey: {
                idChild: e.value,
                idDish: dish.id,
                date: date,
            }
        })

    }

    const handleSubmit = () => {
        PostRequest("/cafeteria/order", formData, () => {}, csrfToken.token, refresh)();
        console.log(formData);
        setSelectedChild([]);
        updateFormData(initialFormData);
    }

    return (
        <View style={globalStyle.row}>

            <View style={globalStyle.cell}><Text>{dish.name}</Text></View>
            <View style={globalStyle.cell}><Text>{dish.course}</Text></View>
            <View style={globalStyle.cell}><Text>{dish.description}</Text></View>
            <View style={globalStyle.cell}>
                <label>
                    Choose a Child:<Select
                    options={children}
                    name="staff"
                    onChange={handleChange}
                    menuPortalTarget={document.querySelector('body')}
                    value ={selectedChild}
                    style={style.select}
                /></label>
            </View>
            <View style={globalStyle.cell}><Pressable onPress={handleSubmit} style={[globalStyle.row, globalStyle.button]}>
                <Text>Click Here to add</Text></Pressable></View>
        </View>
    );
}

const TableHeader = () => {
    return (
        <View style={globalStyle.row}>
            <View style={[globalStyle.cell, globalStyle.header]}><Text>Dish</Text></View>
            <View style={[globalStyle.cell, globalStyle.header]}><Text>Course</Text></View>
            <View style={[globalStyle.cell, globalStyle.header]}><Text>Description</Text></View>
            <View style={[globalStyle.cell, globalStyle.header]}><Text>Children</Text></View>
            <View style={[globalStyle.cell, globalStyle.header]}><Text>Add</Text></View>
        </View>
    )
}

const TableComponent = ({menu, children, refresh, csrfToken, date}) => {
    if(menu && children) {
        let rows = [];
        for (let i = 0; i < menu.length; i++) {
            rows.push(<TableRow key={i} dish={menu[i]} children={children[i]} date={date} refresh={refresh}
                                csrfToken={csrfToken}/>);
        }


        return (<View style={[globalStyle.table, globalStyle.container]}>
            <TableHeader/>
            {rows}
        </View>)
    }

}

const style = StyleSheet.create({
    select: {
        overflow: "visible",
    }
})

export { Cafeteria };