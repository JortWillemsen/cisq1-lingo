# Vulnerability Analysis

## A1:2017 Injection

### Description
SQL Injection is het uitvoeren van extra commandos waar dit niet mag. Dit kan door data mee te geven aan een query
en de interperter te laten denken dat de data onderdeel is van de commando en deze dus uitvoert. 

### Risk
Het risico hier is niet groot. Er wordt gebruik gemaakt van een betrouwbare dependency (hibernate) die 
alle database queries voor ons fixt. Het enige risico hier is een onzekerheid in Hibernate die geëxploiteerd kan worden.


### Counter-measures
We maken gebruik van Dependabot die altijd blijft controleren voor kwetsbaarheden in al onze dependencies waaronder 
Hibernate.

## A2:2017 Broken Authentication

### Description
Authenticatie kan niet goed geïmplementeerd zijn. Dit betekent dat aanvallers bij wachtwoorden of sessie tokens kunnen
komen en deze gebruiken om bij gevoelige informatie te komen.

### Risk
Er is hier op dit moment geen risico. Dit komt, omdat er geen gevoelige informatie opgeslagen wordt en de api volledig
open is. Mocht er wel authenticatie geïmplementeerd worden dan kan dit gedaan worden door Spring.

### Counter-measures
Mocht er Spring gebruikt worden voor de authenticatie betekent dit dat we afhankelijk zijn van Spring. We kunnen
de kwetsbaarheden van Spring monitoren door Dependabot te gebruiken.

## A7:2017 Cross Site Scripting (XXS)

### Description
XXS kan voorkomen wanneer een API gebruiker data niet goed checkt en deze data laat zien op een webpagina doormiddel van
javascript en HTML. Dit betekent dat een aanvaller code kan laten uitvoeren op een computer van een gebruiker.

### Risk
Het risico hier redelijk groot.
Onze API rendert geen HTML of javascript en geeft alleen JSON terug. Dit betekent dat er in de huidige situatie geen XXS
mogelijk is. Het moment dat er een front-end voorgebouwd word dan word dit een risico, omdat de API gebruiker data niet
valideert en deze mee kan geven aan een front-end die niet goed omgaat met de data.

### Counter-measures
Spring Security levert XSS filter opties om iedere request header, parameter en body te controleren. We kunnen hier goed
gebruik van maken, omdat onze API al op het Spring Boot framework draait.