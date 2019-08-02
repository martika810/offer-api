# offer-api

## Api description

Simple RESTful software service that will
allow a merchant to create a new simple offer. Offers, once created, may be
queried. After the period of time defined on the offer it should expire and
further requests to query the offer should reflect that somehow. Before an offer
has expired users may cancel it.

## How to launch the api

    {code} mvn spring-boot:run {code}