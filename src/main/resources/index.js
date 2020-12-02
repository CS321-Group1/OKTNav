// Stores map locations after retrieval
var map_locations;
var test;

function retrieve_map_locations() {
    // TODO
}

function process_navigation_response(response) {
    console.log(response);
    test = response;
}

function submit_navigation_request() {
    let xhr = new XMLHttpRequest();
    var from = $("#from").find(":selected").text();
    var to = $("#to").find(":selected").text();
    var preference = "n/a";
    
    xhr.open("GET", "/navigate?from="+from+"&to="+to+"&pref="+preference, true);
    xhr.send();
    
    xhr.onreadystatechange = function (e) {
        if (xhr.readyState == 4) {
            let response = JSON.parse(xhr.responseText);
            process_navigation_response(response);
        }
    };
}

function exit_application() {
    let xhr = new XMLHttpRequest();
    xhr.open("POST", "/exit", true);
    xhr.send();
}

function initialize() {
    // Tasks
    // -Retrieve list of navigable locations from app
    // -Assign functions to buttons
    retrieve_map_locations();
    $("#go").click(submit_navigation_request);
    $("#exit").click(exit_application);
    window.onunload = exit_application;
}

$(document).ready(initialize);