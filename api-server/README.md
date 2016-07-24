# Getting started

## Requirement
    install java
    install scala
    install activator

## How to run
    activator compile
    activator run

## run db migration
    open localhost:9000 with browser
    click "apply this script now!" button

## valid params
curl -X POST http://localhost:9000/users -H "Content-Type: application/json" -d "{\"firstName\": \"Jim\", \"lastName\": \"Gordon\", \"age\": 55}"
curl -X POST http://localhost:9000/users -H "Content-Type: application/json" -d "{\"firstName\": \"Bruce\", \"lastName\": \"Wayne\", \"age\": 30}"
curl -X POST http://localhost:9000/users -H "Content-Type: application/json" -d "{\"firstName\": \"Bob\", \"lastName\": \"Kane\", \"age\": 83}"
curl -X POST http://localhost:9000/users -H "Content-Type: application/json" -d "{\"firstName\": \"John\", \"lastName\": \"Doe\", \"age\": 25}"

## invalid params
curl -X POST http://localhost:9000/users -H "Content-Type: application/json" -d "{\"first\": \"Jim\", \"lastName\": \"Gordon\", \"age\": 55}"
curl -X POST http://localhost:9000/users -H "Content-Type: application/json" -d "{\"firstName\": \"Bruce\", \"age\": 30}"
curl -X POST http://localhost:9000/users -H "Content-Type: application/json" -d "{\"firstName\": \"X\", \"lastName\": \"X\", \"age\": 83}"
curl -X POST http://localhost:9000/users -H "Content-Type: application/json" -d "{\"firstName\": \"John\", \"lastName\": \"Doe\", \"age\": 1000}"


