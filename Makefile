.DEFAULT_GOAL := help

.PHONY: help up db db-update run shell build down

help:
	@printf '%s\n' \
		'make up     : start Docker Compose in the background' \
		'make db     : start only the database in the background' \
		'make db-update : update database tables from JPA models (Java 25 required)' \
		'make run    : run the application without Docker (Java 25 required)' \
		'make shell  : open Bash or Sh in the app container' \
		'make build  : build Docker Compose images' \
		'make down   : stop Docker Compose without deleting volumes'

up:
	docker compose up -d

db:
	docker compose up -d database

db-update:
	docker compose up -d --wait database
	sh mvnw spring-boot:run -Dspring-boot.run.arguments="--spring.main.web-application-type=none --spring.jpa.hibernate.ddl-auto=update"

run:
	sh mvnw spring-boot:run

shell:
	docker compose exec app sh -c 'if command -v bash >/dev/null 2>&1; then exec bash; else exec sh; fi'

build:
	docker compose build

down:
	docker compose down
