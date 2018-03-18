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

This way, we have produced a facade for the SDK that exposes two operations: 
- get the number of registered light bulbs
- switch the brightness and colour of a given light bulb

The original SDK is much more intricate and feature-rich, so we have simplified into just these.


#### MicroProfile? ####
But where are the MicroProfile usage, you might be wondering? Don't worry, we'll come to that.

And what _is_ MicroProfile? JakartaEE for microservices, easily said.

While Jakarta EE is a great toolbox for more monolithic architectures, it is a bit overkill for microservices. 
MicroProfile is a more right-sied toolkit for microservices.
 
It also bears with it a lot of standardization efforts, to allow for easier replacement of specific tools 
and thus reduce vendor lock-in 


# Gateway #
To control the facade, we have built a gateway, as a separate microservice.

This is the service that actually decides what light we shall have. Its major responsibility is to get input, 
transform it into what they should mean for our lights, and then tell the facade what to do.

The gateway talks with two different input providers:

# Weather #
The default one is the weather service. This service gets the weather forecast for the given area, and extracts the temperature.

# Time #
The other input provider is the time service. This service simply gets the current time.

It's the gateway's responsibility to transform the temperature or time into light implications.


# TODO from here: incorporate #

# MicroProfile #

Things to talk about from the facade
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
* Rest client hack to be able to control the facade - 
this indicates that MicroProfile as well as its implementations still is a young technology so that everything is not yet mature.
However, certain features we have made use of were not available when we started the preparations for this presentation 
- for instance, OpenAPI were included in the Wildfly Swarm implementation just a couple of weeks ago.
* Actually, the next version of MicroProfile, 1.4, is released _today_. This release is focused on improving documentation and tutorials.
As of today, well, as of yesterday, the documentation available is somewhat fractured and hard to find.
* Retry + fallback (HttpConnector::getCloseableHttpResponse)
* Config - f.ex. HueURL or TimeRestConnector
* Health check
* The conversion between the data from the services into a light bulb control direction
* Metrics are automagically available at /metrics



# Round-up #

As you have seen, we have used a lot of the features included in MicroProfile in this project. 
However, there are some we haven't exploited.
* Rest client, as mentioned
* JWT authentication
* OpenTracing