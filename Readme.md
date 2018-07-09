# platfor-http-assessment

This is the source code for the bbc-platform coding challenge

This project is built in Java 10 using maven, so ensure both are installed before building.

**NOTE**

Despite a last minute attempt to fix the problem, this application cannot establish a connection when
behind the reith proxy. Hence, you will also need to be off reith in order to run it!

## Usage

From the project directory:

### build

```sh
./build
```

### test

```sh
./test
```

### run
```sh
# for example

./run http://www.bbc.co.uk/iplayer \
      https://google.com \
      bad://address \
      http://www.bbc.co.uk/missing/thing \
      http://not.exists.bbc.co.uk/ \
      http://www.oracle.com/technetwork/java/javase/downloads/index.html \
      https://www.pets4homes.co.uk/images/articles/1646/large/kitten-emergencies-signs-to-look-out-for- 537479947ec1c.jpg \
      http://site.mockito.org/
```