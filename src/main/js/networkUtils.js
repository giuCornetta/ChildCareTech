import React from 'react';

const Fetch = function(_url, _setter) {
    /*const url = _url;
    const setter = _setter;*/

    return ( () => {
        fetch(_url)
            .then((response) => response.json())
            .then((data) => {
                console.log(data);
                _setter(data);
            })
            .catch((err) => {
                console.log(err.message);
            });
    });
}

export { Fetch };