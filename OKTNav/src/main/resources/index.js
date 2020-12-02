// Stores map locations after retrieval
var map_locations;
var preference = "n/a";
var path_line;

function retrieve_map_locations() {
    // TODO
}

function process_navigation_response(response) {
    console.log(response);
    path = response.path;
    var polyline = document.createElementNS("http://www.w3.org/2000/svg", "polyline");
    polyline.setAttribute("fill", "white");
    polyline.setAttribute("stroke-width", "6");
    polyline.setAttribute("stroke", "#BBC42A");
    var points = "";
    for(var i = 1; i < path.length; i++) {
        console.log(path[i]);        
        points += path[i].x + "," + path[i].y + " ";
    }
    polyline.setAttribute("points", points);
    polyline.setAttribute("class", "cls-1")
    polyline.setAttribute("transform", $("defs").attr("transform"));
    $("#Layer_2").append(polyline);
    path_line = polyline;
}

function submit_navigation_request() {
    let xhr = new XMLHttpRequest();
    var from = $("#from").find(":selected").text();
    var to = $("#to").find(":selected").text();    
    
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
    $("#option1").click(function() {preference="elevator"});
    $("#option2").click(function() {preference="stairs"});
    $("#option3").click(function() {preference="n/a"});
    $("#go").click(submit_navigation_request);
    $("#exit").click(exit_application);
    window.onunload = exit_application;
}

$(document).ready(initialize);