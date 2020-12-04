var preference = "n/a";
var polyline;
var start_node;
var end_node;

// function that stores the floor1locations.csv in each dropdown list and sorts it
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
            // Sort the inputs in descending order to properly display in the drop down list
            data.sort(function(a, b){
                return a.name.localeCompare(b.name);
            });
            let option;
            for (let i = 0; i < data.length; i++) {
                option = document.createElement("option");
                // Added a check to bypass the column names from the CSV file so that we do not add a location called "NAME"
                if(data[i].name === "NAME" && data[i].id === "ID") {continue;}
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

// function to reset the navigation system
function reset_navigation() {
    if (!(polyline == null))
        polyline.remove();
    if (!(start_node == null))
        start_node.remove();
    if (!(end_node == null))
        end_node.remove();
}

// function to keep the map in a certain position when opened
function focus_map(x, y) {
    $("svg").remove();
    var new_svg = document.createElement("img");
    new_svg.setAttribute("src", "OKTFloor1.svg");
    new_svg.setAttribute("easypz", '{"applyTransformTo": "svg > *", "options": { "minScale": 0.5, "maxScale": 10, "bounds": { "top": "NaN", "right": "NaN", "bottom": "NaN", "left": "NaN" }} }');
    xTransform = x - 960;
    yTransform = y - 540;
    $("#svg-container").append(new_svg);
    SVGInject(new_svg);
    $("svg")[0].setAttribute("viewBox", "" + xTransform + " " + yTransform
            + " 1920 1080");
}

// function that focuses on the navigate toolbar and changes the two points of location once they are found
function process_navigation_response(response) {
    reset_navigation();
    console.log(response);
    path = response.path;
    
     
    // starting location
    start_node = document.createElementNS("http://www.w3.org/2000/svg", "circle");
    // destination
    end_node = document.createElementNS("http://www.w3.org/2000/svg", "circle");
    
   
    focus_map(path[0].x, path[0].y);
    
    start_node.setAttribute("cx", path[0].x);
    start_node.setAttribute("cy", path[0].y);
    start_node.setAttribute("r", "10");
    start_node.setAttribute("transform", $("defs").attr("transform"));
    // once the start location is found it turns blue
    start_node.setAttribute("fill", "blue");

    end_node.setAttribute("cx", path[path.length - 1].x);
    end_node.setAttribute("cy", path[path.length - 1].y);
    end_node.setAttribute("r", "10");
    end_node.setAttribute("transform", $("defs").attr("transform"));
    // once the destination is found it turns green
    end_node.setAttribute("fill", "green");

   
    

   
   
    
    // line connecting the two locations
    polyline = document.createElementNS("http://www.w3.org/2000/svg", "polyline");
    polyline.setAttribute("fill-opacity", "0");
    polyline.setAttribute("stroke-width", "6");
    polyline.setAttribute("stroke", "#FF7F11");
    var points = "";
    for (var i = 0; i < path.length; i++) {
        console.log(path[i]);
        points += path[i].x + "," + path[i].y + " ";
    }
    
    // calculate the distance of the connected nodes
    var distance = response.length;
    distance=(distance/50)*6;
    document.getElementById("distance").innerHTML = distance.toFixed(2);
    
    // calculate the time by multiplying the distance by the average speed of a person walking and dividing it by 60 to convert to minutes
    var time = (distance/5.13333)/60;
    document.getElementById("time").innerHTML = time.toFixed(2);
    
    polyline.setAttribute("points", points);
    //polyline.setAttribute("class", "cls-1");
    polyline.setAttribute("transform", $("defs").attr("transform"));

    $("#Layer_2").append(polyline);
    $("#Layer_2").append(start_node);
    $("#Layer_2").append(end_node);
}

// function to alert the user
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

// function that exits the application
function exit_application() {
    let xhr = new XMLHttpRequest();
    xhr.open("POST", "/exit", true);
    xhr.send();
    
    // Exit on acknowledgement
    xhr.onload = function () {
        close();
    }
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
