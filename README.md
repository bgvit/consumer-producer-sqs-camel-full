# Consumer and Producer of SQS using Camel

![Simple cloud diagram](https://github.com/bgvit/consumer-producer-sqs-camel-full/blob/master/microservice_diagram.png?raw=true)

This project was created to be an use case of Camel, in order to easily connect with Amazon SQS and send or receive messages from queues. 
As a plus, we'd implemented OpenFeign library to make our API requests.

# Context
The main idea is to receive from some queue informations about Ticker. That's market information about some cryptocoins.

This Ticker will be processed and will generate an API call. This API request will retrieve information about a lot of crypto coin markets, in order to share price, amount of that coin was negotiate, etc...

### API provided by:

https://www.coinlore.com/pt/cryptocurrency-data-api

# References

https://camel.apache.org/camel-spring-boot/3.9.x/index.html
