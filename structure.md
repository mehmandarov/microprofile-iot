So what is actually happening here?

We are moving to a new office. When doing that, we of course want to be modern, so we dive into the world of Internet of Things. 
In an office, we of course need lightning, so we have decided to introduce some lights.
And as we all know, people tend to be more tired at some times or in some situations than in others.
For example, early in the morning, you are typically more tired, so then you need lights (and coffee...) to wake up. 
Or in the afternoon when you've been working many hours already, and it's getting dark outside, you might need more light.
Or if it's freezingly cold outside, it could be tempting with some warm, intense lights.

Hence, we have bought some light bulbs that can be controlled with bits and bytes. I guess many already know these, the Philips Hue bulbs.
They can for instance be controlled via an app on your phone.

But, as we are a company of geeks, we don't want to sit and control these manually every day. So we need a system to help us.

# Facade #
But let's start from the beginning: controlling the light bulbs. Philips have made an SDK, available as jar files.

#### Anti-corruption layer ####
However, the API is a bit complicated and challenging to work with. It's also subject to change without us controlling it, 
so we decided to make a module wrapping the SDK. This gives us the ability to control which calls are made to the bulbs, 
and it also acts as an _anti-corruption layer_, preventing their code and their terms to leak into the rest of our system. 
We're using the term _facade_ for this module.

#### Maven ####
Maven is the building system we are most familiar with, so we use it for the facade as well. The Hue SDK is not available in Maven central, 
at least not yet, so we figured we had to do something smart for the code to compile with Maven. Hence, we have made a local maven repository, 
and added the SDK to this. Yes, it was somewhat hassle getting started with, but there are some good guides online. 
Also, when it's first properly setup, it just works and keeps working.

#### MicroProfile? ####
But where are the MicroProfile usage, you might be wondering? Don't worry, we'll come to that.

Things to talk about
* CDI all around
* The logger producer trick
* Docker container
* The JAX-RS stuff that just works
* Same thing with the JSON-P stuff that just works
* Switch state of light in WildflyEntryPoint should be annotated with OpenAPI stuff...
* ... which leads to a part about Wildfly Swarm


#### Possibly also some words about ####
* Lombok
* The wrapExceptions hack and ThrowingSupplier
* preferIPv4Stack
* docker-compose


# Gateway #
* Rest client hack to be able to control the facade
* Retry + fallback (HttpConnector::getCloseableHttpResponse)
* Config - f.ex. HueURL or TimeRestConnector
* Health check
* The conversion between the data from the services into a light bulb control direction
* Metrics are automagically available at /metrics