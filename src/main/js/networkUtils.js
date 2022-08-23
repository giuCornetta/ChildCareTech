import React from 'react';

const Fetch = function(_url, _setter) {

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

const PostRequest = (_url, _responseBody , _csrf) => {
    console.log("starting post")
    console.log("_csrf" + _csrf)

    return ( () => {
        console.log("entered in return")
        console.log("_csrf" + _csrf)
        fetch(_url, {
            credentials: 'include',
            method: 'POST',
            body: JSON.stringify(_responseBody),
            headers: {
                'Accept' : 'application/json',
                'Content-Type': 'application/json',
                'X-CSRF-TOKEN': _csrf,
            },
        })
            .then((response) => response.json())
            .then((data) => {
                console.log(data);
            })
            .catch((err) => {
                console.log(err.message);
            });
    });
}

export { Fetch, PostRequest };