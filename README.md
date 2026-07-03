## TMDB

An android app built using Jetpack Compose that consumes TMDB API to display the current trending, upcoming, top rated, and popular movies and tv-shows. It also suggests films based on your watch list.

## Installation

First, obtain your API key from TMDB and add it in a file named apikey.properties within the root directory
YOUR_API_KEY=********************************

Then, replace it in the build.gradle(:app)
 buildConfigField("String", "YOUR_API_KEY","\"${apikeyProperties["YOUR_API_KEY"]}\"" )
 
 Finally, rebuild the project for changes to take effect

 ## Tech Stack
 
Technology                      Purpose

 kotlin                          Programming Language
 MVVM                            Architecture
 Jetpack Compose                 UI
 Hilt                            Dependency injection
 Room                            Local Database
 Retrofit                        API Communication
 Compose Destination             Navigation
 Paging3                         Pagination
 Landscapist - CoilImage         Image Loading
 Coroutiens                      Asynchronous Programming
 Flow                            Reactive UI
 Lottie                          Animation
