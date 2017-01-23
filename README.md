# Android Architecture Example
This app hits the Reddit API to demonstrate how one can incorporate MVP principles into their application's architecture as well as provides uses of some of the latest libraries working together. 
* Dependencies are provided and satisfied with **Dagger 2**.
* Asynchronous events and reactions are defined with **RxJava2**.
* API calls are binded to interfaces with **Retrofit2**.
* Retrieval of posts are persisted to a database cache with **Realm**. Updating of UI elements is done through callbacks of the cache so that all that needs to happen with new data is to update the cache. This provides 2 benefits:
 1. The UI will update automatically since now the UI is a 1-to-1 mapping of the underlying data.
 2. Results are shown to the user immediately, even if out of date. For an example, if the app is killed and relaunched, instead of showing a loading screen and having the user wait, old data is immediately shown and the UI tells the user that there's updated data available if they'd like to refresh. This is a UX implementation detail, but makes sure users wait around less.
* **Moshi** is used as the JSON adapter for server requests/responses.
* **Android DataBinding** is used to bind UI to the underlying data. So while we update UI through Realm callbacks, using DataBinding we don't have to call any setText() or setBackgroundImage() anywhere, we just update the variables that those UI elements correspond to.
* **Butterknife** is used to remove findById() boilerplate.
* **Timber** is used as a logging helper.
* **LeakCanary** is incorporated to inform the dev if any memory leaks have been added.
## Dagger 2
## RxJava 2
## Retrofit 2
## Realm
## Moshi
## Data binding
## Butterknife
## Timber
## LeakCanary
