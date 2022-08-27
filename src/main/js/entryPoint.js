import React from 'react';
import { Home } from './home.js';
import { Details } from './details.js'; //no import Anagrafica
import { Appointments } from './appointments'
import ReactDOM from 'react-dom/client';
import {Attendance} from "./attendance";
import {AddChild} from "./addChild";
import {Cafeteria} from "./cafeteria";
import {Trips} from "./trips";

let component, idContainer;

if(document.getElementById('home')){
    component = <Home />;
    idContainer = 'home';
} else if (document.getElementById('details')){
    component = <Details />;
    idContainer = 'details';
} else if (document.getElementById('appointments')) {
    component = <Appointments />;
    idContainer = 'appointments';
} else if (document.getElementById('attendance')) {
    component = <Attendance />;
    idContainer = 'attendance';
} else if(document.getElementById('addChild')){
    component = <AddChild />;
    idContainer = 'addChild';
} else if(document.getElementById('cafeteria')){
    component = <Cafeteria />;
    idContainer = 'cafeteria';
} else if(document.getElementById('trips')){
    component = <Trips />;
    idContainer = 'trips';
}

const root = ReactDOM.createRoot(document.getElementById(idContainer));
root.render(component);

export { root };