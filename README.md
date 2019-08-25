# MicroProfile + IoT

While it’s easy to figure out why [MicroProfile][1] is cool, it’s might be hard to see how to properly use it in your project. This project intends to show you what MicroProfile is good for by using many of its key elements while solving a specific (and fun) problem – controlling the IoT enabled lighting.

Also, this project is going to show you what happens when the management lets enterprise Java developers and architects decide how the IoT devices can be controlled.

## Documentation
A detailed documentation and the description of the project can be found on a separate [page][4].

## Prerequisites
* Java 8, or newer
* [Maven][8]
* [Docker][6], [docker-compose][7]
* _Hardware:_ It also might be a good idea to have some Philips Hue lamps, or similar

## Running the Project
1. Build all the artifacts with Maven:
    ```
    $ mvn clean install -T 1C
    ```
2. Start all the containers – ```facade```, ```weatherservice```, and ```timeservice```:
    ```
    $ docker-compose up --build
    ```
    
    The setup of the Docker containers and mapping of the ports with the host machine can be seen in the [docker-compose.yml][5] file.

3. Start the gateway service: 
    
    Note: this service can be started at any time. It will also be dockerized in the future.

    ```
    $ cd gateway
    $ mvn liberty:run-server
    ```
4. Run the frontend:
    ```
    $ npm run serve
    ```
5. Use the frontend from http://localhost:8080/

## Authors
* [Mads Opheim][2]
* [Rustam Mehmandarov][3]



[1]: https://microprofile.io/
[2]: https://github.com/madsop
[3]: https://github.com/mehmandarov
[4]: /docs/structure.md
[5]: /docker-compose.yml
[6]: https://docs.docker.com/install/
[7]: https://docs.docker.com/compose/install/
[8]: https://maven.apache.org/