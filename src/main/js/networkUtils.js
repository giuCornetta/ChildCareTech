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

const PostRequest = (_url, _responseBody , _setter, _csrf, _refresh) => {

    return ( () => {
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
            .then((response) => {
                    if(response.status===302){
                        window.open(response.headers.get("Location"), "_self");
                    }
                    else{
                        response.json();
                    }
                }
            )
            .then((data) => {
                console.log(data);
                _setter(data);
                _refresh();
            })
            .catch((err) => {
                console.log(err.message);
            });
    });
}

export { Fetch, PostRequest };