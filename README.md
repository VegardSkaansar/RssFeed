# RssFeed

# This Rss feed
choose a link e.g www.nrk.no/toppsaker.rss and put it into preferences url.
you can also under the preference tab set amount of feeds you want to get loaded in
and frequency of the update

# Rss code
i used library called XmlPullParser to parse the rss xml files.
And the tags used is the title, description, link and enclosure tag for the images

# Async tasks
In this rss feeds application we are using two async task
one for keeping track of the fetching and one fore keeping the 
track of the frequency of the fetches

# Search algorithm
In the newslist.java you will see a filter function that are using input from
an editext onchange to refill the recycler adapter with feeds everytime you are 
changing the text
