// Stores map locations after retrieval
var map_locations;
var preference = "n/a";
var polyline;
var start_node;
var end_node;

function retrieve_map_locations() {
    // TODO
}

function reset_navigation() {
    if (!(polyline == null))
        polyline.remove();
    if (!(start_node == null))
        start_node.remove();
    if (!(end_node == null))
        end_node.remove();
}

function process_navigation_response(response) {
    reset_navigation();
    console.log(response);
    path = response.path;

    start_node = document.createElementNS("http://www.w3.org/2000/svg", "circle");
    end_node = document.createElementNS("http://www.w3.org/2000/svg", "circle");

    start_node.setAttribute("cx", path[0].x);
    start_node.setAttribute("cy", path[0].y);
    start_node.setAttribute("r", "10");
    start_node.setAttribute("transform", $("defs").attr("transform"));
    start_node.setAttribute("fill", "blue");

    end_node.setAttribute("cx", path[path.length - 1].x);
    end_node.setAttribute("cy", path[path.length - 1].y);
    end_node.setAttribute("r", "10");
    end_node.setAttribute("transform", $("defs").attr("transform"));
    end_node.setAttribute("fill", "green");


    polyline = document.createElementNS("http://www.w3.org/2000/svg", "polyline");
    polyline.setAttribute("fill-opacity", "0");
    polyline.setAttribute("stroke-width", "6");
    polyline.setAttribute("stroke", "#BBC42A");
    var points = "";
    for (var i = 1; i < path.length; i++) {
        console.log(path[i]);
        points += path[i].x + "," + path[i].y + " ";
    }
    polyline.setAttribute("points", points);
    //polyline.setAttribute("class", "cls-1");
    polyline.setAttribute("transform", $("defs").attr("transform"));

    $("#Layer_2").append(polyline);
    $("#Layer_2").append(start_node);
    $("#Layer_2").append(end_node);
}

function submit_navigation_request() {
    let xhr = new XMLHttpRequest();
    var from = $("#from").find(":selected").text();
    var to = $("#to").find(":selected").text();

    xhr.open("GET", "/navigate?from=" + from + "&to=" + to + "&pref=" + preference, true);
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
    // Delay to ensure there is suffient time to send request
    setTimeout(() => { close(); }, 1000);
}

function initialize() {
    // Tasks
    // -Retrieve list of navigable locations from app
    // -Assign functions to buttons
    retrieve_map_locations();
    $("#option1").click(function () { preference = "elevator" });
    $("#option2").click(function () { preference = "stairs" });
    $("#option3").click(function () { preference = "n/a" });
    $("#go").click(submit_navigation_request);
    $("#reset").click(reset_navigation);
    $("#exit").click(exit_application);
    window.onunload = exit_application;
}

$(document).ready(initialize);