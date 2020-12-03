// Stores map locations after retrieval
var map_locations;
var preference = "n/a";
var polyline;
var start_node;
var end_node;

function populate_dropdowns() {
    let from = document.getElementById("fromDrop");
    let to = document.getElementById("toDrop");
    from.length = 0;
    to.length = 0;

    let defaultOption = document.createElement("option");
    defaultOption.text = "Choose a location...";

    from.add(defaultOption);
    from.selectedIndex = 0;
    to.add(defaultOption.cloneNode(true));
    to.selectedIndex = 0;

    const request = new XMLHttpRequest();
    request.open("GET", "/locations", true);

    request.onload = function () {
        if (request.status === 200) {
            const data = JSON.parse(request.responseText).locations;
            let option;
            for (let i = 0; i < data.length; i++) {
                option = document.createElement("option");
                option.text = data[i].name;
                option.id = data[i].id;
                from.add(option);
                to.add(option.cloneNode(true));
            }
        } else {
            console.log("Server returned an error...")
        }
    }

    request.onerror = function () {
        console.error("An error occurred with populating the dropdowns.");
    };

    request.send();
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
    for (var i = 0; i < path.length; i++) {
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
    document.getElementById("formAlert").classList.add("d-none")

    let xhr = new XMLHttpRequest();
    let from = document.getElementById("fromDrop");
    from = from[from.selectedIndex].id;
    let to = document.getElementById("toDrop");
    to = to[to.selectedIndex].id;

    if (from == "" || to == "") {
        document.getElementById("formAlert").classList.remove("d-none")
        return;
    }

    console.log("Requesting navigation from", from, "to", to);

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
    populate_dropdowns();
    $("#option1").click(function () { preference = "elevator" });
    $("#option2").click(function () { preference = "stairs" });
    $("#option3").click(function () { preference = "n/a" });
    $("#go").click(submit_navigation_request);
    $("#reset").click(reset_navigation);
    $("#exit").click(exit_application);
    window.onunload = exit_application;
}

$(document).ready(initialize);