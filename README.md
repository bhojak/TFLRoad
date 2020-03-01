# TFLRoad
The TFL open API based project on Kotlin MVVM architecture + Coroutines 

Where we have 1 main use case:

Get a detail information about Road maintained by TFL.
Show details for an specific Road condition.
How to build on your environment
// replace this lines in the App build file

buildConfigField 'String', 'API_ID', '"Your_App_id"'
buildConfigField 'String', 'API_KEY', '"Your_App_key"'

In order to use it you will need to register for a developer key here:
https://api-portal.tfl.gov.uk/

Technical details and Open-source libraries
Minimum SDK level 19

Kotlin + MVVM + Coroutines
Clean architecture patterns - MVVM Architecture
MVVM Architecture (View - DataBinding - ViewModel - Model)

LiveData - It notifies views of any change in the domain layer data.
Lifecycle - dispose of observing data when lifecycle state changes.
ViewModel - UI related data holder, lifecycle aware.

