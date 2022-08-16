import React from 'react';
import { Home } from './home.js';
import { Anagrafica } from './anagrafica.js'; //no import Anagrafica
import { Contatti } from './contatti.js';
import ReactDOM from 'react-dom/client';

let component, idContainer;

if(document.getElementById('home')){
    component = <Home />;
    idContainer = 'home';
} else if (document.getElementById('anagrafica')){
    component = <Anagrafica />;
    idContainer = 'anagrafica';
} /*else if (document.getElementById('contatti')){
    component = <Contatti />;
    idContainer = 'contatti';
}*/

const root = ReactDOM.createRoot(document.getElementById(idContainer));
root.render(component);

export { root };