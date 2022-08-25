
function padTo2Digits(num) {
    return num.toString().padStart(2, '0');
}

function formatDate(string) {
    let date = new Date(string);
    return [
        padTo2Digits(date.getDate()),
        padTo2Digits(date.getMonth() + 1),
        date.getFullYear(),
    ].join('/');
}

export { formatDate };