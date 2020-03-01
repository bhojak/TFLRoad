# TFLRoad
The TFL open API based project on Kotlin MVVM architecture + Coroutines 

Where we have 1 main use case:

Get a detail information about Road maintained by TFL.

Show details for an specific Road condition.

# How to build on your environment

1. Download zip file or clone in your local repository

2. open Android Studio and import project 

3. replace this lines in the App build file (app module):

 *  buildConfigField 'String', 'API_ID', '"Your_App_id"'

 *  buildConfigField 'String', 'API_KEY', '"Your_App_key"'

In order to get this keys for your project, you will need to register for a developer key here:

https://api-portal.tfl.gov.uk/

4. After modifying above two lines, clean build project and run in device or emulator.

5. once app is running and open, type road name in edit box and press button to get more information.
 
6. A valid road names (Ex: A1, A2, A3) will gives you current status of road.

# Technical details and Open-source libraries

Minimum SDK level 19

Kotlin + MVVM + Coroutines

Clean architecture patterns - MVVM Architecture

MVVM Architecture (View - DataBinding - ViewModel - Model)

LiveData - It notifies views of any change in the domain layer data.

Lifecycle - dispose of observing data when lifecycle state changes.

ViewModel - UI related data holder, lifecycle aware.

