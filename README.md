![](samplegif.gif)
# Android Architecture Example
This app demonstrates how one can incorporate MVP principles into their application's architecture as well as provides uses of some of the latest libraries working together. 
* Dependencies are provided and satisfied with **Dagger 2**.
* Asynchronous events and reactions are defined with **RxJava2**.
* API calls are binded to interfaces with **Retrofit2**.
* JSON adapters are provided for server requests/responses with **Moshi**.
* Binding the UI elements to the underlying data is done with **Android DataBinding**.
* findViewById() boilerplate is moved over to annotations with **Butterknife**.
* A logging helper is added with **Timber**.
* New memory leaks are detected with **LeakCanary**.
* Image loading/caching is done with **Glide**.
* ~~Retrieval of posts are persisted to a database cache with **Realm**.~~

## Dagger 2
I've used scoping with Dagger 2 (see ActivityScope.java) to allow singletons to have a lifetime of the Android activity itself. There is an application-scoped (technically Singleton-scoped) component that uses application-wide modules, and child components of this are implemented through component dependencies rather than actual subcomponents. 

I chose to use component dependencies over subcomponents (both accomplish the same thing in different ways) since it's less verbose, requiring a single "dependencies" annotation parameter as seen in [HomeComponent](https://github.com/WaylonBrown/Android-Architecture-Example/blob/master/app/src/main/java/com/redditapp/dagger/components/HomeComponent.java) over the subcomponents route of tying the subcomponent to a seperate module that's consumed by the parent component. The only difference is that child dependencies have to be defined in the parent component as seen in [ApplicationComponent](https://github.com/WaylonBrown/Android-Architecture-Example/blob/master/app/src/main/java/com/redditapp/dagger/components/ApplicationComponent.java) but I think this is a good thing as a reminder of what dependencies might be used outside of the main parent graph.

## RxJava
If you'd like to see the meat and potatoes of how I've used RxJava to get the authentication token from the API, call the API endpoint for the actual data on its success, and have a timeout (which is a pretty standard Rx stream) you can see it all at [HomePresenter](https://github.com/WaylonBrown/Android-Architecture-Example/blob/master/app/src/main/java/com/redditapp/screens/home/HomePresenter.java) and [RxApiCallers](https://github.com/WaylonBrown/Android-Architecture-Example/blob/master/app/src/main/java/com/redditapp/api/RxApiCallers.java).

Its actually a lot more interesting if you check it out on the "Realm" branch, as it ties in choosing between data from the cache or the network, handling expired tokens, and thread-hopping between the background thread and the UI thread to read and write from the cache (Realm is kinda weird like that unless you want to run a brand new query each time).

## Data binding
So while we update UI through Realm callbacks, using DataBinding we don't have to call any setText() or setBackgroundImage() anywhere, we just update the variables that those UI elements correspond to. 

You can see this working in action with a RecyclerView in [ListingAdapter](https://github.com/WaylonBrown/Android-Architecture-Example/blob/master/app/src/main/java/com/redditapp/ui/ListingAdapter.java) which is great, all you have to do is bind the object that the view holder is backed by and the binding takes care of the rest. 

You can also see how data binding works with reusable layouts in [list_item_post_image.xml](https://github.com/WaylonBrown/Android-Architecture-Example/blob/master/app/src/main/res/layout/list_item_post_image.xml).

## Realm
~~Updating of UI elements is done through callbacks of the cache so that all that needs to happen with new data is to update the cache. This provides a 1-to-1 mapping of what you see in the UI and the underlying data.~~
 
*If you want to see the project working with Realm, it's in a seperate "realm" branch.*
 
Well, Realm was nice while it lasted in this project, but it didn't last. At some point, the cons of using Realm ended up far outweighing the pros that I had initially seen when having the Realm implementation working as expected. 

I think the point that finally ended up making me remove it was that I wanted to be able to update a parent object of a API response in the cache. This...wasn't nearly as easy as it should be. The parent object of my API response is 8 levels deep, meaning it contains data, that contains data, that contains data, and so forth. That's pretty standard with a lot of JSON APIs out there. When it came time for mapping an updated response into the cache, I realized that even though the parent object itself was getting updated, all of the 7 child levels were getting **duplicated**. This is because I defined a primary key for the parent object but not the children. In order to properly update it, I would need to define primary keys for every single child object class, and manually set the keys in a custom JSON adapter or by iterating through them at the time of deserialization.

The workaround stated [here](https://github.com/realm/realm-java/issues/2918) in the related Github issue was to remove the old object and add the new one, but this removed my RealmObject listeners which was essentially the whole point of me having realm in the first place. If I were to resubscribe to the listeners, then at that point what was the point of the listener? 

At this point the project doesn't have a DB solution, which is pretty fine for this type of app. I might incorporate an SQLite ORM at some point to compare the two, but the app handles great by grabbing fresh data at the moment. Here's a quick list of the other issues I found using Realm.
* Debugging is painful, in the debugger all values of your RealmObjects show as 0 and null, so you need to manually call the getters in the debugger just to see what the underlying value is.
* You can't have multiple primary keys on an object which is a pretty common things amongst databases (maybe I'm just carrying this over from SQLite and shouldn't be).
* Threading is a nightmare. Constant "you can't access this object on this thread" errors. RxJava helps with this, but sometimes I just want to use an object I've already queried on without the verbosity of thread-hopping, and there's no way I was going to create seperate POJOs that live outside of Realm just to address this. I understand it's worth it for the always-live objects, but at this point I'm already complaining right?
* None of my POJOs could be immutable. Realm requires a no-arg constructor meaning I can't use final fields, and other than that all fields either need to be public or have both getters and setters.
