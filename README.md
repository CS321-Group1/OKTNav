# OKTNav
<!-- ![Olin B. King Technology Hall](https://i.ibb.co/rdG8pK2/image1.png) -->

## Overview
Our project aims to create a navigation aid for UAH’s Olin B. King Technology Hall. OKT is home to many of the computer science courses offered at UAH, however, the interior layout and design of the building often leads to confusion when trying to find where classrooms or offices are located. Our goal is to create an easy to use software that will help students, faculty, or visitors navigate directly to their location in the shortest path possible.
The software will guide users from any entrance to OKT to rooms within the building, as well as between two rooms inside the building. This guidance will take special effort to prioritize both accessibility and efficiency when routing users from place to place.

## Functional Requirements
#### Actors:
Our OKT Nav software will have a single actor, the OKT Visitor. This is a person who wants to navigate to somewhere within OKT. They are expected to know their original location within or outside of OKT. They are also expected to know where within OKT they want to go and be able to read a floor plan diagram with a highlighted route. They are assumed to be computer literate and able to navigate through the app (e.g. start and exit applications, select items from dropdowns, select choices from radio buttons, click buttons).

Overall Flow:
1. User opens the application.
2. User will input the location and destination they want to navigate to.
3. The System will provide a highlighted route from the starting location to the destination.
4. User will be able to follow the route found.
5. User can exit the application.

#### Use Case Diagram:
![UseCaseDiagram](https://i.ibb.co/n8jBmcW/image2.png)

#### Use Case #1:
##### Receive OKT Navigation
**Description**: User receives navigation within OKT from the System
**Actor**: OKT Visitor
**Precondition**: None
##### Scenario:
1. User opens the app.
2. The System presents the User with three user inputs:  a starting location (defaulting to the main front door), a destination (defaulting to Blue’s Tech Hall), and a choice selecting between ‘elevators only’, ‘stairs only’, or ‘no preference’ (defaulting to ‘no preference’).
3. The User will select their locations from a list and choose a stairs/elevator choice (default to ’no preference’).
4. Then, the User selects ‘Navigate’ to confirm their selections.
5. The System will then present the User with a floor plan of OKT with a route highlighted from their starting point to their destination. If the route spans multiple floors, all of the floors concerned will be presented with navigation routes to and from the appropriate elevator or stair connections.
6. Return to step 2.
	
> **Alternate Flow A** - Exit App:
1. From any step 2-6, the User selects ‘Exit’
2. The application closes
> **Alternate Flow B** - Navigate from Defaults:
1. From step 2, user skips steps 3 and 4 and directly executes step 5
2. The User will be presented with a map with a floor plan of OKT with a route highlighted from the main front door to Blue’s Tech Hall

#### Functional Requirements Summary:

Req | Requirement Statement | Use Case Step
--- | --------------------- | -------------
1 | The application shall allow the user to provide their beginning destination from an outside door or an interior room of the building. | 1.2
2 | The application shall allow the user to provide their ending destination from inside the building to a specific classroom, or outside the building to a specific exit. | 1.2
3 | The application shall allow the user to toggle their preferred routing option between solely using elevators, solely using stairs, or no preference. | 1.3
4 | The application shall allow the user to initiate the plotting of their route. | 1.4
5 | The application shall populate the OKT floor plan with a shortest distance walking route from the selected beginning point to the selected destination point in ten or fewer seconds. | 1.5
6 | The application shall allow the user to reconfigure their navigation inputs and generate a new route. | 1.6
7 | The application shall generate paths across different floors of the building to allow the user to navigate using elevators or stairs, dependent on the user’s preferences. | 1.5
8 | The application shall allow the user to exit the application. | 1.Alternate A.2

## UML Diagram
![UML](https://i.ibb.co/FwhHHMB/CS321-UML.png)
